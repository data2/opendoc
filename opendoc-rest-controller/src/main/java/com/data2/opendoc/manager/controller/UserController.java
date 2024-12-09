package com.data2.opendoc.manager.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.data2.opendoc.manager.aop.filter.JwtUtil;
import com.data2.opendoc.manager.api.dto.output.Resp;
import com.data2.opendoc.manager.config.DocConfig;
import com.data2.opendoc.manager.server.domain.User;
import com.data2.opendoc.manager.server.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.Date;

/**
 * @author auto-generated
 * @date 2024/11/16
 */
@RestController(value = "user")
@Slf4j
public class UserController {
    @Autowired
    private DocConfig docConfig;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("selectUser")
    public Resp<User> selectUser() {
        return new Resp<>().ok(userMapper.selectById(JwtUtil.getCurrentUsername().getId()));
    }

    @GetMapping("selectUserById")
    public User selectUserById(Integer id) {
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
        if (StringUtils.isNotEmpty(user.getEmail())){
            articleQueryWrapper.lambda().eq(User::getEmail,user.getEmail());
        }
        if (StringUtils.isNotEmpty(user.getPhone())) {
            articleQueryWrapper.lambda().eq(User::getPhone, user.getPhone());
        }
        articleQueryWrapper.orderByDesc("created_at");
        return userMapper.selectPage(page, articleQueryWrapper);
    }

    @PostMapping("regist")
    public Resp<Boolean> regist(@RequestBody User user) {
        if (user== null || StringUtils.isAnyEmpty(user.getPassword(),user.getNickName(),user.getEmail())){
            return Resp.fail( "传参为空");
        }
        user.setCreatedAt(new Date());
        user.setLevel(0);
        return new Resp<Boolean>().ok(userMapper.insert(user) == 1);
    }
    @PostMapping("logout")
    public Resp<Boolean> logout() {
        JwtUtil.expire(JwtUtil.getCurrentUsername().getPassword());
        return new Resp<Boolean>().ok(true);
    }
    @PostMapping("login")
    public Resp<Boolean> login(@RequestBody  User user, HttpServletResponse response) {
        if (user== null || StringUtils.isAnyEmpty(user.getPassword(),user.getEmail())){
            return Resp.fail( "传参为空");
        }
        User querUser = new User();
        querUser.setEmail(user.getEmail());
        IPage<User> res = selectPage(new Page(1, 1), querUser);
        if (res!=null && res.getRecords().size() != 0){
            User queryUser = res.getRecords().get(0);
            if (StringUtils.equals(user.getPassword(),queryUser.getPassword())){
                String token = JwtUtil.generateToken(user.getEmail());
                log.info(token);
                Cookie cookie = new Cookie("token", URLEncoder.encode(token));
                cookie.setDomain(docConfig.getCookieDomain());
                response.addCookie(cookie);
                return new Resp<Boolean>().ok(true);
            }
        }
        return Resp.fail("账号登陆失败");
    }
}
