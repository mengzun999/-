package com.utils;
 
import java.util.List;

 
/**
 * @author Administrator
 */
public interface GenericDao<T, PK extends Number> {
 
	 /**
	  * 执行聚合函数
	  * @param sql
	  * @return
	  */
	 int executeScalre(String sql);
	 int executeScalre(String sql,Object object);
	 int executeScalre(String sql,Object[] args);
	
	 /**
	  * 列表
	  * @param select
	  * @param args
	  * @return
	  */
	 List<T> findAll(String select,Object[] args);
	 List<T> findAll(String select,Object obj);
	 List<T> findAll(String select);
	 
	 /**
	  * 查询对象
	  * @param select
	  * @param args
	  * @return
	  */
	 T findObject(String select,Object[] args);
	 T findObject(String select,Object obj);
	 T findObject(String select);
	 
	 int save(String insert,Object...args);
	 int delete(String delete,Integer id);
	 int update(String update,Object...args);
	 
	 int getTotal(String sql,Object[] args);
	 int getTotal(String sql,Object object);
	 int getTotal(String sql);
	 
	 /**
	  * 分页
	  * @param sql
	  * @param currentPage
	  * @param pagesize
	  * @return
	  */
	 com.utils.Pager<T> pagelist(String sql, String currentPage, int pagesize);
	com.utils.Pager<T> pagelist(String sql, String currentPage, int pagesize, Object obj);
	com.utils.Pager<T> pagelist(String sql, String currentPage, int pagesize, Object[] args);
}