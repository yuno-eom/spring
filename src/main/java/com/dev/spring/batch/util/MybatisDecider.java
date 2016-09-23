package com.dev.spring.batch.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;

public class MybatisDecider implements JobExecutionDecider{

	static private Log log = LogFactory.getLog(MybatisPartitioner.class);
	
	@SuppressWarnings("unused")
	@Override
	public FlowExecutionStatus decide(JobExecution arg0, StepExecution arg1) {
		log.debug("Decider JobExecution" + arg0.getExecutionContext().get("boardGrp"));
		log.debug("Decider StepExecution" + arg1.getExecutionContext().get("boardGrp"));
		
		if(true){
			return new FlowExecutionStatus("report");
		}
		
		return null;
	}
}