package com.geekbrains.onlineclassifieds.services;

import com.geekbrains.onlineclassifieds.converters.AdvertisementConverter;
import com.geekbrains.onlineclassifieds.converters.PageConverter;
import com.geekbrains.onlineclassifieds.dto.*;
import com.geekbrains.onlineclassifieds.entities.Advertisement;
import com.geekbrains.onlineclassifieds.entities.Category;
import com.geekbrains.onlineclassifieds.entities.User;
import com.geekbrains.onlineclassifieds.exceptions.AdvertisementOwnershipException;
import com.geekbrains.onlineclassifieds.exceptions.FreeLimitExceededException;
import com.geekbrains.onlineclassifieds.exceptions.UnavailableAdvertisementException;
import com.geekbrains.onlineclassifieds.repositories.AdvertisementRepository;
import com.geekbrains.onlineclassifieds.repositories.specifications.AdvertisementSpecifications;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdvertisementServiceImpl implements AdvertisementService {
    private final AdvertisementRepository advertisementRepository;
    private final AdvertisementConverter advertisementConverter;
    private final UserService userService;
    private final CategoryService categoryService;
    private final PageConverter pageConverter;

    private User getUserByUsername(String username) {
        return userService.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("Selected username not found in the DB: " + username));
    }

    private Advertisement getAdvertisementById(Long id) {
        return advertisementRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Can't perform the operation - no advertisement with id: %s found in the DB", id)));
    }

    private void checkUserRights(Advertisement advertisement, String username) {
        if (!username.equals(advertisement.getUser().getUsername())) {
            User user = getUserByUsername(username);
            if (user.getRoles().stream().noneMatch(r -> r.getName().equals(RoleConstants.ROLE_ADMIN))) {
                throw new AdvertisementOwnershipException("Advertisement does not belong to the user who sent the request: you are not authorized to manipulate this advertisement");
            }
        }
    }

    @Override
    @Transactional
    public Advertisement saveNewAdvertisement(AdvertisementDto advertisementDto, String username) {
        User user = getUserByUsername(username);
        if (advertisementRepository.countByUserAndIsPaidAndIsDeleted(user, false ,false) >= AdvertisementConstants.FREE_ADVERTISEMENT_LIMIT) {
            throw new FreeLimitExceededException(String.format("Sorry, maximum %s free advertisements allowed. Please, consider upgrading to payed or deleting old advertisements", AdvertisementConstants.FREE_ADVERTISEMENT_LIMIT));
        }
        advertisementDto.setExpirationDate(LocalDateTime.now().plusDays(1));
        advertisementDto.setIsPaid(false);
        advertisementDto.setIsDeleted(false);
        String categoryName = advertisementDto.getCategoryDto().getName();
        Category category = categoryService.getCategoryByName(categoryName)
                .orElseThrow(() -> new EntityNotFoundException("Can't save the product, category not found: " + categoryName));
        Advertisement advertisement = advertisementConverter.dtoToEntity(advertisementDto, user, category);
        advertisement.setId(null);
        return advertisementRepository.save(advertisement);
    }

    @Override
    @Transactional
    public Advertisement updateAdvertisementInfo(Long id, AdvertisementDto advertisementDto, String username) {
        Advertisement advertisement = getAdvertisementById(id);
        checkUserRights(advertisement, username);
        advertisement.setTitle(advertisementDto.getTitle());
        advertisement.setDescription(advertisementDto.getDescription());
        advertisement.setUserPrice(advertisementDto.getUserPrice());
        String categoryName = advertisementDto.getCategoryDto().getName();
        advertisement.setCategory(categoryService.getCategoryByName(categoryName).orElseThrow(() -> new EntityNotFoundException("Can't update the product, category not found: " + categoryName)));
        return advertisement;
    }

    @Override
    @Transactional
    public void updateToPaid(Long id) {
        Advertisement advertisement = getAdvertisementById(id);
        if (advertisement.getExpirationDate().plusHours(1).isAfter(LocalDateTime.now().plusDays(AdvertisementConstants.MAXIMUM_PAYED_LENGTH))) {
            throw new UnavailableAdvertisementException(String.format("You don't need to pay for the advertisement (id: %s) now. It already has its maximum duration available.", id));
        }
        advertisement.setIsPaid(true);
        advertisement.setExpirationDate(advertisement.getExpirationDate().plusDays(AdvertisementConstants.DAYS_GRANTED_BY_PAYMENT));
    }

    @Override
    @Transactional
    public PageResponseDto<AdvertisementDto> findAllWithFilter(BigDecimal minPrice, BigDecimal maxPrice, String partTitle, Long categoryId, Integer page, Boolean isNotDeleted, Boolean isNotExpiredYet) {
        Specification<Advertisement> specification = Specification.where(null);
        if (isNotDeleted != null && isNotDeleted) {
            specification = specification.and(AdvertisementSpecifications.isNotDeleted());
        }
        if (isNotExpiredYet != null && isNotExpiredYet) {
            specification = specification.and(AdvertisementSpecifications.isNotExpiredYet(LocalDateTime.now()));
        }
        if (categoryId != null) {
            Category category = categoryService.getCategoryById(categoryId)
                    .orElseThrow(() -> new EntityNotFoundException("Selected category not found (not found in the DB) id: " + categoryId));
            specification = specification.and(AdvertisementSpecifications.hasCategory(category));
        }
        if (maxPrice != null) {
            specification = specification.and(AdvertisementSpecifications.lessThanOrEqualToPrice(maxPrice));
        }
        if (minPrice != null) {
            specification = specification.and(AdvertisementSpecifications.greaterThanOrEqualToPrice(minPrice));
        }
        if (partTitle != null) {
            specification = specification.and(AdvertisementSpecifications.titleLike(partTitle));
        }
        return pageConverter.convertToCustomPage(advertisementRepository.findAll(specification, PageRequest.of(page - 1, 10))
                .map(advertisementConverter::entityToDto));
    }

    @Override
    @Transactional
    public void updateExpiredAdvertisements(LocalDateTime currentDateTime, int pageSize) {
        Pageable pageable = PageRequest.of(0, pageSize);
        Page<Advertisement> expiredAdvertisementsPage;
        do {
            expiredAdvertisementsPage = advertisementRepository.findByExpirationDateBeforeAndIsDeletedFalse(currentDateTime, pageable);
            List<Advertisement> expiredAdvertisements = expiredAdvertisementsPage.getContent();
            processExpiredAdvertisements(expiredAdvertisements);
        } while (expiredAdvertisementsPage.hasNext());
    }

    private void processExpiredAdvertisements(List<Advertisement> expiredAdvertisements) {
        expiredAdvertisements.forEach(advertisement -> {
            advertisement.setIsDeleted(true);
            advertisementRepository.save(advertisement);
        });
    }

    @Override
    @Transactional
    public void markAdvertisementAsDeleted(Long advertisementId, String username) {
        Advertisement advertisement = getAdvertisementById(advertisementId);
        checkUserRights(advertisement, username);
        advertisement.setIsDeleted(true);
    }

    @Override
    public AdvertisementInfoDto showDetailedInfo(Long id) {
        Advertisement advertisement = getAdvertisementById(id);
        return new AdvertisementInfoDto(
                advertisement.getTitle(),
                advertisement.getDescription(),
                advertisement.getUserPrice(),
                advertisement.getCategory().getName()
        );
    }

    @Override
    @Transactional
    public UserContactsDto showUserContacts(Long id) {
        Advertisement advertisement = getAdvertisementById(id);
        if (advertisement.getIsDeleted()) throw new UnavailableAdvertisementException(String.format("The advertisement with id %s has expired or was deleted.", id));
        User user = advertisement.getUser();
        return new UserContactsDto(
                user.getDisplayName(),
                user.getTelephone(),
                user.getEmail()
        );
    }
}