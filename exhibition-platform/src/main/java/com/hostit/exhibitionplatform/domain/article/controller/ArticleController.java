package com.hostit.exhibitionplatform.domain.article.controller;

import com.hostit.exhibitionplatform.domain.article.entity.Article;
import com.hostit.exhibitionplatform.domain.article.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/article")
@RestController
public class ArticleController {
    @Autowired
    private ArticleService service;

    @PostMapping("/save")
    public Article saveArticle(@RequestBody Article article)
    {
        return service.saveArticle(article);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Article> projectInfo() {
        List<Article> articles = null;
        articles = service.getArticles();
        return articles;
    }

    @GetMapping("/{id}")
    public Article findArticleById(@PathVariable int id) {
        return service.getArticleById(id);
    }

    @PostMapping("/update")
    Article updateArticle (@RequestBody Article article)
    {
        return service.saveArticle(article);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteArticleById(@PathVariable int id) {
        return service.deleteArticle(id);
    }

}