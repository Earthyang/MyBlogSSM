package org.yang.service;

import org.springframework.stereotype.Service;
import org.yang.dao.ArticleDao;
import org.yang.entity.Article;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author yang
 * @date 2018-12-18
 */
@Service
public class ArticleService {
    @Resource
    private ArticleDao articleDao;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public Article getArticleById(long id){
        Article article = articleDao.getArticleById(id);
        article.setCategory(articleDao.getCategoryById(article.getCategoryId()).getDisplayName());
        return article;
    }

    public List<Article> getFirst10Article(){
        return articleDao.getFirst10Article();
    }

}
