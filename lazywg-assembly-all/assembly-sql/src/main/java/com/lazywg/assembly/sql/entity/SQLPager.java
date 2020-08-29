package com.lazywg.assembly.sql.entity;

/**
 * author: gaowang
 *
 * createTime:2018年1月31日 下午8:53:54
 *
 */
public class SQLPager {

	private int pageIndex;
	private int pageSize;
	private int dataCount;
	private String orderStr;
	
	public SQLPager(){}
	
	public SQLPager(int pageIndex,int pageSize){
		this(pageIndex, pageSize, "");
	}
	
	public SQLPager(int pageIndex,int pageSize,String orderStr){
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
		this.orderStr = orderStr;
	}
	
	public int getPageIndex() {
		return pageIndex;
	}
	
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	
	public int getPageSize() {
		return pageSize;
	}
	
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getDataCount() {
		return dataCount;
	}
	
	public void setDataCount(int dataCount) {
		this.dataCount = dataCount;
	}
	
	public String getOrderStr() {
		return orderStr;
	}
	
	public void setOrderStr(String orderStr) {
		this.orderStr = orderStr;
	}
	
	public int getPageCount() {
		return (dataCount-1)/pageSize + 1;
	}
	
	public int getStartIndex() {
		return (pageIndex - 1) * pageSize + 1;
	}
	
	public int getEndIndex() {
		if (pageIndex*pageSize <= dataCount || dataCount == 0) {
			return pageIndex * pageSize;
		}
		return dataCount;
	}
}
