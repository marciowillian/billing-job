package com.mwcc.billingjob;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

@SpringBatchTest
@SpringBootTest
@ExtendWith(OutputCaptureExtension.class)
class BillingJobApplicationTests {

	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;
	
	@Autowired
	private JobRepositoryTestUtils jobRepositoryTestUtils;
	
//	@Autowired
//	private Job job;
//	
//	@Autowired
//	private JobLauncher jobLauncher;
	
	@BeforeEach
	public void setUp() {
		this.jobRepositoryTestUtils.removeJobExecutions();
	}
	
//	@Test
//	void testJobExecution(CapturedOutput output) throws Exception {
//		//given
//		JobParameters jobParameters = this.jobLauncherTestUtils.getUniqueJobParametersBuilder()
//				.addString("input.file", "/some/imput/file")
//				.toJobParameters();
//						
//		//when
//		JobExecution jobExecution = this.jobLauncherTestUtils.launchJob(jobParameters);
//		
//		//then
//		Assertions.assertTrue(output.getOut().contains("processing billing information from file /some/input/file"));
//		Assertions.assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
//	}

	@Test
	void testJobExecution(CapturedOutput output) throws Exception {
		//given
		JobParameters jobParameters = new JobParametersBuilder()
				.addString("input.file", "src/main/resources/billing-2023-01.csv")
				.toJobParameters();
		
		//when
		JobExecution jobExecution = this.jobLauncherTestUtils.launchJob(jobParameters);
		
		//then
		Assertions.assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
		Assertions.assertTrue(Files.exists(Paths.get("staging", "billing-2023-01.csv")));
	}

}
