package com.data2.opendoc.manager.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.data2.opendoc.manager.server.domain.Article;
import com.data2.opendoc.manager.server.mapper.ArticleMapper;
import com.data2.opendoc.manager.server.mapper.UserMapper;
import io.netty.util.internal.StringUtil;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

/**
 * @author auto-generated
 * @date 2024/11/16
 */
@RestController(value = "")
public class ArticleController {

    @Autowired
    private ArticleMapper articleMapper;

    @GetMapping("selectArticleById")
    public Article selectById(Integer id) {
        return articleMapper.selectById(id);
    }

    @GetMapping("updateArticleById")
    public Boolean updateById(Article article) {
        return articleMapper.updateById(article)==1;
    }

    @GetMapping("deleteArticleById")
    public Boolean deleteById(Integer id) {
        return articleMapper.deleteById(id)==1;
    }

    @GetMapping("selectArticlePage")
    public IPage<Article> selectPage(Page page, Article article) {
        if (page == null){
            page = new Page(1,10);
        }else{
            page.setSize(10);
        }

        QueryWrapper<Article> articleQueryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(article.getTitle())){
            articleQueryWrapper.lambda().like(Article::getTitle,article.getTitle());
        }
        if (article.getUserId() !=null) {
            articleQueryWrapper.lambda().eq(Article::getUserId, article.getUserId());
        }
        articleQueryWrapper.orderByDesc("created_at");
        return articleMapper.selectPage(page, articleQueryWrapper);
    }
}
