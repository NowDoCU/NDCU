package com.hotsix.semochang.model.network.response;

import com.hotsix.semochang.model.EstimatedPopulation;
import com.hotsix.semochang.model.EstimatedSales;
import com.hotsix.semochang.model.StoreRentalPrice;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * author: pinest94
 * since: 2021-04-05
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommercialApiResponse {

    /***
     * 상권 코드
     */
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
     * 상권 추정유동인구추이 정보
     */
    private List<EstimatedPopulation> estimatedPopulationList;

    /***
     * 상권 추정매출추이 정보
     */
    private List<EstimatedSales> estimatedSalesList;

    /***
     * 상권 임대료 시세 정보
     */
    private StoreRentalPrice storeRentalPrice;
}
