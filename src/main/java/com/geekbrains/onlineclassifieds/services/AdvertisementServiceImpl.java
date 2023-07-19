package com.geekbrains.onlineclassifieds.services;

import com.geekbrains.onlineclassifieds.converters.AdvertisementConverter;
import com.geekbrains.onlineclassifieds.dto.AdvertisementDto;
import com.geekbrains.onlineclassifieds.entities.Advertisement;
import com.geekbrains.onlineclassifieds.entities.Category;
import com.geekbrains.onlineclassifieds.entities.User;
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

    @Override
    public Advertisement saveNewAdvertisement(AdvertisementDto advertisementDto, String username) {
        User user = userService.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("Selected username not found (not found in the DB): " + username));
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
    public Advertisement updateAdvertisementInfo(Long id, AdvertisementDto advertisementDto) {
        Advertisement advertisement = advertisementRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Can't update the product (not found in the DB) id: " + id));
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
        Advertisement advertisement = advertisementRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Can't update the product (not found in the DB) id: " + id));
        advertisement.setIsPaid(true);
        if (advertisement.getExpirationDate() == null) { // ToDo: Idea says it can't be null, which is technically true; but it is created null in initialization. Temporary.
            advertisement.setExpirationDate(LocalDateTime.now().plusDays(7));
        } else {
            advertisement.setExpirationDate(advertisement.getExpirationDate().plusDays(7)); // ToDo: days added should depend on the payment
        }
    }

    @Override
    public Page<AdvertisementDto> findAllWithFilter(BigDecimal minPrice, BigDecimal maxPrice, String partTitle, Long categoryId, Integer page, Boolean isNotDeleted, Boolean isNotExpiredYet) {
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
        return advertisementRepository.findAll(specification, PageRequest.of(page, 10)).map(advertisementConverter::entityToDto);
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
}
