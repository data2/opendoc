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
    private String content;
    private Date createdAt;
    private Integer isPublic;
    private Integer isOfficial;
    private Integer views;
    private Integer comments;
    private Integer ups;

}