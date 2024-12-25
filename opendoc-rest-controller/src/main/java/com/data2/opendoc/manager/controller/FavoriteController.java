package com.data2.opendoc.manager.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.data2.opendoc.manager.aop.filter.JwtUtil;
import com.data2.opendoc.manager.api.dto.output.Resp;
import com.data2.opendoc.manager.server.domain.Article;
import com.data2.opendoc.manager.server.domain.Discussion;
import com.data2.opendoc.manager.server.domain.Favorite;
import com.data2.opendoc.manager.server.domain.User;
import com.data2.opendoc.manager.server.mapper.ArticleMapper;
import com.data2.opendoc.manager.server.mapper.FavoriteMapper;
import com.data2.opendoc.manager.server.utils.UniqueNumberGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author auto-generated
 * @date 2024/11/16
 */
@RestController(value = "")
public class FavoriteController {

    @Autowired
    private FavoriteMapper favoriteMapper;
    @Autowired
    private ArticleMapper articleMapper;

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

    @PostMapping("selectFavoritePage")
    public Resp<IPage> selectFavoritePage(@RequestBody  Page page) {
        if (page == null){
            page = new Page(1,20);
        }else{
            page.setSize(20);
        }

        User user = JwtUtil.getCurrentUsername();
        if (user == null){
            return Resp.fail("未登录");
        }

        QueryWrapper<Favorite> favoriteQueryWrapper = new QueryWrapper<>();
        favoriteQueryWrapper.lambda().eq(Favorite::getUserId,user.getId());
        favoriteQueryWrapper.orderByDesc("created_at");
        Page result = favoriteMapper.selectPage(page, favoriteQueryWrapper);

        if (result == null || result.getRecords().size() == 0){
            return Resp.fail("无");
        }
        Favorite[] favorites = (Favorite[]) result.getRecords().toArray();
        List<Article> articles = articleMapper.selectBatchIds(Arrays.stream(favorites).map(Favorite::getArticleId).collect(Collectors.toList()));

        result.setRecords(articles);
        return new Resp<IPage>().ok(result);

    }

    @PostMapping("favor")
    public Resp<Boolean> favorite(@RequestBody Favorite favorite) {
        if (favorite == null || favorite.getArticleId() == null){
            return Resp.fail("参数为空");
        }

        User user = JwtUtil.getCurrentUsername();
        if (user == null){
            return Resp.fail("未登录");
        }
        favorite.setUserId(user.getId());
        favorite.setCreatedAt(new Date());

        return new Resp<Boolean>().ok(favoriteMapper.insert(favorite)==1);
    }


}
