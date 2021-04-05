package com.hotsix.semochang.model.network.request;

import lombok.Builder;
import lombok.Data;

/**
 * author: pinest94
 * since: 2021-04-01
 */

@Data
@Builder
public class LoginApiRequest {

    private String email;

    private String password;
}
