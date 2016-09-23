package com.dev.spring.batch.service;

import org.springframework.batch.item.ItemProcessor;

import com.dev.spring.batch.model.Report;

public class ReportProcessor implements ItemProcessor<Report, Report> {

	@Override
	public Report process(Report item) throws Exception {
		
		System.out.println("Processing..." + item);
		return item;
	}

}