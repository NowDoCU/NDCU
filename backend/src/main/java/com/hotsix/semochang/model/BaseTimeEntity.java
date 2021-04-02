package com.hotsix.semochang.model;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * author: pinest94
 * since: 2021-04-02
 */

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseTimeEntity {

    /**
     * 생성일자
     * */
    @CreatedDate
    private LocalDateTime createdAt;

    /**
     * 생성자
     * */
    @CreatedBy
    private String createdBy;

    /**
     * 수정일자
     * */
    @LastModifiedDate
    private LocalDateTime updatedAt;

    /**
     * 수정일자
     * */
    @LastModifiedBy
    private String updatedBy;
}
