package com.dev.spring.board.model;

public class CommonFile {

	private int fileSeq;
	private int bdSeq;
	private String saveNm;
	private String realNm;
	private String pathNm;
	
	public int getFileSeq() {
		return fileSeq;
	}
	public void setFileSeq(int fileSeq) {
		this.fileSeq = fileSeq;
	}
	public int getBdSeq() {
		return bdSeq;
	}
	public void setBdSeq(int bdSeq) {
		this.bdSeq = bdSeq;
	}
	public String getSaveNm() {
		return saveNm;
	}
	public void setSaveNm(String saveNm) {
		this.saveNm = saveNm;
	}
	public String getRealNm() {
		return realNm;
	}
	public void setRealNm(String realNm) {
		this.realNm = realNm;
	}
	public String getPathNm() {
		return pathNm;
	}
	public void setPathNm(String pathNm) {
		this.pathNm = pathNm;
	}
}
