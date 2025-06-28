package com.utils;

import java.util.List;

 
 
public class GenericDaoImpl<T, PK extends Number> implements GenericDao<T, Number> {
 
	private Class<T> clazz;
	
	protected GenericDaoImpl() {
		this.clazz=(Class<T>)((java.lang.reflect.ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
 
	
 
	@Override
	public int save(String insert,Object...args) {
		
		return com.utils.BaseDao.executeCommand(insert, args);
	}
 
	@Override
	public int delete(String delete,Integer id) {
		
		return com.utils.BaseDao.executeCommand(delete, id);
	}
 
	@Override
	public int update(String udpate,Object...args) {
		
		return com.utils.BaseDao.executeCommand(udpate, args);
	}
	@Override
	public List<T> findAll(String select, Object[] args) {
		
		return com.utils.BaseDao.findAll(select, clazz, args);
	}
	@Override
	public List<T> findAll(String select, Object obj) {
		
		return this.findAll(select, new Object[]{obj});
	}
	
	@Override
	public List<T> findAll(String sql) {
		
		return this.findAll(sql, null);
	}
	@Override
	public T findObject(String select, Object[] args) {
		// TODO Auto-generated method stub
		return com.utils.BaseDao.findByObject(select, clazz, args);
	}
	@Override
	public T findObject(String select, Object obj) {
		// TODO Auto-generated method stub
		return this.findObject(select, new Object[]{obj});
	}
	@Override
	public T findObject(String select) {
		// TODO Auto-generated method stub
		return this.findObject(select, null);
	}
 
 
	@Override
	public com.utils.Pager<T> pagelist(String sql, String currentPage, int pagesize, Object[] args) {
		
		return com.utils.BaseDao.pageAll(sql, clazz, currentPage, pagesize, args);
	}
 
 
	@Override
	public com.utils.Pager<T> pagelist(String sql, String currentPage, int pagesize) {
		
		return this.pagelist(sql, currentPage,pagesize, null);
	}
 
 
	@Override
	public com.utils.Pager<T> pagelist(String sql, String currentPage, int pagesize, Object obj) {
		
		return this.pagelist(sql, currentPage, pagesize, new Object[]{obj});
	}
 
 
	@Override
	public int getTotal(String sql, Object[] args) {
		
		return com.utils.BaseDao.executeScalare(sql, args);
	}
 
 
	@Override
	public int getTotal(String sql, Object object) {
		// TODO Auto-generated method stub
		return this.getTotal(sql, new Object[]{object});
	}
 
	@Override
	public int getTotal(String sql) {
		return this.getTotal(sql, null);
	}
 
	@Override
	public int executeScalre(String sql) {
		return this.executeScalre(sql, null);
	}
 
	@Override
	public int executeScalre(String sql, Object object) {
		
		return this.executeScalre(sql, new Object[]{object});
	}
 
	@Override
	public int executeScalre(String sql, Object[] args) {
		return com.utils.BaseDao.executeScalare(sql, args);
	}
 
}