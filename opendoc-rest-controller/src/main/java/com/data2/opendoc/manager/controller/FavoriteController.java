package com.data2.opendoc.manager.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.data2.opendoc.manager.server.domain.Article;
import com.data2.opendoc.manager.server.domain.Discussion;
import com.data2.opendoc.manager.server.domain.Favorite;
import com.data2.opendoc.manager.server.mapper.ArticleMapper;
import com.data2.opendoc.manager.server.mapper.FavoriteMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author auto-generated
 * @date 2024/11/16
 */
@RestController(value = "")
public class FavoriteController {

    @Autowired
    private FavoriteMapper favoriteMapper;

    @GetMapping("selectFavoriteById")
    public Favorite selectById(Integer id) {
        return favoriteMapper.selectById(id);
    }

    @GetMapping("updateFavoriteById")
    public Object updateById(Favorite favorite) {
        return favoriteMapper.updateById(favorite);
    }

    @GetMapping("deleteFavoriteById")
    public Boolean deleteById(Integer id) {
        return favoriteMapper.deleteById(id)==1;
    }

    @GetMapping("selectFavoritePage")
    public IPage<Favorite> selectPage(Page page, Favorite favorite) {
        if (page == null){
            page = new Page(1,10);
        }else{
            page.setSize(10);
        }

        QueryWrapper<Favorite> favoriteQueryWrapper = new QueryWrapper<>();
        if (favorite.getUserId() != null){
            favoriteQueryWrapper.lambda().eq(Favorite::getUserId,favorite.getUserId());
        }
        if (favorite.getArticleId() != null) {
            favoriteQueryWrapper.lambda().eq(Favorite::getArticleId, favorite.getArticleId());
        }
        favoriteQueryWrapper.orderByDesc("created_at");
        return favoriteMapper.selectPage(page, favoriteQueryWrapper);
    }
}
