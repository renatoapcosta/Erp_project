package com.nextech.erp.daoImpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.nextech.erp.dao.SuperDao;

public class SuperDaoImpl<T> implements SuperDao<T>{

	@Autowired
	SessionFactory sessionFactory;
	Session session = null;
	Transaction tx = null;
	
	@Override
	public Long add(T bean) throws Exception {
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		Long id = (Long) session.save(bean);
		tx.commit();
		session.close();
		return id;
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public T getById(Class<?> z,long id) throws Exception {
		session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(z);
		criteria.add(Restrictions.eq("isactive", true));
		criteria.add(Restrictions.eq("id", id));
		return criteria.list().size() > 0 ? (T) criteria.list().get(0)
				: null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getList(Class<?> z) throws Exception {
		session = sessionFactory.openSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(z);
		criteria.add(Restrictions.eq("isactive", true));
		List<T> list = criteria.list();
		session.close();
		return list;
	}

	@Override
	public boolean delete(Class<?> z,long id) throws Exception {
		session = sessionFactory.openSession();
		Object o = session.load(z, id);
		tx = session.getTransaction();
		session.beginTransaction();
		session.delete(o);
		tx.commit();
		return true;
	}

	@Override
	public T update(T bean) throws Exception {
		session = sessionFactory.openSession();
		session.beginTransaction();
		session.update(bean);
		session.getTransaction().commit();
		session.close();
		return bean;
	}

}