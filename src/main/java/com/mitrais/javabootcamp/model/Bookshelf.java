package com.mitrais.javabootcamp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "t_bookshelf")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bookshelf {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String shelfId;

    @NotNull
    @NotBlank
    private Long capacity;

    @ManyToMany
    @JoinTable(
            name = "t_book_list",
            joinColumns = @JoinColumn(name = "bookshelf_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    private List<Book> bookList;

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
        this.shelfId = UUID.randomUUID().toString();
        this.createTime = new Date();
    }

    @PreUpdate
    public void preUpdate() {
        this.updateTime = new Date();
    }

}
