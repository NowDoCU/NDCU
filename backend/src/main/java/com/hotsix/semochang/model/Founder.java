package com.hotsix.semochang.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * author: pinest94
 * since: 2021-03-31
 */

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Founder {

    /***
     * 창업자 id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /***
     * 창업자 이메일
     */
    private String email;

    /***
     * 창업자 비밀번호
     */
    private String password;

    /***
     * 창업자 이름
     */
    private String name;

    /***
     * 창업자 전화번호
     */
    private String phoneNumber;

    /***
     * 창업자 상태
     * DEACTIVATED : 비활성화 / READY : 인증 전 / ACTIVATED : 활성화 / ADMIN : 관리자
     * TODO: ENUM Type으로 변경할 예정
     */
    private String status;

    /***
     * 창업자 이메일 인증키
     */
    private String verificationKey;
}