package com.hostit.exhibitionplatform.domain.article.service;


import com.hostit.exhibitionplatform.domain.article.entity.Article;
import com.hostit.exhibitionplatform.domain.article.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ArticleService {
    @Autowired
    private ArticleRepository repository;

    public Article saveArticle(Article article)
    {
        return repository.save(article);
    }

    public List<Article> getArticles()
    {
        return repository.findAll();
    }

    public String deleteArticle(int id)
    {
        repository.deleteById(id);
        return "article deleted ! " + id;
    }

    public Article getArticleById(int id)
    {
        return repository.findById(id).orElse(null);
    }


}