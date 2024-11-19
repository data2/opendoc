package com.data2.opendoc.manager.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.data2.opendoc.manager.server.domain.Article;
import com.data2.opendoc.manager.server.domain.Discussion;
import com.data2.opendoc.manager.server.mapper.ArticleMapper;
import com.data2.opendoc.manager.server.mapper.DiscussionMapper;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author auto-generated
 * @date 2024/11/16
 */
@RestController(value = "discussion")
public class DiscussionController {

    @Autowired
    private DiscussionMapper discussionMapper;

    @GetMapping("selectById")
    public Discussion selectById(Integer id) {
        return discussionMapper.selectById(id);
    }

    @GetMapping("updateById")
    public Boolean updateById(Discussion discussion) {
        return discussionMapper.updateById(discussion)==1;
    }

    @GetMapping("deleteById")
    public Boolean deleteById(Integer id) {
        return discussionMapper.deleteById(id)==1;
    }

    @GetMapping("selectPage")
    public IPage<Discussion> selectPage(Page page, Discussion discussion) {
        if (page == null){
            page = new Page(1,10);
        }else{
            page.setSize(10);
        }

        QueryWrapper<Discussion> discussionQueryWrapper = new QueryWrapper<>();
        if (discussion.getUserId() != null){
            discussionQueryWrapper.lambda().eq(Discussion::getUserId,discussion.getUserId());
        }
        if (discussion.getArticleId() != null) {
            discussionQueryWrapper.lambda().eq(Discussion::getArticleId, discussion.getArticleId());
        }
        discussionQueryWrapper.orderByDesc("createdAt");
        return discussionMapper.selectPage(page, discussionQueryWrapper);
    }
}
