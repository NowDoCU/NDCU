package com.hotsix.semochang.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

/**
 * author: pinest94
 * since: 2021-03-31
 */

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreRentalPrice {

    /***
     * 임대시세 id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String commercialCode;

    /***
     * 상권명
     */
    private String commercialName;

    /***
     * 활성화지역 2층 보증금
     */
    private int activateSecondDeposit;

    /***
     * 활성화지역 1층 보증금
     */
    private int activateFirstDeposit;

    /***
     * 활성화지역 지하 보증금
     */
    private int activateUnderDeposit;

    /***
     * 비활성화지역 2층 보증금
     */
    private int deactivateSecondDeposit;

    /***
     * 비활성화지역 1층 보증금
     */
    private int deactivateFirstDeposit;

    /***
     * 비활성화지역 지하 보증금
     */
    private int deactivateUnderDeposit;

    /***
     * 활성화지역 2층 평당 임대료
     */
    private int activateSecondRent;

    /***
     * 활성화지역 1층 평당 임대료
     */
    private int activateFirstRent;

    /***
     * 활성화지역 지하 평당 임대료
     */
    private int activateUnderRent;

    /***
     * 비활성화지역 2층 평당 임대료
     */
    private int deactivateSecondRent;

    /***
     * 비활성화지역 1층 평당 임대료
     */
    private int deactivateFirstRent;

    /***
     * 비활성화지역 지하 평당 임대료
     */
    private int deactivateUnderRent;
}
