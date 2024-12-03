package com.data2.opendoc.manager.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.data2.opendoc.manager.aop.filter.JwtFilter;
import com.data2.opendoc.manager.aop.filter.JwtUtil;
import com.data2.opendoc.manager.api.dto.output.Resp;
import com.data2.opendoc.manager.server.domain.Article;
import com.data2.opendoc.manager.server.domain.Discussion;
import com.data2.opendoc.manager.server.domain.User;
import com.data2.opendoc.manager.server.mapper.ArticleMapper;
import com.data2.opendoc.manager.server.mapper.DiscussionMapper;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @author auto-generated
 * @date 2024/11/16
 */
@RestController(value = "")
public class DiscussionController {

    @Autowired
    private DiscussionMapper discussionMapper;

    @GetMapping("selectDiscussionById")
    public Discussion selectById(Integer id) {
        return discussionMapper.selectById(id);
    }

    @GetMapping("updateDiscussionById")
    public Boolean updateById(Discussion discussion) {
        return discussionMapper.updateById(discussion)==1;
    }

    @GetMapping("deleteDiscussionById")
    public Boolean deleteById(Integer id) {
        return discussionMapper.deleteById(id)==1;
    }

    @PostMapping("insertDiscussion")
    public Resp<Boolean> insertDiscussion(@RequestBody Discussion discussion) {
        if (discussion == null){
            return Resp.fail("参数为空");
        }

        User user = JwtUtil.getCurrentUsername();
        discussion.setUserId(user.getId());
        discussion.setNickName(user.getNickName());
        discussion.setCreatedAt(new Date());
        discussion.setUps(0);

        return new Resp<>().ok(discussionMapper.insert(discussion));
    }

    @PostMapping("selectDiscussionPage")
    public Resp<IPage> selectPage( Page page, @RequestBody Discussion discussion) {
        if (page == null){
            page = new Page(1,10);
        }else{
            page.setSize(10);
        }

        if (discussion.getUserId() == null && discussion.getArticleId() == null){
            return Resp.fail("参数错误");
        }

        QueryWrapper<Discussion> discussionQueryWrapper = new QueryWrapper<>();
        if (discussion.getUserId() != null){
            discussionQueryWrapper.lambda().eq(Discussion::getUserId,discussion.getUserId());
        }
        if (discussion.getArticleId() != null){
            discussionQueryWrapper.lambda().eq(Discussion::getArticleId, discussion.getArticleId());
        }

        discussionQueryWrapper.orderByDesc("created_at");
        return new Resp<>().ok(discussionMapper.selectPage(page, discussionQueryWrapper));
    }
}
