package com.hotsix.semochang.model.network.response;

import lombok.Builder;
import lombok.Data;

/**
 * author: pinest94
 * since: 2021-04-01
 */

@Data
@Builder
public class LoginApiResponse {

    private String accessToken;
}
