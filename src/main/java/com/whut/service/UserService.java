package com.whut.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whut.dao.impl.UserDao;

@Service
public class UserService {
	@Autowired  
    private UserDao userDao;  
	
	
}
