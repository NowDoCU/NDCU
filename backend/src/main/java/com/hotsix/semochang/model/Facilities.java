package com.hotsix.semochang.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

/**
 * author: pinest94
 * since: 2021-04-07
 */

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Facilities {
    
    /***
     * 기본키
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String commercialCode;

    /***
     * 모든 집객시설 수
     */
    private int allCnt;

    /***
     * 학교 수
     */
    private int school;

    /***
     * 극장+백화점
     */
    private int entertainment;

    /***
     * 숙박시설
     */
    private int accommodation;

    /***
     * 지하철(지하철 + 철도)
     */
    private int rail;

    /***
     * 버스(버스정류장 + 버스터미널)
     */
    private int bus;
}
