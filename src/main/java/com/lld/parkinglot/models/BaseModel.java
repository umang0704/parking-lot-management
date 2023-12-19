package com.lld.parkinglot.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import javax.persistence.MappedSuperclass;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class BaseModel {

  @CreationTimestamp @JsonIgnore private Date createdAt;
  @UpdateTimestamp @JsonIgnore private Date updatedAt;
}
