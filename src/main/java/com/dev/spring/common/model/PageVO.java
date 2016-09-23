package com.dev.spring.common.model;

public class PageVO {
	
	private String url; // 페이지 클릭시 이동할 url
	private int pageCount; // [설정] 한번에 보여줄 페이지 갯수
	private int rowCount; // [설정] 한페이지당 row수
	private int rowMax; // 총 row수
	private int nowPage; // 현재 선택한 페이지
	
	// 쿼리 조회용
	private int startRowNum;
	private int endRowNum;	
	private int scopeRow; //  endrowNum - startRowNum
	
	public int getScopeRow() {
		return scopeRow;
	}
	public void setScopeRow(int scopeRow) {
		this.scopeRow = scopeRow;
	}
	public int getStartRowNum() {
		return startRowNum;
	}
	public void setStartRowNum(int startRowNum) {
		this.startRowNum = startRowNum;
	}
	public int getEndRowNum() {
		return endRowNum;
	}
	public void setEndRowNum(int endRowNum) {
		this.endRowNum = endRowNum;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public int getRowCount() {
		return rowCount;
	}
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}
	public int getRowMax() {
		return rowMax;
	}
	public void setRowMax(int rowMax) {
		this.rowMax = rowMax;
	}
	public int getNowPage() {
		return nowPage;
	}
	public void setNowPage(int nowPage) {
		this.nowPage = nowPage;
	}
}