package com.whut.service;

import java.util.List;

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
	
	public int saveUser(UserEntity entity){
		try {
			userDao.save(entity);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	public List<UserEntity> getLatestN(int num){
		return userDao.getLatestN(num);
	}
	
}
