package com.data2.opendoc.manager.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.data2.opendoc.manager.server.domain.Article;
import com.data2.opendoc.manager.server.domain.User;
import com.data2.opendoc.manager.server.mapper.ArticleMapper;
import com.data2.opendoc.manager.server.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author auto-generated
 * @date 2024/11/16
 */
@RestController(value = "user")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("selectUserById")
    public User selectById(Integer id) {
        return userMapper.selectById(id);
    }

    @GetMapping("updateUserById")
    public Object updateById(User user) {
        return userMapper.updateById(user);
    }

    @GetMapping("selectUserPage")
    public IPage<User> selectPage(Page page, User user) {
        if (page == null){
            page = new Page(1,10);
        }else{
            page.setSize(10);
        }

        QueryWrapper<User> articleQueryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(user.getUserName())){
            articleQueryWrapper.lambda().eq(User::getUserName,user.getUserName());
        }
        if (StringUtils.isNotEmpty(user.getPhone())) {
            articleQueryWrapper.lambda().eq(User::getPhone, user.getPhone());
        }
        articleQueryWrapper.orderByDesc("created_at");
        return userMapper.selectPage(page, articleQueryWrapper);
    }

    @GetMapping("regist")
    public Boolean regist(User user) {
        if (user== null || StringUtils.isAnyEmpty(user.getPassword(),user.getUserName())){
            return false;
        }
        user.setCreatedAt(new Date());
        user.setLevel(0);
        return userMapper.insert(user) == 1;
    }
    @GetMapping("login")
    public Boolean login(User user) {
        if (user== null || StringUtils.isAnyEmpty(user.getPassword(),user.getUserName())){
            return false;
        }
        IPage<User> res = selectPage(new Page(1, 1), new User(user.getUserName()));
        if (res!=null && res.getRecords().size() != 0){
            User queryUser = res.getRecords().get(0);
            if (StringUtils.equals(user.getPassword(),queryUser.getPassword())){
                return true;
            }
        }
        return false;
    }
}
