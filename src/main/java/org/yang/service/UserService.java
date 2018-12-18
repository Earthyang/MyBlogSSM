package org.yang.service;

import org.springframework.stereotype.Service;
import org.yang.dao.UserDao;
import org.yang.entity.User;

import javax.annotation.Resource;

/**
 * 判断用户是否存在
 * Resource : 是JDK1.6支持的注解，默认按照名称进行装配，名称可以通过name属性进行指定
 * @author yang
 * @date 2018-12-18
 */
@Service
public class UserService {
    @Resource
    private UserDao userDao;

    public boolean login(String username,String password){
        User user = userDao.getUser(username, password);
        if (user == null) {
            return false;
        }else{
            return true;
        }
    }
}
