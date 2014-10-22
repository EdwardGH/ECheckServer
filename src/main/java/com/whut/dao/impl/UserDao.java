package com.whut.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.whut.dao.BaseDao;
import com.whut.entity.UserEntity;

@Repository
public class UserDao extends BaseDao<UserEntity,Integer>{
	public List<UserEntity> getLatestN(int num) {
		Criteria c = getSession().createCriteria(entityClass);
		return c.addOrder(Property.forName("username").asc())
		        .setMaxResults(num)
		        .list();
	}
	
	public List<UserEntity> getNextPage(int num) {
		Criteria c = getSession().createCriteria(entityClass);
		return c.addOrder(Property.forName("username").asc())
				.add(Restrictions.lt("date", null))
		        .setMaxResults(num)
		        .list();
	}
}
