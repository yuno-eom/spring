package com.dev.spring.board.model;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class CommonBoard {

	private int bdSeq;
	private int prSeq;
	private String bdGrp;
	private String title;
	private String content;
	private String userId;
	private String regNm;
	private String regYmdt;
	
	private List<MultipartFile> uploads;
	private List<CommonFile> bdFiles;
	
	private String cmd; //DB : insert, update, delete / UI : input, reply, modify
	
	public int getBdSeq() {
		return bdSeq;
	}
	public void setBdSeq(int bdSeq) {
		this.bdSeq = bdSeq;
	}
	public int getPrSeq() {
		return prSeq;
	}
	public void setPrSeq(int prSeq) {
		this.prSeq = prSeq;
	}
	public String getBdGrp() {
		return bdGrp;
	}
	public void setBdGrp(String bdGrp) {
		this.bdGrp = bdGrp;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getRegNm() {
		return regNm;
	}
	public void setRegNm(String regNm) {
		this.regNm = regNm;
	}
	public String getRegYmdt() {
		return regYmdt;
	}
	public void setRegYmdt(String regYmdt) {
		this.regYmdt = regYmdt;
	}
	public List<MultipartFile> getUploads() {
		return uploads;
	}
	public void setUploads(List<MultipartFile> uploads) {
		this.uploads = uploads;
	}
	public List<CommonFile> getBdFiles() {
		return bdFiles;
	}
	public void setBdFiles(List<CommonFile> bdFiles) {
		this.bdFiles = bdFiles;
	}
	public String getCmd() {
		return cmd;
	}
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
}
