package com.sdu.samus.model;

/*
 * 分页用到的实体类
 */
public class Pagination {

	private int totalCount;	//总数目
	private int pageSize;	//一页显示的数目
	private int pageCount;	//总页数
	private int currentPage;//当前页数
	private int offset;		//当前页第一条的索引

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageCount() {
		if(totalCount < 1){
			pageCount = 0;
			return pageCount;
		}
		pageCount = (totalCount - 1) / getPageSize() + 1;
		return pageCount;
	}

	public int getCurrentPage() {
		if (currentPage < 1) {
			currentPage = 1;
		}
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getOffset() {
		offset = (getCurrentPage() - 1) * getPageSize();
		return offset;
	}

	public boolean isFirstPage(){
		if(this.currentPage <= 1){
			return true;
		}
		return false;
	}

	public boolean isLastPage(){
		if(this.currentPage >= this.getPageCount()){
			return true;
		}
		return false;
	}
}

