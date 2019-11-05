package com.baizhi.controller;

import com.baizhi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequestMapping("user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("selectUsersByStarId")
    public Map<String,Object> selectUsersByStarId(Integer page,Integer rows,String starId){
        return userService.selectUsersByStarId(page,rows,starId);
    }

}
