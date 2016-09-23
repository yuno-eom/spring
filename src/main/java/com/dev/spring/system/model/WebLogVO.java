package com.dev.spring.system.model;

public class WebLogVO {
	
	private String url;
	private String remoteAddr;
	private String httpReferer;
	private String httpUserAgent;
	private String userId;
	private String inputYmdt;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getRemoteAddr() {
		return remoteAddr;
	}
	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}
	public String getHttpReferer() {
		return httpReferer;
	}
	public void setHttpReferer(String httpReferer) {
		this.httpReferer = httpReferer;
	}
	public String getHttpUserAgent() {
		return httpUserAgent;
	}
	public void setHttpUserAgent(String httpUserAgent) {
		this.httpUserAgent = httpUserAgent;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getInputYmdt() {
		return inputYmdt;
	}
	public void setInputYmdt(String inputYmdt) {
		this.inputYmdt = inputYmdt;
	}
	
}
