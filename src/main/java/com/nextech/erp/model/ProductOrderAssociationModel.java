package com.nextech.erp.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

public class ProductOrderAssociationModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2264984414287739733L;
	private String description;
	private long client;
	private long status;
	private Date deliveryDate;	
	private Date createDate;	
	private List<Productorderassociation> orderproductassociations;
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public long getClient() {
		return client;
	}
	public void setClient(long client) {
		this.client = client;
	}
	public Date getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public List<Productorderassociation> getOrderproductassociations() {
		return orderproductassociations;
	}
	public void setOrderproductassociations(
			List<Productorderassociation> orderproductassociations) {
		this.orderproductassociations = orderproductassociations;
	}
	public long getStatus() {
		return status;
	}
	public void setStatus(long status) {
		this.status = status;
	}	
	
}
