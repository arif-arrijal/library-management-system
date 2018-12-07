package com.mitrais.javabootcamp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "t_book")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    private String title;

    @NotNull
    @NotBlank
    private String description;

    @NotNull
    @Min(0)
    private Long stock;

    @Version
    private Integer version;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    private String createBy;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;
    private String updateBy;

    @PrePersist
    public void prePersist() {
        this.createTime = new Date();
    }

    @PreUpdate
    public void preUpdate() {
        this.updateTime = new Date();
    }

}
