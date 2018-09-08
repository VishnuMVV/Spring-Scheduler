package com.domain.vishnu.demoscheduler.config;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

import java.io.IOException;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
//import org.quartz.SimpleScheduleBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;
import com.domain.vishnu.demoscheduler.demojob.JobDemo;
import com.domain.vishnu.demoscheduler.config.AutoWiringSpringBeanJobFactory;

@Configuration
@ConditionalOnExpression("'${using.spring.schedulerFactory}'=='false'")
public class QuartzConfig {
	
	@Autowired
    private ApplicationContext applicationContext;
	
	@Bean
	public SpringBeanJobFactory springBeanJobFactory() {
	   AutoWiringSpringBeanJobFactory jobFactory = new AutoWiringSpringBeanJobFactory();
	   jobFactory.setApplicationContext(applicationContext);
	   return jobFactory;
	}
	
	@Bean
	public JobDetail jobDetail() {
		return JobBuilder.newJob().ofType(JobDemo.class).storeDurably().withIdentity(JobKey.jobKey("Qrtz_Job_Detail"))
				.withDescription("Invoke Sample Job service...").build();
	}

	@Bean
	public Trigger trigger(JobDetail job) {
		return TriggerBuilder.newTrigger().forJob(job).withIdentity("Qrtz_Trigger").withDescription("Sample trigger")
				.withSchedule(simpleSchedule().repeatForever().withIntervalInSeconds(5)).build();
	}

	@Bean
	public Scheduler scheduler(Trigger trigger, JobDetail job) throws SchedulerException, IOException {
		StdSchedulerFactory factory = new StdSchedulerFactory();
		factory.initialize(new ClassPathResource("quartz.properties").getInputStream());

		Scheduler scheduler = factory.getScheduler();
		scheduler.setJobFactory(springBeanJobFactory());
		scheduler.scheduleJob(job, trigger);

		scheduler.start();
		return scheduler;
	}
	
}
