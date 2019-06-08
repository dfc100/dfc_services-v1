package com.dfc.network.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@MappedSuperclass
public abstract class AuditFieldEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @CreatedDate
    private Timestamp createdDt;

    @LastModifiedDate
    private Timestamp updatedDt;

}
