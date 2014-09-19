package com.whut.dao;

import java.io.Serializable;
import java.util.List;



/**
 * Data Access Object interface
 * 
 * @author Administrator
 * 
 * @param <T>
 * @param <PK>
 */
public interface DaoInterface<T, PK extends Serializable> {

	/**
	 * 根据id获得对象
	 * 
	 * @param id
	 *            主键ID
	 */
	public T get(PK id);

	/**
	 * 获取所有数据
	 */
	public List<T> getAll();

	
	/**
	 * 根据实体类与ID获得对象
	 * 
	 * @param clazz
	 *            实体类
	 * @param id
	 *            主键ID
	 */
	public <X> X get(final Class<X> clazz, final Serializable id);

	/**
	 * 获取所有数据
	 * 
	 * @param entityClass
	 *            参数X的反射类型
	 */
	public <X> List<X> getAll(final Class<X> entityClass);

	
	/**
	 * 删除对象
	 * 
	 * @param entity
	 *            实体类
	 */
	public void delete(final Object entity);

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 *            主键ID
	 */
	public void delete(final PK id);

	/**
	 * 根据实体类与ID删除对象
	 * 
	 * @param clazz
	 *            实体类
	 * @param id
	 *            主键ID
	 */
	public <X> void delete(final Class<X> clazz, final Serializable id);

	/**
	 * 保存对象
	 * 
	 * @param entity
	 *            保存的实体对象
	 */
	public void save(final Object entity);

	/**
	 * 更新对象
	 */
	public void update(final Object entity);

	/**
	 * 通过相应的 属性名与属性值进行查询
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public List<T> findByProperty(final String propertyName, final Object value);

	
	/**
	 * 用hql语句查询，结果集里面只有一个实体类的对象集
	 */
	public <X> List<X> queryByHQL(Class<X> entityClass, final String hql);
	/**
	 * 用hql语句查询，结果集里面只有一个实体类的对象集
	 * @param <X>
	 * @param hql
	 * @return
	 */
	public <X> List<X> queryByHQL( final String hql);

	
	/**
	 * 用sql语句查询
	 */
	@SuppressWarnings("unchecked")
	public List queryBySQL(final String sql);
	/**
	 * 执行Hql的非select语句
	 * @param hql
	 */
	public void executeHQL(final String hql);
	/**
	 * 执行sql的非select语句
	 */
	public void executeSQL(final String sql);
	/**
	 * 获取查询结果集里面的第一行的第一列的值，必须为整型
	 * @param hql
	 * @return
	 */
	public Object getValByHql(final String hql);
	/**
	 * 获取sql查询结果集里面的第一行的第一列的值，必须为整型
	 * @param sql
	 * @return
	 */
	public Object getValBySql(final String sql);
	
//	public int totalRecord(final String hql);

}
