package com.dev.spring.report.model;

import java.util.List;

import com.dev.spring.common.DataMap;
import com.dev.spring.common.model.SearchVO;

public class ReportVO extends SearchVO {

	private String mode;
	private String format; //for report
	private String grid;   //test for grid
	private List<DataMap> dataList; //test for xml
	
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getGrid() {
		return grid;
	}
	public void setGrid(String grid) {
		this.grid = grid;
	}
	public List<DataMap> getDataList() {
		return dataList;
	}
	public void setDataList(List<DataMap> dataList) {
		this.dataList = dataList;
	}

}
