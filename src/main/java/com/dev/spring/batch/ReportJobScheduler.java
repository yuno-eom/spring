package com.dev.spring.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;

public class ReportJobScheduler {

	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired
	private Job reportJob;
	
	private JobExecution execution;
	
	public void run(){		
		try {
			execution = jobLauncher.run(reportJob, new JobParameters());
			System.out.println("Execution status: "+ execution.getStatus());
		} catch (JobExecutionAlreadyRunningException e) {
			e.printStackTrace();
		} catch (JobRestartException e) {			
			e.printStackTrace();
		} catch (JobInstanceAlreadyCompleteException e) {			
			e.printStackTrace();
		} catch (JobParametersInvalidException e) {			
			e.printStackTrace();
		}
	}
}
