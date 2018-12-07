package com.mitrais.javabootcamp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    private String username;

    private String password;

    @ManyToMany
    @JoinTable(
            name = "t_user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roleList = new ArrayList<>();


    @Transient
    private List<Long> roleIdList = new ArrayList<>();

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
