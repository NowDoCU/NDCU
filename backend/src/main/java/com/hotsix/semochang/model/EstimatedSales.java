package com.hotsix.semochang.model;

import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * author: pinest94
 * since: 2021-03-29
 */

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"commercial"})
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

    /***
     * 해당 상권정보
     */
    @ManyToOne
    @JoinColumn(name = "commercial_code")
    @JsonIgnore
    private Commercial commercial;
}
