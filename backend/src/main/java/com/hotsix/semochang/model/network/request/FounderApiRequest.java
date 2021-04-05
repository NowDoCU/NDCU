package com.hotsix.semochang.model.network.request;

import com.hotsix.semochang.model.Bookmark;
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
public class FounderApiRequest {

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
}
