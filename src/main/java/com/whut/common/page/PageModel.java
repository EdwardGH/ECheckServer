package com.whut.common.page;

import java.util.List;

/**
 * 封装page
 * @author Edward
 *
 * @param <T>
 */
public class PageModel<T> {

	/**
	 * 一页数据默认20条
	 */
	private int pageSize = 20;
	/**
	 * 当前页码
	 */
	private int page;

	/**
	 * 上一页
	 */
	private int prePage;

	/**
	 * 下一页
	 */
	private int nextPage;
	/**
	 * 一共有多少条数据
	 */
	private long recordCount;

	/**
	 * 一共有多少页
	 */
	private int totalPage;
	/**
	 * 数据集合
	 */
	private List<T> records;
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getPrePage() {
		return prePage;
	}
	public void setPrePage(int prePage) {
		this.prePage = prePage;
	}
	public int getNextPage() {
		return nextPage;
	}
	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}
	public long getRecordCount() {
		return recordCount;
	}
	public void setRecordCount(long recordCount) {
		this.recordCount = recordCount;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public List<T> getRecords() {
		return records;
	}
	public void setRecords(List<T> records) {
		this.records = records;
	}


	

}
