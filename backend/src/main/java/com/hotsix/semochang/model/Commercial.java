package com.hotsix.semochang.model;

import lombok.*;

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
@ToString(exclude = {"estimatedPopulationList", "estimatedSalesList", "storeRentalPrice"})
public class Commercial {

    /***
     * 상권 코드
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String commercialCode;

    /***
     * 상권 구분코드
     */
    private String divisionCode;

    /***
     * 상권 구분명
     */
    private String divisionName;

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

    @OneToOne
    @JoinColumn(name = "commercial_code")
    private StoreRentalPrice storeRentalPrice;
}
