package com.data2.opendoc.manager.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.data2.opendoc.manager.aop.filter.JwtUtil;
import com.data2.opendoc.manager.api.dto.output.Resp;
import com.data2.opendoc.manager.config.DocConfig;
import com.data2.opendoc.manager.server.domain.Article;
import com.data2.opendoc.manager.server.domain.TeamMembers;
import com.data2.opendoc.manager.server.domain.User;
import com.data2.opendoc.manager.server.mapper.ArticleMapper;
import com.data2.opendoc.manager.server.mapper.TeamMembersMapper;
import com.data2.opendoc.manager.server.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Handler;

/**
 * @author auto-generated
 * @date 2024/11/16
 */
@RestController(value = "")
public class ArticleController {
    @Autowired
    private DocConfig docConfig;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TeamMembersMapper teamMembersMapper;

    @GetMapping("selectArticleById")
    public Resp<Article> selectById(Long id) {
        Map<String,Object> map = new HashMap<>();
        Article article = articleMapper.selectById(id);
        map.put("article", article);
        if (article.getIsPublic() == 0){
            if (JwtUtil.getCurrentUsername().getId()!=article.getUserId()){
                return Resp.fail("无权限");
            }
        }
        if (article.getIsPublic() == 2){
            QueryWrapper<TeamMembers> query = new QueryWrapper<>();
            query.eq("user_id",JwtUtil.getCurrentUsername().getId());
            query.eq("team_id",article.getTeamId());
            if (teamMembersMapper.selectList(query)==null){
                return Resp.fail("无权限");
            }
        }
        User user = userMapper.selectById(article.getUserId());
        user.setPassword(null);
        user.setPhone(null);
        map.put("user", user);

        return new Resp<>().ok(map);
    }

    @GetMapping("updateArticleById")
    public Boolean updateById(Article article) {
        return articleMapper.updateById(article)==1;
    }

    @GetMapping("deleteArticleById")
    public Boolean deleteById(Integer id) {
        return articleMapper.deleteById(id)==1;
    }

    @PostMapping("selectArticlePage")
    public Resp<IPage> selectPage(Page page, @RequestBody Article article) {
        if (page == null){
            page = new Page(1,10);
        }else{
            page.setSize(10);
        }

        QueryWrapper<Article> articleQueryWrapper = new QueryWrapper<>();
        if (article.getIsPublic() != null && article.getIsPublic() != 1){
            if (article.getUserId() !=null) {
                articleQueryWrapper.lambda().eq(Article::getUserId, article.getUserId());
            }
            if (article.getTeamId() !=null) {
                articleQueryWrapper.lambda().eq(Article::getTeamId, article.getTeamId());
            }
        }
        if (article.getIsPublic() != null){
            articleQueryWrapper.lambda().like(Article::getIsPublic,article.getIsPublic());
        }
        if (StringUtils.isNotEmpty(article.getTitle())){
            articleQueryWrapper.lambda().like(Article::getTitle,article.getTitle());
        }
        if (StringUtils.isNotEmpty(article.getDescription())){
            articleQueryWrapper.lambda().like(Article::getDescription,article.getDescription());
        }
        articleQueryWrapper.orderByDesc("created_at");
        return new Resp<IPage>().ok(articleMapper.selectPage(page, articleQueryWrapper));
    }

    @PostMapping("searchArticlePage")
    public Resp<IPage> searchArticlePage(Page page, @RequestBody Article article) {
        if (page == null){
            page = new Page(1,10);
        }else{
            page.setSize(10);
        }

        QueryWrapper<Article> articleQueryWrapper = new QueryWrapper<>();
        if (article.getIsPublic() != null && article.getIsPublic() != 1){
            if (article.getUserId() !=null) {
                articleQueryWrapper.lambda().eq(Article::getUserId, article.getUserId());
            }
            if (article.getTeamId() !=null) {
                articleQueryWrapper.lambda().eq(Article::getTeamId, article.getTeamId());
            }
        }
        if (article.getIsPublic() != null){
            articleQueryWrapper.lambda().like(Article::getIsPublic,article.getIsPublic());
        }
        if (StringUtils.isNotEmpty(article.getTitle())){
            articleQueryWrapper.lambda().like(Article::getTitle,article.getTitle());
        }
        if (StringUtils.isNotEmpty(article.getDescription())){
            articleQueryWrapper.lambda().like(Article::getDescription,article.getDescription());
        }
        articleQueryWrapper.orderByDesc("created_at");
        return new Resp<IPage>().ok(articleMapper.selectPage(page, articleQueryWrapper));
    }

    @GetMapping("selectArticlePagePerson")
    public Resp<IPage> selectArticlePagePerson(Page page) {
        if (page == null){
            page = new Page(1,10);
        }else{
            page.setSize(10);
        }
        User user = JwtUtil.getCurrentUsername();
        Article query = new Article();
        query.setUserId(user.getId());
        return selectPage(page, query);
    }

    @PostMapping("createArticle")
    public Resp<Boolean> createArticle(@RequestBody Article article) {
        if (article == null ||
                StringUtils.isAnyEmpty(article.getContent(),article.getTitle(),article.getTag(),article.getDescription())){
            return Resp.fail("参数为空");
        }

        User user = JwtUtil.getCurrentUsername();
        if (user == null){
            return Resp.fail("未登录");
        }
        article.setUserId(user.getId());
        if (docConfig.getOfficialEmail().contains(user.getEmail())){
            article.setIsOfficial(1);
        }

        IPage page = selectPage(new Page(1, 10), article).getObject();
        if (page!= null && page.getRecords().size() >0){
            return Resp.fail("文章存在相似可能，请修改标题、描述等");
        }

        return new Resp<Boolean>().ok(articleMapper.insert(article)==1);
    }
}
