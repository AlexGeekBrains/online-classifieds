package com.geekbrains.onlineclassifieds.converters;

import com.geekbrains.onlineclassifieds.dto.PageResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class PageConverter {
    public <T> PageResponseDto<T> convertToCustomPage(Page<T> springPage) {
        return new PageResponseDto<>(
                springPage.getContent(),
                springPage.getNumber(),
                springPage.getSize(),
                springPage.getTotalElements(),
                springPage.getTotalPages(),
                springPage.isLast(),
                springPage.isFirst(),
                springPage.isEmpty()
        );
    }
}
