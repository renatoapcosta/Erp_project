package com.nextech.erp.service;

import java.util.List;

public interface CRUDService<T> {
	T getEntityById(Class<?> z, long id) throws Exception;

	List<T> getEntityList(Class<?> z) throws Exception;

	boolean deleteEntity(Class<?> z, long id) throws Exception;

	T updateEntity(T bean) throws Exception;

	Long addEntity(T bean) throws Exception;
}