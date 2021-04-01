package com.hotsix.semochang.model;

import lombok.Builder;
import lombok.Data;

/**
 * author: pinest94
 * since: 2021-04-01
 */

@Data
@Builder
public class LoginResponseDTO {

    private String accessToken;
}
