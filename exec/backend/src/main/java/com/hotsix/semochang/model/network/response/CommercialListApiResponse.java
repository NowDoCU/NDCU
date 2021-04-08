package com.hotsix.semochang.model.network.response;

import com.hotsix.semochang.model.Commercial;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * author: pinest94
 * since: 2021-04-05
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommercialListApiResponse {

    private List<Commercial> commercialList;
}
