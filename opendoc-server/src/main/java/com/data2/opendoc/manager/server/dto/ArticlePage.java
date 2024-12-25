package com.data2.opendoc.manager.server.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@ToString
public class ArticlePage  implements Serializable {
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

    private Integer pageNum;
    private Integer pageSize;
    public void setPageParam(Integer pageSize, Integer pageNum){
        this.pageNum =pageNum;
        this.pageSize=pageSize;
    }
}
