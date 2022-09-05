package com.hostit.exhibitionplatform.domain.article.repository;


import com.hostit.exhibitionplatform.domain.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ArticleRepository extends JpaRepository<Article, Integer> {
}