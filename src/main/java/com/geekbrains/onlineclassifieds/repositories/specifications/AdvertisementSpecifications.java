package com.geekbrains.onlineclassifieds.repositories.specifications;

import com.geekbrains.onlineclassifieds.entities.Advertisement;
import com.geekbrains.onlineclassifieds.entities.Category;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Repository
public interface AdvertisementSpecifications {
    static Specification<Advertisement> isNotDeleted() {
        return (root, query, builder) ->
                builder.equal(root.get("isDeleted"), false);
    }

    static Specification<Advertisement> isNotExpiredYet(LocalDateTime localDateTime) {
        return (root, query, builder) ->
                builder.greaterThan(root.get("expirationDate"), localDateTime);
    }

    static Specification<Advertisement> hasCategory(Category category) {
        return (root, query, builder) -> builder.equal(root.get("category"), category);
    }

    static Specification<Advertisement> lessThanOrEqualToPrice(BigDecimal maxPrice) {
        return (root, query, builder) ->
                builder.lessThanOrEqualTo(root.get("userPrice"), maxPrice);
    }

    static Specification<Advertisement> greaterThanOrEqualToPrice(BigDecimal minPrice) {
        return (root, query, builder) ->
                builder.greaterThanOrEqualTo(root.get("userPrice"), minPrice);
    }

    static Specification<Advertisement> titleLike(String titlePart) {
        return (root, query, builder) -> builder.like(builder.lower(root.get("title")), String.format("%%%s%%", titlePart.toLowerCase()));
    }
}
