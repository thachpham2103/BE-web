package com.example.be.web.doman.entity.common;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)

public abstract class FlagUserDateAuditing extends UserDateAuditing {

    @Column(nullable = false)
    private Boolean deleteFlag= Boolean.FALSE;

    @Column(nullable = false)
    private Boolean activeFlag= Boolean.TRUE;


}
