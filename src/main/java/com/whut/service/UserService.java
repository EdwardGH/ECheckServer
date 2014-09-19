package com.whut.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whut.dao.impl.UserDao;
import com.whut.entity.UserEntity;

@Service
public class UserService {
	@Autowired  
    private UserDao userDao;  
	
	public UserEntity findById(Integer id) {
		return userDao.get(id);
	}
	
	public UserEntity findUniqueByProperty(String propertyName,Object value) {
		return userDao.findUniqueBy(propertyName, value);
	}
}
