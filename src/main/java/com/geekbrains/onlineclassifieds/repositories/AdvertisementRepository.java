package com.geekbrains.onlineclassifieds.repositories;

import com.geekbrains.onlineclassifieds.entities.Advertisement;
import com.geekbrains.onlineclassifieds.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDateTime;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Long>, JpaSpecificationExecutor<Advertisement> {
    Page<Advertisement> findByExpirationDateBeforeAndIsDeletedFalse(LocalDateTime currentDateTime, Pageable pageable);
    long countByUserAndIsPaidAndIsDeleted(User user, Boolean isPaid, Boolean isDeleted);
}