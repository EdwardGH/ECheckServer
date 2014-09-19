package com.whut.dao;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.whut.core.utils.BaseFunction;
import com.whut.core.utils.ReflectUtils;

@Repository
public class BaseDao<T, PK extends Serializable> {

	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
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
		entityClass = ReflectUtils.getClassGenricType(getClass());
	}

	private void save1(T entity) {
		Assert.notNull(entity, "entity不能为空");
		Session session = getSession();
		Transaction tran = null;
		try {
			if (session != null) {
				tran = session.beginTransaction();
				tran.begin();
				session.saveOrUpdate(entity);
				tran.commit();
			}
		} catch (Exception ex) {
			tran.rollback();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		// this.logger.debug("save entity: {}", entity);
	}

	public void save(T entity) {
		Assert.notNull(entity, "entity不能为空");
		getSession().saveOrUpdate(entity);
		// this.logger.debug("save entity: {}", entity);
	}

	public void delete(T entity) {
		Assert.notNull(entity, "entity不能为空");
		getSession().delete(entity);
		// this.logger.debug("delete entity: {}", entity);
	}

	private void delete1(T entity) {
		Assert.notNull(entity, "entity不能为空");
		Session session = getSession();
		Transaction tran = null;
		try {
			if (session != null) {
				tran = session.beginTransaction();
				tran.begin();
				session.delete(entity);
				tran.commit();
			}
		} catch (Exception ex) {
			tran.rollback();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		// this.logger.debug("delete entity: {}", entity);
	}

	public void delete(PK id) {
		Assert.notNull(id, "id不能为空");
		delete(get(id));
		// this.logger.debug("delete entity {},id is {}",
		// this.entityClass.getSimpleName(), id);
	}

	public T get(PK id) {
		Assert.notNull(id, "id不能为空");
		return (T) getSession().load(this.entityClass, id);
	}

	public List<T> getAll() {
		return find(new Criterion[0]);
	}

	public List<T> getAll(String orderBy, boolean isAsc) {
		Criteria c = createCriteria(new Criterion[0]);
		if (isAsc)
			c.addOrder(Order.asc(orderBy));
		else {
			c.addOrder(Order.desc(orderBy));
		}
		return c.list();
	}

	public List<T> findBy(String propertyName, Object value) {
		Assert.hasText(propertyName, "propertyName不能为空");
		Criterion criterion = Restrictions.eq(propertyName, value);
		return find(new Criterion[] { criterion });
	}

	public T findUniqueBy(String propertyName, Object value) {
		Assert.hasText(propertyName, "propertyName不能为空");
		if (value == null) {
			return null;
		}
		if ((value instanceof String)) {
			if (BaseFunction.isEmpty((String) value) == true) {
				return null;
			}

		} else if ((value instanceof Long)) {
			Long temp = (Long) value;
			if (temp.intValue() <= 0) {
				return null;
			}

		} else if ((value instanceof Integer)) {
			Integer temp = (Integer) value;
			if (temp.intValue() <= 0) {
				return null;
			}

		}

		Criterion criterion = Restrictions.eq(propertyName, value);
		return (T) createCriteria(new Criterion[] { criterion }).uniqueResult();
	}

	public List<T> findByIds(List<PK> ids) {
		return find(new Criterion[] { Restrictions.in(getIdName(), ids) });
	}

	public <X> List<X> find(String hql) {
		return createQuery(hql).list();
	}

	public <X> List<X> find(String hql, Object[] values) {
		return createQuery(hql, values).list();
	}

	public <X> List<X> find(String hql, Map<String, ?> values) {
		return createQuery(hql, values).list();
	}

	public <X> X findUnique(String hql) {
		return (X) createQuery(hql).uniqueResult();
	}

	public <X> X findUnique(String hql, Object[] values) {
		return (X) createQuery(hql, values).uniqueResult();
	}

	public <X> X findUnique(String hql, Map<String, ?> values) {
		return (X) createQuery(hql, values).uniqueResult();
	}

	public int batchExecute(String hql, Object[] values) {
		return createQuery(hql, values).executeUpdate();
	}

	public int batchExecute(String hql, Map<String, ?> values) {
		return createQuery(hql, values).executeUpdate();
	}

	public Query createQuery(String queryString) {
		Assert.hasText(queryString, "queryString不能为空");
		Query query = getSession().createQuery(queryString);
		return query;
	}

	public Query createQuery(String queryString, Object[] values) {
		Assert.hasText(queryString, "queryString不能为空");
		Query query = getSession().createQuery(queryString);
		if ((values != null) && (values.length > 0)) {
			if ((values[0] instanceof Hashtable)) {
				Hashtable temp = (Hashtable) values[0];
				query = setParamHash(query, temp);
			} else if ((values[0] instanceof List)) {
				List temp = (List) values[0];
				int size = temp.size();
				for (int i = 0; i < size; i++) {
					Object param = temp.get(i);

					if ((param instanceof String)) {
						String paramValue = (String) param;
						query.setString(i, paramValue);
					} else if ((param instanceof Integer)) {
						Integer paramValue = (Integer) param;
						query.setInteger(i, paramValue.intValue());
					} else if ((param instanceof Long)) {
						Long paramValue = (Long) param;
						query.setLong(i, paramValue.longValue());
					} else if ((param instanceof Double)) {
						Double paramValue = (Double) param;
						query.setDouble(i, paramValue.doubleValue());
					} else if ((param instanceof Float)) {
						Float paramValue = (Float) param;
						query.setFloat(i, paramValue.floatValue());
					}

				}

			} else {
				for (int i = 0; i < values.length; i++) {
					query.setParameter(i, values[i]);
				}

			}

		}

		return query;
	}

	public Query createQuery(String queryString, Map<String, ?> values) {
		Assert.hasText(queryString, "queryString不能为空");
		Query query = getSession().createQuery(queryString);
		if (values != null) {
			query.setProperties(values);
		}
		return query;
	}

	private Query setParamHash(Query query, Hashtable<String, ?> values) {
		if (values != null) {
			Enumeration parameterNames = values.keys();
			while (parameterNames.hasMoreElements() == true) {
				String pName = (String) parameterNames.nextElement();
				Object param = values.get(pName);
				if ((param instanceof String)) {
					String paramValue = (String) param;
					query.setString(pName, paramValue);
				} else if ((param instanceof Integer)) {
					Integer paramValue = (Integer) param;
					query.setInteger(pName, paramValue.intValue());
				} else if ((param instanceof Double)) {
					Double paramValue = (Double) param;
					query.setDouble(pName, paramValue.doubleValue());
				}

			}

		}

		return query;
	}

	public List<T> find(Criterion[] criterions) {
		return createCriteria(criterions).list();
	}

	public T findUnique(Criterion[] criterions) {
		return (T) createCriteria(criterions).uniqueResult();
	}

	public Criteria createCriteria(Criterion[] criterions) {
		Criteria criteria = getSession().createCriteria(this.entityClass);
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}

	public void initEntity(T entity) {
		Hibernate.initialize(entity);
	}

	public void initEntity(List<T> entityList) {
		for (Iterator i$ = entityList.iterator(); i$.hasNext();) {
			Object entity = i$.next();
			Hibernate.initialize(entity);
		}
	}

	public Query distinct(Query query) {
		query.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return query;
	}

	public Criteria distinct(Criteria criteria) {
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return criteria;
	}

	public String getIdName() {
		ClassMetadata meta = sessionFactory.getClassMetadata(this.entityClass);
		return meta.getIdentifierPropertyName();
	}
}
