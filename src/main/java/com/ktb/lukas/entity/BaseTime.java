package com.ktb.lukas.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public abstract class BaseTime {


    @CreatedDate
    @Column(name = "create_at", updatable = false, nullable = false)          // 최초 등록 후 수정되지 않게 함
    private LocalDateTime createdAt;      // 자동으로 생성일자 입력


    @LastModifiedDate
    @Column(name = "update_at", nullable = false)
    private LocalDateTime updatedAt;   // 자동으로 수정일자 입력


}
