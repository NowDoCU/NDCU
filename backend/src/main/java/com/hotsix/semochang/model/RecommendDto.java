package com.hotsix.semochang.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class RecommendDto {
    private List<String> areas;
    private List<String> categories;

    public RecommendDto(List<String> areas, List<String> categories) {
        this.areas = areas;
        this.categories = categories;
    }
}
