package org.yang.controller;

import org.yang.entity.Article;
import org.yang.entity.Category;
import org.yang.entity.User;
import org.yang.service.ArticleService;
import org.yang.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.tautua.markdownpapers.Markdown;
import org.tautua.markdownpapers.parser.ParseException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.List;

@Controller
public class ArticleController {
    @Resource
    private ArticleService articleService;
    @Resource
    private UserService userService;

    @RequestMapping("/")
    public String index(Model model){
        List<Article> articles = articleService.getFirst10Article();
        for (Article article : articles){
            System.out.println(articles.size());
            System.out.println(article.getCategory());
        }
        model.addAttribute("articles",articles);
        return "views/index";
    }
}
