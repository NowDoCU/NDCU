package com.hotsix.semochang.model.network.response;

import com.hotsix.semochang.model.Bookmark;
import com.hotsix.semochang.model.Commercial;
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
public class FounderApiResponse {

    /***
     * 창업자 id
     */
    private Long id;

    /***
     * 창업자 이메일
     */
    private String email;

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
     * 북마크 목록
     */
    private List<Bookmark> bookmarkList;

}
