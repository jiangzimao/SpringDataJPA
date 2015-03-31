package org.jqiaofu.spring.job;

import java.time.LocalDateTime;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ProductTask {
	
	Logger logger = LoggerFactory.getLogger(ProductTask.class);

	@Inject
	private JobLauncher jobLauncher;

	@Inject
	private JobRegistry jobRegistry;

//	@Scheduled(initialDelay = 1000, fixedRate = 5000)
	public void process() {
		
		logger.debug("begin of dailyInterestJob...@" + (LocalDateTime.now()));
		System.out.println("start....");
		try {
			JobExecution jobExecution = jobLauncher.run(jobRegistry.getJob("testJob"), new JobParameters());
			System.out.println(jobExecution);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("end !!");
		logger.info("end of dailyInterestJob...@" + (LocalDateTime.now()));
	}

}
