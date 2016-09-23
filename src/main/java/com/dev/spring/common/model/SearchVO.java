package com.dev.spring.common.model;

public class SearchVO {
	
	private String searchFilter; // 검색조건 콤보선택
	private String searchText;   // 검색조건 값
	
	private String startYmd;     // 검색 시작일
	private String endYmd;       // 검색 종료일
	
	private PageVO page;
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		
		sb.append(" searchFilter=").append(searchFilter);
		sb.append(" searchText=").append(searchText);
		
		if(page != null){
			sb.append(" page:").append(page.toString());
		}
		
		return sb.toString();
	}
	
	public String getSearchFilter() {
		return searchFilter;
	}
	public void setSearchFilter(String searchFilter) {
		this.searchFilter = searchFilter;
	}
	public String getSearchText() {
		return searchText;
	}
	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}
	public String getStartYmd() {
		return startYmd;
	}
	public void setStartYmd(String startYmd) {
		this.startYmd = startYmd;
	}
	public String getEndYmd() {
		return endYmd;
	}
	public void setEndYmd(String endYmd) {
		this.endYmd = endYmd;
	}
	public PageVO getPage() {
		return page;
	}
	public void setPage(PageVO page) {
		this.page = page;
	}
}