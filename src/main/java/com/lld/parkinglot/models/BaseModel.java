package com.lld.parkinglot.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.MappedSuperclass;
import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class BaseModel {

    @CreationTimestamp
    @JsonIgnore
    private Date createdAt;
    @UpdateTimestamp
    @JsonIgnore
    private Date updatedAt;
}
