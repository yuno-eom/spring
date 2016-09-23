package com.dev.spring.board.model;

import com.dev.spring.common.model.SearchVO;

public class SearchBoard extends SearchVO {

	private String bdGrp;
	//private String sbTitle;  //CommonBoard와 구분...
	//private String sbContent;
	//private String sbWriter;
	
	public String getBdGrp() {
		return bdGrp;
	}
	public void setBdGrp(String bdGrp) {
		this.bdGrp = bdGrp;
	}
/*
	public String getSbTitle() {
		return sbTitle;
	}
	public void setSbTitle(String sbTitle) {
		this.sbTitle = sbTitle;
	}
	public String getSbContent() {
		return sbContent;
	}
	public void setSbContent(String sbContent) {
		this.sbContent = sbContent;
	}
	public String getSbWriter() {
		return sbWriter;
	}
	public void setSbWriter(String sbWriter) {
		this.sbWriter = sbWriter;
	}
*/
}
