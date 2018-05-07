package com.sdu.samus.vo;

public class PaginationVO {

	private Integer pageSize;
	private Integer pageNumber;

	public PaginationVO(){}

	public PaginationVO(Integer pageSize,Integer pageNumber){
		this.pageSize = pageSize;
		this.pageNumber = pageNumber;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}


}
