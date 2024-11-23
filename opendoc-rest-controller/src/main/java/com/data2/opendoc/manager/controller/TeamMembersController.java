package com.data2.opendoc.manager.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.data2.opendoc.manager.server.domain.TeamMembers;
import com.data2.opendoc.manager.server.domain.Teams;
import com.data2.opendoc.manager.server.mapper.TeamMembersMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author auto-generated
 * @date 2024/11/16
 */
@RestController(value = "")
public class TeamMembersController {

    @Autowired
    private TeamMembersMapper teamMembersMapper;

    @GetMapping("selectTeammembersById")
    public TeamMembers selectById(Integer id) {
        return teamMembersMapper.selectById(id);
    }

    @GetMapping("updateTeammembersById")
    public Boolean updateById(TeamMembers teamMembers) {
        return teamMembersMapper.updateById(teamMembers)==1;
    }

    @GetMapping("deleteTeammembersById")
    public Boolean deleteById(Integer id) {
        return teamMembersMapper.deleteById(id)==1;
    }

    @GetMapping("selectTeammembersPage")
    public IPage<Teams> selectPage(Page page, TeamMembers teamMembers) {
        if (page == null){
            page = new Page(1,10);
        }else{
            page.setSize(10);
        }

        QueryWrapper<TeamMembers> teamsQueryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(teamMembers.getTeamName())){
            teamsQueryWrapper.lambda().like(TeamMembers::getTeamName,teamMembers.getTeamName());
        }
        if (teamMembers.getTeamId() != null) {
            teamsQueryWrapper.lambda().eq(TeamMembers::getTeamId, teamMembers.getTeamId());
        }
        if (StringUtils.isNotEmpty(teamMembers.getNickName())){
            teamsQueryWrapper.lambda().like(TeamMembers::getNickName,teamMembers.getNickName());
        }
        if (teamMembers.getUserId() != null) {
            teamsQueryWrapper.lambda().eq(TeamMembers::getUserId, teamMembers.getUserId());
        }
        teamsQueryWrapper.orderByDesc("joined_at");
        return teamMembersMapper.selectPage(page, teamsQueryWrapper);
    }
}
