package com.hotsix.semochang.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;

/**
 * author: pinest94
 * since: 2021-04-07
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Gu {

    @Id
    private String _id;

    private String sig_cd;

    private String sig_eng_nm;

    private String full_nm;

    private String sig_kor_nm;

    private String lng;

    private String lat;

    private List<List<String>> coordinates;
}
