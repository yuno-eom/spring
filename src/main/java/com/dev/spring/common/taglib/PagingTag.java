package com.dev.spring.common.taglib;

import java.io.IOException;

import javax.servlet.jsp.tagext.TagSupport;

//import com.dev.spring.common.Constants;
import com.dev.spring.common.Global;

public final class PagingTag extends TagSupport {
	
	private static final long serialVersionUID = 6413561626844723724L;
	
	private String url; // 페이지 클릭시 이동할 url
	private String pageCount; // [설정] 한번에 보여줄 페이지 갯수
	private String rowCount; // [설정] 한페이지당 row수
	private String rowMax; // 총 row수
	private String nowPage; // 현재 선택한 페이지
	
	private String params; // 추가 param list

	@Override
	public int doStartTag() {
		try {			
			pageContext.getOut().print(makePageHTML());
		} catch (IOException e) {
			
		}		
		
		return SKIP_BODY;		
	}
	
	public String makePageHTML(){
		String contextPath = Global.resource.getString("CONTEXT_PATH");
		StringBuilder sb = new StringBuilder();
		int pCount = Integer.parseInt(pageCount);
		int rCount = Integer.parseInt(rowCount);
		int rMax = Integer.parseInt(rowMax);
		int nPage = Integer.parseInt(nowPage);
				
		if(pCount > 0 && rCount > 0 && rMax > 0 && nPage > 0){
			// 총 페이지 수
			int maxPage = rMax / rCount + ((rMax % rCount == 0) ? 0 : 1);
			int startPage = 1 + (pCount * (nPage / pCount - ((nPage % pCount == 0) ? 1 : 0)));		
			int endPage = startPage + pCount - 1;
			
			if(endPage > maxPage) endPage = maxPage;
			//System.out.println("maxPage: "+maxPage+" , startPage: "+startPage+" , endPage: "+endPage);
			
			sb.append("<div class='paging'>");
			
			if(startPage - 1 > 1){
				sb.append(getAHref(startPage - 1, "<img src='"+contextPath+"/images/btn/btn_prev.gif' alt='이전' />", "class='prev'"));
			}
			
			for(int i = startPage; i <= endPage; i++){
				sb.append(getAHref(i, String.valueOf(i), ""));
			}
			
			if(endPage + 1 <= maxPage){
				sb.append(getAHref(endPage + 1, "<img src='"+contextPath+"/images/btn/btn_next.gif' alt='다음' />", "class='next'"));
			}
			
			sb.append("</div>\n");
			
			sb.append(getScript());
		}
		
		return sb.toString();
	}
	
	private String getAHref(int nPage, String title, String css){
		String pageNumber = title;
		
		if(nowPage.equals(title)){
			pageNumber = "<strong>" + title + "</strong>";
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append("<a href='#none' onclick='goPage(").append(nPage).append(")' ").append(css).append(" >").append(pageNumber).append("</a>\n");
		
		return sb.toString();
	}
	
	private String getScript(){
		StringBuilder sb = new StringBuilder();
		
		sb.append("<form id='frmPage' method='post' action='").append(url).append("'>\n");
		sb.append("  <input type='hidden' name='page.pageCount' value='").append(pageCount).append("'>\n");
		sb.append("  <input type='hidden' name='page.rowCount' value='").append(rowCount).append("'>\n");
		sb.append("  <input type='hidden' name='page.rowMax' value='").append(rowMax).append("'>\n");
		sb.append("  <input type='hidden' name='page.nowPage' id='nowPage' value='").append(nowPage).append("'>\n");
		
		if(params != null){
			String[] paramList = params.split("&");
			for(int i = 0; i < paramList.length; i++){
				String[] property = paramList[i].split("=");
				
				if(property.length > 1){
					sb.append("  <input type='hidden' name='").append(property[0]).append("'");
					sb.append(" value='").append(property[1]).append("'>\n");
					
//					if(property[0].equals("menuSubID")){
//						for(int j=0;j<Constants.MENU_SUB_ID.length;j++){
//							if(Constants.MENU_SUB_ID[j].equals(property[1])){
//								sb.append("  <input type='hidden' name='bdSeqNo' id='bdSeqNo' value='0'>\n");
//								break;
//							}
//						}
//					}
				}
			}
		}
		
		sb.append("</form>\n");
		
		sb.append("<script type='text/javascript'>\n");
		sb.append("  function goPage(pageNum){\n");
		sb.append("    $('#nowPage').val(pageNum);\n");
		sb.append("    $('#frmPage').submit();\n");
		sb.append("  }\n");
		sb.append("</script>\n");
		
		return sb.toString();
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setPageCount(String pageCount) {
		this.pageCount = pageCount;
	}

	public void setRowCount(String rowCount) {
		this.rowCount = rowCount;
	}

	public void setRowMax(String rowMax) {
		this.rowMax = rowMax;
	}

	public void setNowPage(String nowPage) {
		this.nowPage = nowPage;
	}

	public void setParams(String params) {
		this.params = params;
	}	
}
