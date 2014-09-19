package com.whut.dao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.whut.core.utils.ReflectUtils;


@Repository
public class BaseDao<T, PK extends Serializable> implements DaoInterface<T, PK > {
	
	@Autowired    
    private SessionFactory sessionFactory;    
        
    public Session getSession(){    
        return sessionFactory.getCurrentSession();    
    } 

    /**
	 * 实体类型对应的实体类
	 */
	protected Class<T> entityClass;

	/**
	 * 初始化 实体类 的反射类型
	 */
	public BaseDao() {
		entityClass = ReflectUtils
				.getClassGenricType(getClass());
	}
	public T get(PK id) {
		return (T) getSession().get(entityClass,id);
	}
	@Override
	public List<T> getAll() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public <X> X get(Class<X> clazz, Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public <X> List<X> getAll(Class<X> entityClass) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void delete(Object entity) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void delete(PK id) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public <X> void delete(Class<X> clazz, Serializable id) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void save(Object entity) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void update(Object entity) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public List<T> findByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public T merge(T detachedInstance) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void attachClean(T instance) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public <X> List<X> queryByHQL(Class<X> entityClass, String hql) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public <X> List<X> queryByHQL(String hql) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List queryBySQL(String sql) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void executeHQL(String hql) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void executeSQL(String sql) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Object getValByHql(String hql) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Object getValBySql(String sql) {
		// TODO Auto-generated method stub
		return null;
	}

	

	
}
