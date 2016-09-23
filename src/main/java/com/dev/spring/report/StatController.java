package com.dev.spring.report;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dev.spring.common.DataMap;
import com.dev.spring.report.model.ReportVO;
import com.dev.spring.report.service.StatService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("report")
public class StatController {
	
	private static final Logger logger = LoggerFactory.getLogger(StatController.class);
	
	@Autowired StatService statService;
	
	@RequestMapping(value = "{statistics}", method = {RequestMethod.GET, RequestMethod.POST})
	public String statistics(@PathVariable String statistics, @ModelAttribute ReportVO reportVO, Model model) {
		
		logger.debug("{} : {}", statistics, reportVO.getSearchFilter());
		if (reportVO.getSearchFilter() == null) { //GET
			reportVO.setSearchFilter("2014");
		} else {
			List<DataMap> statList = new ArrayList<DataMap>();
			
			if ("board".equals(statistics)) statList = statService.selectBoardStat(reportVO);
			
			//reportVO.setDataList(statList); //for XML
			model.addAttribute("statList", statList);
		}
		
		if ("report".equals(reportVO.getMode())) {
			logger.debug("{} : {}", reportVO.getMode(), reportVO.getFormat());
			model.addAttribute("statYear", reportVO.getSearchFilter());
			model.addAttribute("format", reportVO.getFormat());
			
			return "report_"+statistics;
		}
		
		String grid = reportVO.getGrid() == null ? "jqGrid" : reportVO.getGrid();
		return "report/"+statistics+"_"+grid;
	}
	
	// dhtmlxGrid List ajax
	@RequestMapping(value = "/boardGrid", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public Model boardGrid(HttpServletRequest request, Model model) throws Exception {
		
		log4Parameters(request);
		String searchFilter = request.getParameter("searchFilter");
		
		ReportVO reportVO = new ReportVO();
		reportVO.setSearchFilter(searchFilter);
		
		List<DataMap> statList = statService.selectBoardStat(reportVO);
		
		//model.addAttribute("total_count", 17);
		//model.addAttribute("pos", 0);
		model.addAttribute("data", statList);
		
		ObjectMapper om = new ObjectMapper();
		logger.debug("json: {}", om.writeValueAsString(model));
		
		return model;
	}
	
	private void log4Parameters(HttpServletRequest request) {
		
		for(Enumeration<String> e=request.getParameterNames(); e.hasMoreElements();) {
			String name = e.nextElement();     //input name
			String value = request.getParameter(name); //input value
			
			logger.debug("parameter: {} = {}", name, value);
		}		
	}
}