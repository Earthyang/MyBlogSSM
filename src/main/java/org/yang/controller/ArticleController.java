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


/**
 * 核心控制器
 * PathVariable 获取占位符的值
 * @author yang
 * @date 2018-12-20
 */
@Controller
public class ArticleController {
    @Resource
    private ArticleService articleService;
    @Resource
    private UserService userService;

    @RequestMapping("/detail/{id}/firstPage")
    public String detail(@PathVariable("id") Long id, Model model){
        Article article = articleService.getArticleById(id);
        Markdown markdown = new Markdown();
        try {
            StringWriter out = new StringWriter();
            markdown.transform(new StringReader(article.getContent()), out);
            out.flush();
            article.setContent(out.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        model.addAttribute("article", article);
        return "views/detail";
    }

    @RequestMapping("/")
    public String index(Model model){
        List<Article> articles = articleService.getFirst10Article();
        model.addAttribute("articles",articles);
        return "views/index";
    }

    @RequestMapping("/column/{displayName}/{category}")
    public String column(@PathVariable("category") String category , Model model , @PathVariable("displayName") String displayName){
        model.addAttribute("articles",articleService.getArticlesByCategoryName(category));
        model.addAttribute("displayName",displayName);
        return "views/columnPage";
    }

    @RequestMapping("/yang")
    public String admin(Model model){
        model.addAttribute("articles",articleService.getFirst10Article());
        return "admin/index";
    }

    @RequestMapping("/yang/login")
    public String login() {
        return "admin/login";
    }

    @RequestMapping(method = RequestMethod.POST ,value = "/yang/dologin")
    public String dologin(HttpServletRequest request , Model model ,User user){
        if(userService.login(user.getUsername(),user.getPassword())){
            request.getSession().setAttribute("user",user);
            model.addAttribute("user",user);
            return "redirect:/yang";
        }
        else{
            model.addAttribute("error", "用户名或密码错误");
            return "admin/login";
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/yang/dologin")
    public String dologin(HttpServletRequest request, Model model) {
        if (request.getSession().getAttribute("user") == null) {
            return "admin/login";
        }
        return "redirect:/yang";
    }

    @RequestMapping("/yang/write")
    public String write(Model model){
        List<Category> categories = articleService.getCategories();
        categories.remove(0);
        model.addAttribute("categories",categories);
        return "admin/write";
    }

    @RequestMapping(method = RequestMethod.POST , value = "/yang/write")
    public String write(Article article){
        if(article.getId() == 0L){
            articleService.writeBlog(article);
        }else{
            articleService.updateBlog(article);
        }
        return "redirect:/yang";
    }

    @RequestMapping("/yang/update/{id}")
    public String update(@PathVariable("id") Long id, Model model) {
        Article article = articleService.getArticleById(id);
        model.addAttribute("article", article);
        return "admin/write";
    }

    @RequestMapping("/yang/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        articleService.deleteArticleById(id);
        return "redirect:/yang";
    }


}
