package com.geekbrains.onlineclassifieds.controllers;

import com.geekbrains.onlineclassifieds.converters.AdvertisementConverter;
import com.geekbrains.onlineclassifieds.dto.AdvertisementDto;
import com.geekbrains.onlineclassifieds.dto.AdvertisementInfoDto;
import com.geekbrains.onlineclassifieds.dto.PageResponseDto;
import com.geekbrains.onlineclassifieds.dto.UserContactsDto;
import com.geekbrains.onlineclassifieds.entities.Advertisement;
import com.geekbrains.onlineclassifieds.exceptions.ListError;
import com.geekbrains.onlineclassifieds.exceptions.SingleError;
import com.geekbrains.onlineclassifieds.services.AdvertisementService;
import com.geekbrains.onlineclassifieds.validators.AdvertisementValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;

@RestController
@RequestMapping("api/v1/advertisements")
@RequiredArgsConstructor
public class AdvertisementController {
    private final AdvertisementService advertisementService;
    private final AdvertisementValidator advertisementValidator;
    private final AdvertisementConverter advertisementConverter;

    @Operation(summary = "Save a new advertisement")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Advertisement saved successfully",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = AdvertisementDto.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ListError.class))}),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SingleError.class))}),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SingleError.class))})
    })
    @SecurityRequirement(name = "JWT")
    @PostMapping()
    public AdvertisementDto saveNewAdvertisement(@RequestBody AdvertisementDto advertisementDto, Principal principal) {
        advertisementValidator.validate(advertisementDto);
        Advertisement advertisement = advertisementService.saveNewAdvertisement(advertisementDto, principal.getName());
        return advertisementConverter.entityToDto(advertisement);
    }

    @Operation(summary = "Update an advertisement")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Advertisement updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AdvertisementDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ListError.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SingleError.class))}),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = SingleError.class)))
    })
    @SecurityRequirement(name = "JWT")
    @PutMapping("/{id}")
    public AdvertisementDto updateAdvertisement(@PathVariable Long id, @RequestBody AdvertisementDto advertisementDto, Principal principal) {
        advertisementValidator.validate(advertisementDto);
        Advertisement advertisement = advertisementService.updateAdvertisementInfo(id, advertisementDto, principal.getName());
        return advertisementConverter.entityToDto(advertisement);
    }

    @Operation(summary = "Update advertisement to paid")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Advertisement updated to paid successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = SingleError.class))),

    })
    @SecurityRequirement(name = "JWT")
    @PutMapping("/{id}/paid")
    public void updateToPaid(@PathVariable Long id) {
        advertisementService.updateToPaid(id);
    }

    @Operation(summary = "Filter advertisements")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved filtered advertisements",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PageResponseDto.class))}),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SingleError.class))}),
    })
    @GetMapping("/get-advertisements")
    public ResponseEntity<PageResponseDto<AdvertisementDto>> filterAdvertisements(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String partTitle,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(defaultValue = "1") Integer page) {
        if (page < 1) {
            page = 1;
        }
        PageResponseDto<AdvertisementDto> filteredAdvertisements = advertisementService.findAllWithFilter(minPrice, maxPrice, partTitle, categoryId, page, true, true);
        return ResponseEntity.ok(filteredAdvertisements);
    }

    @Operation(summary = "Mark advertisement as deleted")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Advertisement marked as deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SingleError.class))}),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SingleError.class))}),

    })
    @SecurityRequirement(name = "JWT")
    @PostMapping("/{id}/mark-deleted")
    public void markAdvertisementAsDeleted(@PathVariable Long id, Principal principal) {
        advertisementService.markAdvertisementAsDeleted(id, principal.getName());
    }

    @Operation(summary = "Show detailed info of the advertisement")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved filtered advertisements",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = AdvertisementInfoDto.class))}),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SingleError.class))}),
    })
    @GetMapping("/get-advertisements/{id}")
    // I made the method but later became unsure if it's needed. That might be Front's task to hide adv. description on Page and show when clicked - right now description is in AdvertisementDto. Just in case I will leave it here for now till there's front part.
    public ResponseEntity<AdvertisementInfoDto> showDetailedInfo(@PathVariable Long id) {
        return ResponseEntity.ok(advertisementService.showDetailedInfo(id));
    }

    @Operation(summary = "Show contacts of the advertisement's owner")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contacts have been shown",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserContactsDto.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SingleError.class))}),

    })
    @SecurityRequirement(name = "JWT")
    @GetMapping("/get-advertisements/{id}/contacts")
    public ResponseEntity<UserContactsDto> showUserContacts(@PathVariable Long id) {
        return ResponseEntity.ok(advertisementService.showUserContacts(id));
    }
}
