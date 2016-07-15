package com.yingjun.ssm.core.goods.dao;

import com.yingjun.ssm.api.user.entity.User;


public interface UserDao {

	/**
     * 根据手机号查询用户对象
     * @param userPhone
     * @return
     */
    User queryByPhone(long userPhone);

}
