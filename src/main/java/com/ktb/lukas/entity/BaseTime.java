package com.ktb.lukas.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public abstract class BaseTime {


    @CreatedDate
    @Column(updatable = false, nullable = false)          // 최초 등록 후 수정되지 않게 함
    private LocalDateTime REG_TIME;      // 자동으로 생성일자 입력


    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime UPDATE_TIME;   // 자동으로 수정일자 입력


}
