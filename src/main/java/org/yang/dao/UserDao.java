package org.yang.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.yang.entity.User;

/**
 * 定义对user的操作,Repository标签是用来给持久层的类定义一个名字，让Spring根据这个名字关联到这个类。
 * @Time 2018-12-18
 */
@Repository
public interface UserDao {
    /**
     * 根据用户名与密码查询用户信息
     * @param username [in] 用户名
     * @param password [in] 密码
     * @return User
     */
    public User getUser(@Param("username") String username, @Param("password") String password);
}
