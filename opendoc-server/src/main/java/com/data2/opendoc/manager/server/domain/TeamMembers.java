package com.data2.opendoc.manager.server.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("team_members")
public class TeamMembers {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long teamId;
    private String teamName;
    private Long userId;
    private String nickName;
    private Date joinedAt;
    private Date createdAt;
}