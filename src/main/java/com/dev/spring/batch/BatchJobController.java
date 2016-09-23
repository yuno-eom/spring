package com.dev.spring.batch;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dev.spring.batch.model.StatusResponse;

@Controller
@RequestMapping("/batch")
public class BatchJobController {

	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired @Qualifier("reportJob")
	private Job reportJob;
	
	@Autowired @Qualifier("mybatisJob")
	private Job mybatisJob;
	
	@Autowired @Qualifier("partitionJob")
	private Job partitionJob;
	
	@RequestMapping(value="/reportJob")
	@ResponseBody
	public StatusResponse reportJob() {
		try {
			Map<String,JobParameter> parameters = new HashMap<String,JobParameter>();
			parameters.put("date", new JobParameter(new Date()));
			
			jobLauncher.run(reportJob, new JobParameters(parameters));
			return new StatusResponse(true);
			
		} catch (JobInstanceAlreadyCompleteException ex) {
			return new StatusResponse(false, "This job has been completed already!");
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@RequestMapping(value="/mybatisJob")
	@ResponseBody
	public StatusResponse mybatisJob() {
		try {
			Map<String,JobParameter> parameters = new HashMap<String,JobParameter>();
			parameters.put("date", new JobParameter(new Date()));
			
			jobLauncher.run(mybatisJob, new JobParameters(parameters));
			return new StatusResponse(true);
			
		} catch (JobInstanceAlreadyCompleteException ex) {
			return new StatusResponse(false, "This job has been completed already!");
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@RequestMapping(value="/partitionJob")
	@ResponseBody
	public StatusResponse partitionJob() {
		try {
			Map<String,JobParameter> parameters = new HashMap<String,JobParameter>();
			parameters.put("date", new JobParameter(new Date()));
			
			jobLauncher.run(partitionJob, new JobParameters(parameters));
			return new StatusResponse(true);
			
		} catch (JobInstanceAlreadyCompleteException ex) {
			return new StatusResponse(false, "This job has been completed already!");
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
