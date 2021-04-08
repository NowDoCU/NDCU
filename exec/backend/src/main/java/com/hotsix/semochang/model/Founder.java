package com.hotsix.semochang.model;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;

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
@EqualsAndHashCode(callSuper=false)
@ToString(exclude = {"bookmarkList"})
public class Founder extends BaseTimeEntity {

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

    /***
     * 북마크 목록
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "founder")
    private List<Bookmark> bookmarkList;
}