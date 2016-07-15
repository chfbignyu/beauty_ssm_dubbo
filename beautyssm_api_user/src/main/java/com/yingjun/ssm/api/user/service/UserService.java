package com.yingjun.ssm.api.user.service;

import com.yingjun.ssm.api.user.entity.User;

import java.util.List;



public interface UserService {

	List<User> getUserList(int offset, int limit);
	 
}
