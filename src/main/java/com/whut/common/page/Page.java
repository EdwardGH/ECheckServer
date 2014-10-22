package com.whut.common.page;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * 分页数据类
 * 
 * @author hk
 * 
 *         2012-10-26 下午8:23:15
 */
public class Page<T> {

	/**
	 * 一页数据默认20条
	 */
	private int limit = 20;
	/**
	 * 当前页码
	 */
	private int page;

	/**
	 * 上一页
	 */
	private int upPage;

	/**
	 * 下一页
	 */
	private int nextPage;
	/**
	 * 一共有多少条数据
	 */
	private long totalCount;

	/**
	 * 一共有多少页
	 */
	private int totalPage;
	/**
	 * 数据集合
	 */
	private List<T> datas;

	/**
	 * 分页的url
	 */
	private String pageUrl;

	public Page()
	{
	
	}
	
	/**
	 * 获取第一条记录位置
	 * 
	 * @return
	 */
	public int getFirstResult() {
		return (this.getPage() - 1) * this.getLimit();
	}

	/**
	 * 获取最后记录位置
	 * 
	 * @return
	 */
	public int getLastResult() {
		return this.getPage() * this.getPage();
	}

	/**
	 * 计算一共多少页
	 */
	public void setTotalPage() {
		this.totalPage = (int) ((this.totalCount % this.limit > 0) ? (this.totalCount / this.limit + 1)
				: this.totalCount / this.limit);
	}

	/**
	 * 设置 上一页
	 */
	public void setUpPage() {
		this.upPage = (this.page > 1) ? this.page - 1 : this.page;
	}

	/**
	 * 设置下一页
	 */
	public void setNextPage() {
		this.nextPage = (this.page == this.totalPage) ? this.page : this.page + 1;
	}

	public int getNextPage() {
		return nextPage;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public int getUpPage() {
		return upPage;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount2) {
		this.totalCount = totalCount2;
	}

	public List<T> getDatas() {
		return datas;
	}

	public void setDatas(List<T> datas) {
		this.datas = datas;
	}

	public String getPageUrl() {
		return pageUrl;
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}

	public Page(int pageNo, int pageSize, long totalCount2) {
		this.setPage(pageNo);
		this.setLimit(pageSize);
		this.setTotalCount(totalCount2);
		this.init();
	}

	/**
	 * 初始化计算分页
	 */
	private void init() {
		this.setTotalPage();// 设置一共页数
		this.setUpPage();// 设置上一页
		this.setNextPage();// 设置下一页
	}
	
	/**
	 * 根据request参数初始化page
	 * @return
	 */
	public static Page initPage(HttpServletRequest request){
		Page page = new Page();
		if(request!=null&&request.getParameter("limit")!=null&&request.getParameter("limit")!=""
				&&request.getParameter("page")!=null&&request.getParameter("page")!=""){
			page.setLimit(Integer.valueOf(request.getParameter("limit")));
			page.setPage(Integer.valueOf(request.getParameter("page")));
		}
		else{
			page.setLimit(15);
			page.setPage(1);
		}
		return page;
	}
}
