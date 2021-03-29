package com.hotsix.semochang.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * author: pinest94
 * since: 2021-03-29
 */

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstimatedSales {

    /***
     * 추정매출 기본키 id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /***
     * 추정매출 연도
     */
    private String year;

    /***
     * 추정매출 분기
     */
    private String quarter;

    /***
     * 추정매출 상권분류코드
     */
    private String divisionCode;

    /***
     * 추정매출 상권분류명
     */
    private String divisionName;

    /***
     * 추정매출 상권코드
     */
    private String commercialCode;

    /***
     * 추정매출 상권명
     */
    private String commercialName;

    /***
     * 추정매출 업종코드
     */
    private String industryCode;

    /***
     * 추정매출 업종명
     */
    private String industryName;

    /***
     * 추정매출 당월매출금액
     */
    private Long salesMonth;
}
