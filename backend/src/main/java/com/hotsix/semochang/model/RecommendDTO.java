package com.hotsix.semochang.model;

import lombok.*;

import javax.persistence.Entity;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendDTO {

    private List<String> areas;

    private String categories;


}
