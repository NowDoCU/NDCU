package com.hotsix.semochang.model;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;

/**
 * author: pinest94
 * since: 2021-03-30
 */

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"estimatedPopulationList", "estimatedSalesList"})
public class Commercial {

    /***
     * 상권 아이디
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /***
     * 상권 구분코드
     */
    private String divisionCode;

    /***
     * 상권 구분명
     */
    private String divisionName;

    /***
     * 상권 코드
     */
    private String commercialCode;

    /***
     * 상권명
     */
    private String commercialName;

    /***
     * 상권 위치 x좌표
     */
    private int x;

    /***
     * 상권 위치 y좌표
     */
    private int y;

    /***
     * 상권 시군구 코드
     */
    private String sigunguCode;

    /***
     * 상권 행정동 코드
     */
    private String dongCode;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "commercial")
    private List<EstimatedPopulation> estimatedPopulationList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "commercial")
    private List<EstimatedSales> estimatedSalesList;
}
