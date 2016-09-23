package com.dev.spring.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dev.spring.common.model.PageVO;

public class ControllerExt {
	
	private static final Logger logger = LoggerFactory.getLogger(ControllerExt.class);
	protected PageVO page; // paging
	
	public String execute() throws Exception {
		return null;
	}
	
	protected void initPage(int pageCount, int rowCount, int nowPage, int rowMax){	
		
		if(page == null){ // page 초기화
			page = new PageVO();			
			page.setPageCount(pageCount);
			page.setRowCount(rowCount);
		}
		page.setNowPage(nowPage);
		page.setRowMax(rowMax);
		page.setScopeRow((nowPage-1) * rowCount); //offset
		
		/* oracle
		page.setStartRowNum((page.getNowPage() - 1) * page.getRowCount() + 1);
		page.setEndRowNum(page.getStartRowNum() + page.getRowCount());
		page.setScopeRow(page.getEndRowNum() - page.getStartRowNum());
		*/
		logger.debug("page limit {}, offset {}", page.getRowCount(), page.getScopeRow());
	}

	public PageVO getPage() {
		return page;
	}

	public void setPage(PageVO page) {
		this.page = page;
	}
}