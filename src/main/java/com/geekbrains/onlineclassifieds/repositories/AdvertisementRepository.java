package com.geekbrains.onlineclassifieds.repositories;

import com.geekbrains.onlineclassifieds.entities.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDateTime;
import java.util.List;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Long>, JpaSpecificationExecutor<Advertisement> {
    List<Advertisement> findByExpirationDateBeforeAndIsDeletedFalse(LocalDateTime currentDateTime);
}
