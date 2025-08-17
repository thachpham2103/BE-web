package com.example.be.web.doman.entity.common;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)

public abstract class UserAuditing {

    @CreatedDate
    @Column(updatable = false)
    private String createBy;

    @LastModifiedBy
    @Column(nullable = false)
    private String lastModifiedBy;

}
