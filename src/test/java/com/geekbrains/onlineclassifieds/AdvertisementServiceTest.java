package com.geekbrains.onlineclassifieds;

import com.geekbrains.onlineclassifieds.entities.Advertisement;
import com.geekbrains.onlineclassifieds.entities.User;
import com.geekbrains.onlineclassifieds.repositories.AdvertisementRepository;
import com.geekbrains.onlineclassifieds.services.AdvertisementServiceImpl;
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

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdvertisementServiceTest {
    @Mock
    private AdvertisementRepository advertisementRepository;

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
                    new User()));
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
}
