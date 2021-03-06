package com.nextech.erp.daoImpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import com.nextech.erp.dao.RawmaterialorderinvoiceDao;
import com.nextech.erp.model.Rawmaterialorderinvoice;

public class RawmaterialorderinvoiceDaoImpl extends
		SuperDaoImpl<Rawmaterialorderinvoice> implements
		RawmaterialorderinvoiceDao {
	@Autowired
	SessionFactory sessionFactory;
	Session session = null;
	Transaction tx = null;

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public List<Rawmaterialorderinvoice> getRawmaterialorderinvoiceByStatusId(
			long id) throws Exception {
		session = sessionFactory.openSession();
		Criteria criteria = session
				.createCriteria(Rawmaterialorderinvoice.class);
		criteria.add(Restrictions.eq("status.id", id));
		return criteria.list();
	}
}
