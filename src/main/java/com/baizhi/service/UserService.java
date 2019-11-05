package com.baizhi.service;

import java.util.Map;

public interface UserService {

    Map<String,Object> selectUsersByStarId(Integer page,Integer rows,String starId);

}
