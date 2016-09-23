package com.dev.spring.common.util;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.dev.spring.common.Global;
import com.dev.spring.user.model.UserInfo;

public class ExcelView extends AbstractExcelView  {
	
	private static final Logger logger = LoggerFactory.getLogger(ExcelView.class);
	
	@Override
	@SuppressWarnings("unchecked")
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook wb
			, HttpServletRequest req, HttpServletResponse resp) throws Exception {
		
		HSSFSheet sheet;
		HSSFRow row;
		int r = 0;
		
		String locale = Global.getMessages("com.locale"); //properties test for Header
		String key = (String) model.keySet().toArray()[0];
		logger.debug("locale: {}, key: {}", locale, key);
		
		if(model.containsKey("users")){
			sheet = wb.createSheet("users");
			if(locale.contains("ar")){
				sheet.setRightToLeft(true); //아랍 엑셀 default 확인..(& style.setAlignment()..)
			}
			
			row = sheet.createRow(r);
			row.setRowStyle(getHeaderStyle(wb));
			row.createCell(0).setCellValue(Global.getMessages("lbl.userId"));
			row.createCell(1).setCellValue(Global.getMessages("lbl.userNm"));
			row.createCell(2).setCellValue(Global.getMessages("lbl.email"));
			row.createCell(3).setCellValue(Global.getMessages("lbl.grade"));
			
			List<UserInfo> userInfo = (List<UserInfo>) model.get("users");
			for (UserInfo user : userInfo) {
				row = sheet.createRow(++r);
				row.createCell(0).setCellValue(user.getUserId());
				row.createCell(1).setCellValue(user.getUserNm());
				row.createCell(2).setCellValue(user.getEmail());
				row.createCell(3).setCellValue(user.getGrade());
			}
		//}else if(){
			
		}
	}
	
	private HSSFCellStyle getHeaderStyle(HSSFWorkbook wb){
		
		HSSFCellStyle style = wb.createCellStyle();
		HSSFFont font = wb.createFont();
		
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		
		style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setFont(font);
		
	    return style;
	}
/*
	private HSSFCellStyle getNormalStyle(HSSFWorkbook wb){
		
		HSSFCellStyle style = wb.createCellStyle();

	    return style;
	}
*/
}