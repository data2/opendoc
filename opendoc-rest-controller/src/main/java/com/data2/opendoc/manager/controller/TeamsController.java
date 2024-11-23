package com.data2.opendoc.manager.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.data2.opendoc.manager.server.domain.Teams;
import com.data2.opendoc.manager.server.mapper.TeamsMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author auto-generated
 * @date 2024/11/16
 */
@RestController(value = "")
public class TeamsController {

    @Autowired
    private TeamsMapper teamsMapper;

    @GetMapping("selectTeamsById")
    public Teams selectById(Integer id) {
        return teamsMapper.selectById(id);
    }

    @GetMapping("updateTeamsById")
    public Boolean updateById(Teams teams) {
        return teamsMapper.updateById(teams)==1;
    }

    @GetMapping("deleteTeamsById")
    public Boolean deleteById(Integer id) {
        return teamsMapper.deleteById(id)==1;
    }

    @GetMapping("selectTeamsPage")
    public IPage<Teams> selectPage(Page page, Teams teams) {
        if (page == null){
            page = new Page(1,10);
        }else{
            page.setSize(10);
        }

        QueryWrapper<Teams> teamsQueryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(teams.getTeamName())){
            teamsQueryWrapper.lambda().like(Teams::getTeamName,teams.getTeamName());
        }
        if (teams.getId() != null) {
            teamsQueryWrapper.lambda().eq(Teams::getId, teams.getId());
        }
        teamsQueryWrapper.orderByDesc("created_at");
        return teamsMapper.selectPage(page, teamsQueryWrapper);
    }
}
