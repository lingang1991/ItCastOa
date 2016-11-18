package cn.itcast.oa.service;

import cn.itcast.oa.base.DaoSupport;
import cn.itcast.oa.domain.User;

public interface UserService extends DaoSupport<User> {
    /*根据用户名和密码查询用户*/
	User findByLoginNameAndPassword(String loginName, String password);

}
