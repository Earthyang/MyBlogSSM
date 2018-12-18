package org.yang.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.yang.entity.Article;
import org.yang.entity.Category;

import java.util.List;

/**
 * 定义对于文章的操作
 * @author yang
 * @Time 2018-12-18
 */
@Repository
public interface ArticleDao {
    /**
     * 由ID查出文章内容
     * @param id [in] ID
     * @return Article
     */
    public Article getArticleById(@Param("id") Long id);

    /**
     * 查询前10篇文章
     * @return List<Article>
     */
    public List<Article> getFirst10Article();

    /**
     * 查出一类文章
     * @param categoryId [in] 类别
     * @return List<Article></>
     */
    public List<Article> getArticlesByCategoryName(Long categoryId);

    /**
     * 查询文章种类
     * @return List<Category></>
     */
    public List<Category> getCategories();

    /**
     * 写入文章
     * @param article [in] 文章对象
     */
    public void writeBlog(Article article);

    /**
     * 由类名查出ID
     * @param name [in] 类名
     * @return 类ID
     */
    public Long getCategoryIdByName(String name);

    /**
     * 删除文章
     * @param id [in] ID
     */
    public void deleteArticleById(Long id);

    /**
     * 根据ID更新文章
     * @param article [in] 文章实例
     */
    public void updateArticleById(Article article);

    /**
     * 由ID查询类别实例
     * @param id [in] ID
     * @return Category 类别实例
     */
    public Category getCategoryById(Long id);

}
