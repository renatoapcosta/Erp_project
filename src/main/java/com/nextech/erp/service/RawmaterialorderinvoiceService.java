package com.nextech.erp.service;

import java.util.List;

import com.nextech.erp.model.Rawmaterialorderinvoice;

public interface RawmaterialorderinvoiceService extends
		CRUDService<Rawmaterialorderinvoice> {
	public List<Rawmaterialorderinvoice> getRawmaterialorderinvoiceByStatusId(
			long id) throws Exception;
}