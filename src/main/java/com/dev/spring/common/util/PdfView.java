package com.dev.spring.common.util;

import java.awt.Color;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.dev.spring.common.Global;
import com.dev.spring.user.model.UserInfo;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class PdfView extends AbstractPdfView {
	
	private static final Logger logger = LoggerFactory.getLogger(PdfView.class);
	
	private BaseFont bf;
	
	@Override
	@SuppressWarnings("unchecked")
	protected void buildPdfDocument(Map<String, Object> model, Document doc, PdfWriter writer
			, HttpServletRequest req, HttpServletResponse resp) throws Exception {
		
		String locale = Global.getMessages("com.locale"); //properties test for Header
		String key = (String) model.keySet().toArray()[0];
		logger.debug("locale: {}, key: {}", locale, key);
		
		if(locale.contains("ar")){
			bf = BaseFont.createFont("font/trado.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED); //resources
		}else{
			bf = BaseFont.createFont("font/NanumGothic.ttc,0", BaseFont.IDENTITY_H, BaseFont.EMBEDDED); //resources
		}
		
		if(model.containsKey("users")){
			Font font  = new Font(bf, 14, Font.BOLD);
			doc.add(new Phrase("Users Info", font));
			
			PdfPTable table = new PdfPTable(4);
			table.setWidthPercentage(100);
			
			table.addCell(getHeaderCell(Global.getMessages("lbl.userId")));
			table.addCell(getHeaderCell(Global.getMessages("lbl.userNm")));
			table.addCell(getHeaderCell(Global.getMessages("lbl.email")));
			table.addCell(getHeaderCell(Global.getMessages("lbl.grade")));
			
			List<UserInfo> userInfo = (List<UserInfo>) model.get("users");
			for (UserInfo user : userInfo) {
				table.addCell(getNormalCell(user.getUserId()));
				table.addCell(getNormalCell(user.getUserNm()));
				table.addCell(getNormalCell(user.getEmail()));
				table.addCell(getNormalCell(user.getGrade()));
			}
			
			doc.add(table);
		//}else if(){
			
		}	
	}
	
	private PdfPCell getHeaderCell(String str) throws Exception{
		Font font  = new Font(bf, 10, Font.BOLD);
		PdfPCell cell = new PdfPCell(new Phrase(str, font));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBackgroundColor(new Color(0, 255, 255));
		cell.setPadding(8);
		
		return cell;
	}
	
	private PdfPCell getNormalCell(String str) throws Exception{
		Font font  = new Font(bf, 10, Font.NORMAL);
		PdfPCell cell = new PdfPCell(new Phrase(str, font));
		//cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		//cell.setBackgroundColor(new Color(0, 255, 255));
		cell.setPadding(5);
		
		return cell;
	}

	public BaseFont getBf() {
		return bf;
	}

	public void setBf(BaseFont bf) {
		this.bf = bf;
	}

}