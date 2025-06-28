package com.utils;
 
import java.util.List;
/**
 * 分页工具类
 * @author 宋伟宁
 *
 * @param <T>
 */
public class Pager<T> {
 
	  private int offset;//起始位置：limit offset,pagesize
	  private int currentPage;//当前页面
	  private int nextPage;//下一页
	  private int prevPage;//上一页
	  private int pageCount;//总页数
	  private int total;//总记录数
	  private int pagesize;//每页的记录数
	  private List<T> datas;//分页显示的数据集
	  private String url;				// 请求url
	  private String keyword;			// 要模糊查询的内容
	  private String select;			// 要模糊查询的字段名
	  private String items;  //分页导航
	  
	    //生成导航条
	  public String getItems() {
		  int index = url.indexOf("?");
			if(index > 0){
				url = url + "&";
			}else{
				url = url + "?";
			}
			
			
			StringBuffer buffer = new StringBuffer();
			
			buffer.append("<font size='3'>找到 "+this.total+" 条记录 ");
			if(this.currentPage > 1){
				buffer.append("<a style='color:blue' href='"+url+"currentPage="+1+"&keyword="+keyword+"&select="+select+"'>[首页]</a>");
				buffer.append("<a style='color:blue' href='"+url+"currentPage="+(this.currentPage-1)+"&keyword="+keyword+"&select="+select+"'>[上一页]</a>");
			}
			buffer.append("当前第 "+this.currentPage+"/"+pageCount+" 页");
			
			if(this.currentPage < this.pageCount){
				buffer.append("<a style='color:blue' href='"+url+"currentPage="+(this.currentPage+1)+"&keyword="+keyword+"&select="+select+"'>[下一页]</a>");
				buffer.append("<a style='color:blue' href='"+url+"currentPage="+(this.pageCount)+"&keyword="+keyword+"&select="+select+"'>[末页]</a>");
			}
			
			
			
			buffer.append(" 每页 "+this.pagesize+" 条");
			buffer.append("</font>");
			this.items=buffer.toString();
		return this.items;
	}
 
	
 
	public Pager(int total,String currentPage,int pagesize){//带参构造
		   this.total=total;
		   this.pagesize=pagesize;
		   if(currentPage==null || currentPage.isEmpty()){
			   currentPage="1";
		   }
		   //计算起始位置： 
		   this.currentPage=Integer.parseInt(currentPage);
		   this.offset = (this.currentPage-1)*this.pagesize;
		  
		   //计算上一页
		   this.prevPage= this.currentPage==1 ? 1 : this.currentPage-1;
		   //计算总页数
		   this.pageCount = this.total% this.pagesize > 0 ? this.total/this.pagesize+1 : this.total/this.pagesize;
		   //计算下一页
		   this.nextPage = this.currentPage== this.pageCount ? this.pageCount : this.currentPage+1;
		   
	  }
	  
	public Pager() {
	
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getNextPage() {
		return nextPage;
	}
	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}
	public int getPrevPage() {
		return prevPage;
	}
	public void setPrevPage(int prevPage) {
		this.prevPage = prevPage;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getPagesize() {
		return pagesize;
	}
	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}
	public List<T> getDatas() {
		return datas;
	}
	public void setDatas(List<T> datas) {
		this.datas = datas;
	}
 
	public String getUrl() {
		return url;
	}
 
	public void setUrl(String url) {
		this.url = url;
	}
 
	public String getKeyword() {
		return keyword;
	}
 
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
 
	public String getSelect() {
		return select;
	}
 
	public void setSelect(String select) {
		this.select = select;
	}
	
}