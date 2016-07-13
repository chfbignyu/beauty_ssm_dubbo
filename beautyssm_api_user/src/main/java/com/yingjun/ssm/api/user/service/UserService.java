package com.yingjun.ssm.api.user.service;

import java.util.List;

import com.yingjun.ssm.api.user.entity.User;



public interface UserService {

	List<User> getUserList(int offset, int limit);
	 
}
