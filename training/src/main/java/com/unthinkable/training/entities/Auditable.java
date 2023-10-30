package com.unthinkable.training.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public abstract class Auditable {

//    @CreatedBy
//    protected String createdBy;

    @CreatedDate
    @Column(updatable = false)
    protected LocalDateTime createdDate;

//    @LastModifiedBy
//    protected String lastModifiedBy;

    @LastModifiedDate
    protected LocalDateTime lastModifiedDate;

}