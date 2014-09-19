package com.whut.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.whut.dao.BaseDao;
import com.whut.entity.UserEntity;

@Repository
public class UserDao extends BaseDao<UserEntity,Integer>{
	/*@Autowired    
    private SessionFactory sessionFactory;    
        
    public Session getSession(){    
        return sessionFactory.getCurrentSession();    
    } 
    
    public UserEntity get(int id) {
    	Session session = getSession();
		return  (UserEntity) getSession().get(UserEntity.class,id);
	}*/
}
