package com.geekbrains.onlineclassifieds.repositories;

import com.geekbrains.onlineclassifieds.entities.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
}
