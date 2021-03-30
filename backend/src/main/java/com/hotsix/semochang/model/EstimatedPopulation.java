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
public class EstimatedPopulation {

    /***
     * 추정유동인구 기본키 id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /***
     * 연도
     */
    private String year;

    /***
     * 분기
     */
    private String quarter;

    /***
     * 상권분류코드
     */
    private String divisionCode;

    /***
     * 상권분류명
     */
    private String divisionName;

    /***
     * 상권코드
     */
    private String commercialCode;

    /***
     * 상권명
     */
    private String commercialName;

    /***
     * 전체 유동인구
     */
    private int allPopulation;

    /***
     * 남성 유동인구
     */
    private int malePopulation;

    /***
     * 여성 유동인구
     */
    private int femalePopulation;

    /***
     * 10대 유동인구
     */
    private int age10;

    /***
     * 20대 유동인구
     */
    private int age20;

    /***
     * 30대 유동인구
     */
    private int age30;

    /***
     * 40대 유동인구
     */
    private int age40;

    /***
     * 50대 유동인구
     */
    private int age50;

    /***
     * 60대이상 유동인구
     */
    private int age60;

    /***
     * time1 : 00~06 시간대
     */
    private int time1;

    /***
     * time2 : 06~11 시간대
     */
    private int time2;

    /***
     * time3 : 11~14 시간대
     */
    private int time3;

    /***
     * time4 : 14~17 시간대
     */
    private int time4;

    /***
     * time5 : 17~21 시간대
     */
    private int time5;

    /***
     * time6 : 21~00 시간대
     */
    private int time6;

    /***
     * 월요일 유동인구
     */
    private int monPopulation;

    /***
     * 화요일 유동인구
     */
    private int tuePopulation;

    /***
     * 수요일 유동인구
     */
    private int wedPopulation;

    /***
     * 목요일 유동인구
     */
    private int thuPopulation;

    /***
     * 금요일 유동인구
     */
    private int friPopulation;

    /***
     * 토요일 유동인구
     */
    private int satPopulation;

    /***
     * 일요일 유동인구
     */
    private int sunPopulation;
}
