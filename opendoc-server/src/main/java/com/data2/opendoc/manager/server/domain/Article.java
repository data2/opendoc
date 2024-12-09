package com.data2.opendoc.manager.server.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("article")
public class Article {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private String nickName;
    private String title;
    private String description;
    private String tag;
    private Long teamId;
    private String content;
    private Date createdAt;
    private Integer isPublic;//0 私有 1 公开 2团队公开
    private Integer isOfficial;
    private Integer views;
    private Integer comments;
    private Integer ups;


}