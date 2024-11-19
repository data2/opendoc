package com.data2.opendoc.manager.server.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("teams")
public class Teams {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String teamName;
    private Long createdBy;
    private Date createdAt;
}