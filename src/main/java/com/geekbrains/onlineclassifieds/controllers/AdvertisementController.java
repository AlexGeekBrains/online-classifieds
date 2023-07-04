package com.geekbrains.onlineclassifieds.controllers;

import com.geekbrains.onlineclassifieds.converters.AdvertisementConverter;
import com.geekbrains.onlineclassifieds.dto.AdvertisementDto;
import com.geekbrains.onlineclassifieds.entities.Advertisement;
import com.geekbrains.onlineclassifieds.services.AdvertisementService;
import com.geekbrains.onlineclassifieds.validators.AdvertisementValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/advertisements")
@RequiredArgsConstructor
public class AdvertisementController {
    private final AdvertisementService advertisementService;
    private final AdvertisementValidator advertisementValidator;
    private final AdvertisementConverter advertisementConverter;

    @PostMapping
    public AdvertisementDto saveNewAdvertisement(@RequestBody AdvertisementDto advertisementDto, @RequestHeader(name = "username") String user) {
        advertisementValidator.validate(advertisementDto);
        Advertisement advertisement = advertisementService.saveNewAdvertisement(advertisementDto, user); // ToDo: is "username" header safe?
        return advertisementConverter.entityToDto(advertisement);
    }

    @PutMapping
    public AdvertisementDto updateAdvertisement(@RequestBody AdvertisementDto advertisementDto) {
        advertisementValidator.validate(advertisementDto);
        Advertisement advertisement = advertisementService.updateAdvertisementInfo(advertisementDto);
        return advertisementConverter.entityToDto(advertisement);
    }

    @PutMapping("/{id}")
    public void updateToPaid(@PathVariable Long id) {
        advertisementService.updateToPaid(id);
    }
}
