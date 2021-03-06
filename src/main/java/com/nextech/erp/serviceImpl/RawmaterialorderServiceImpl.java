package com.nextech.erp.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;

import com.nextech.erp.dao.RawmaterialorderDao;
import com.nextech.erp.model.Rawmaterialorder;
import com.nextech.erp.service.RawmaterialorderService;

public class RawmaterialorderServiceImpl extends CRUDServiceImpl<Rawmaterialorder> implements RawmaterialorderService{
	@Autowired
	RawmaterialorderDao rawmaterialorderDao;
	
	@Override
	public Rawmaterialorder getRawmaterialorderByIdName(long id,String rmname) throws Exception {
		return rawmaterialorderDao.getRawmaterialorderByIdName(id,rmname);
	}

}

