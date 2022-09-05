package com.hostit.exhibitionplatform.domain.article.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Article")

public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(nullable = false)
    private int makerId;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String content;

    private String name;
    @Column(nullable = false)
    private OffsetDateTime createdAt;

    private String createdBy;

    private OffsetDateTime modifiedAt;

    private String modifiedBy;

}