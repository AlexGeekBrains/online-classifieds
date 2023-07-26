package com.geekbrains.onlineclassifieds;

import com.geekbrains.onlineclassifieds.dto.AdvertisementDto;
import com.geekbrains.onlineclassifieds.dto.CategoryDto;
import com.geekbrains.onlineclassifieds.entities.Advertisement;
import com.geekbrains.onlineclassifieds.entities.Category;
import com.geekbrains.onlineclassifieds.entities.User;
import com.geekbrains.onlineclassifieds.exceptions.AdvertisementOwnershipException;
import com.geekbrains.onlineclassifieds.repositories.AdvertisementRepository;
import com.geekbrains.onlineclassifieds.services.AdvertisementServiceImpl;
import com.geekbrains.onlineclassifieds.services.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdvertisementServiceTest {
    @Mock
    private AdvertisementRepository advertisementRepository;

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private AdvertisementServiceImpl advertisementService;

    @Test
    void testUpdateExpiredAdvertisementsWithExpiredAdvertisements() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        int pageSize = 10;

        List<Advertisement> expiredAdvertisements = new ArrayList<>();
        // Создаем список просроченных объявлений
        for (int i = 0; i < 50; i++) {
            expiredAdvertisements.add(new Advertisement("BMW",
                    "was not in an accident, not painted",
                    BigDecimal.valueOf(35000 + i),
                    false,
                    false,
                    LocalDateTime.now().minusDays(1),
                    new User(),
                    new Category()
            ));
        }
        // Создаем страницу с просроченными объявлениями
        Page<Advertisement> expiredAdvertisementsPage = new PageImpl<>(expiredAdvertisements);
        // Имитируем поведение advertisementRepository.findByExpirationDateBeforeAndIsDeletedFalse для первой страницы
        when(advertisementRepository.findByExpirationDateBeforeAndIsDeletedFalse(currentDateTime, PageRequest.of(0, pageSize))).thenReturn(expiredAdvertisementsPage);
        // Вызываем тестируемый метод
        advertisementService.updateExpiredAdvertisements(currentDateTime, pageSize);
        // Проверяем, что метод findByExpirationDateBeforeAndIsDeletedFalse был вызван только один раз.
        verify(advertisementRepository, times(1)).findByExpirationDateBeforeAndIsDeletedFalse(any(), any());
        // Проверяем, что поля isDeleted стали равны true в каждом объявлении в списке expiredAdvertisements
        expiredAdvertisements.forEach(advertisement -> assertTrue(advertisement.getIsDeleted()));
    }

    @Test
    public void testUpdateAdvertisementInfo() {

        AdvertisementDto advertisementDto = new AdvertisementDto();
        advertisementDto.setTitle("New Title");
        advertisementDto.setDescription("New Description");
        advertisementDto.setUserPrice(BigDecimal.valueOf(1000));
        advertisementDto.setCategoryDto(new CategoryDto(2L, "New Category"));

        // Создаем объект Advertisement для имитации репозитория
        Long advertisementId = 1L;
        Advertisement advertisement = new Advertisement();
        advertisement.setId(advertisementId);
        advertisement.setTitle("Old Title");
        advertisement.setDescription("Old Description");
        advertisement.setUserPrice(BigDecimal.valueOf(500));
        Category category = new Category();
        category.setName("Electronics");
        advertisement.setCategory(category);

        // Настройка имитации репозитория
        when(advertisementRepository.findById(advertisementId)).thenReturn(Optional.of(advertisement));
        when(categoryService.getCategoryByName(anyString())).thenReturn(Optional.of(category));

        // Вызываем тестируемый метод
        Advertisement updatedAdvertisement = advertisementService.updateAdvertisementInfo(advertisementId, advertisementDto);

        // Проверяем, что объект Advertisement был изменен с использованием данных из Dto
        assertEquals(advertisementDto.getTitle(), updatedAdvertisement.getTitle());
        assertEquals(advertisementDto.getDescription(), updatedAdvertisement.getDescription());
        assertEquals(advertisementDto.getUserPrice(), updatedAdvertisement.getUserPrice());
        assertEquals(category, updatedAdvertisement.getCategory());

        // Проверяем, что метод findById был вызван один раз с правильным id
        verify(advertisementRepository, times(1)).findById(advertisementId);

        // Проверяем, что метод getCategoryByName был вызван один раз с правильным именем категории
        verify(categoryService, times(1)).getCategoryByName(advertisementDto.getCategoryDto().getName());
    }

    @Test
    public void testMarkAdvertisementAsDeletedSuccessful() {
        // Создаем тестовые данные
        Long advertisementId = 1L;
        String username = "testUser";
        Advertisement advertisement = new Advertisement();
        advertisement.setId(advertisementId);
        advertisement.setIsDeleted(false);
        User user = new User();
        user.setUsername(username);
        advertisement.setUser(user);

        //  Настройка имитации репозитория
        when(advertisementRepository.findById(advertisementId)).thenReturn(Optional.of(advertisement));

        // Вызываем тестируемый метод
        advertisementService.markAdvertisementAsDeleted(advertisementId, username);

        // Проверяем, что метод setIsDeleted(true) был вызван у объекта advertisement
        assertTrue(advertisement.getIsDeleted());
    }

    @Test
    public void testMarkAdvertisementAsDeletedAdvertisementNotFound() {
        // Создаем тестовые данные
        Long advertisementId = 1L;
        String username = "testUser";

        //  Настройка имитации репозитория для ситуации, когда объявление не найдено
        when(advertisementRepository.findById(advertisementId)).thenReturn(Optional.empty());

        // Вызываем тестируемый метод и ожидаем исключение EntityNotFoundException
        assertThrows(EntityNotFoundException.class, () -> advertisementService.markAdvertisementAsDeleted(advertisementId, username));
    }

    @Test
    public void testMarkAdvertisementAsDeletedAdvertisementNotOwned() {
        // Создаем тестовые данные
        Long advertisementId = 1L;
        String username = "testUser";
        Advertisement advertisement = new Advertisement();
        advertisement.setId(advertisementId);
        advertisement.setIsDeleted(false);
        User otherUser = new User();
        otherUser.setUsername("otherUser"); // Устанавливаем другое имя пользователя
        advertisement.setUser(otherUser);

        //  Настройка имитации репозитория
        when(advertisementRepository.findById(advertisementId)).thenReturn(Optional.of(advertisement));

        // Вызываем тестируемый метод и ожидаем исключение AdvertisementOwnershipException
        assertThrows(AdvertisementOwnershipException.class, () -> advertisementService.markAdvertisementAsDeleted(advertisementId, username));
    }
}