package com.geekbrains.onlineclassifieds.controllers;

import com.geekbrains.onlineclassifieds.converters.AdvertisementConverter;
import com.geekbrains.onlineclassifieds.dto.AdvertisementDto;
import com.geekbrains.onlineclassifieds.entities.Advertisement;
import com.geekbrains.onlineclassifieds.services.AdvertisementService;
import com.geekbrains.onlineclassifieds.utils.JwtTokenUtil;
import com.geekbrains.onlineclassifieds.validators.AdvertisementValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("api/v1/advertisements")
@RequiredArgsConstructor
public class AdvertisementController {
    private final AdvertisementService advertisementService;
    private final AdvertisementValidator advertisementValidator;
    private final AdvertisementConverter advertisementConverter;
    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping()
    public AdvertisementDto saveNewAdvertisement(@RequestBody AdvertisementDto advertisementDto, @RequestHeader(name = "Authorization") String token) {
        advertisementValidator.validate(advertisementDto);
        Advertisement advertisement = advertisementService.saveNewAdvertisement(advertisementDto, jwtTokenUtil.getUsernameFromToken(token));
        return advertisementConverter.entityToDto(advertisement);
    }

    @PutMapping("/{id}")
    public AdvertisementDto updateAdvertisement(@PathVariable Long id, @RequestBody AdvertisementDto advertisementDto) {
        advertisementValidator.validate(advertisementDto);
        Advertisement advertisement = advertisementService.updateAdvertisementInfo(id, advertisementDto);
        return advertisementConverter.entityToDto(advertisement);
    }

    @PutMapping("/{id}/paid")
    public void updateToPaid(@PathVariable Long id) {
        advertisementService.updateToPaid(id);
    }

    @GetMapping("/get-advertisements")
    public ResponseEntity<Page<AdvertisementDto>> filterAdvertisements(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String partTitle,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(defaultValue = "0") Integer page) {
        Page<AdvertisementDto> filteredAdvertisements = advertisementService.findAllWithFilter(minPrice, maxPrice, partTitle, categoryId, page);
        return ResponseEntity.ok(filteredAdvertisements);
    }
}
