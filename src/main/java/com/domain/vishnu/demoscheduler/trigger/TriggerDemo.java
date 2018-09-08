//package com.domain.vishnu.demoscheduler.trigger;
//
//import org.quartz.JobBuilder;
//import org.quartz.JobDetail;
//import org.quartz.Scheduler;
//import org.quartz.Trigger;
//import org.quartz.TriggerBuilder;
//import org.quartz.impl.StdSchedulerFactory;
//
//import com.domain.vishnu.demoscheduler.demojob.JobDemo;
//
//public class TriggerDemo {
//
//    JobDetail job = JobBuilder.newJob(JobDemo.class).build();
//	
//	Trigger trigger = TriggerBuilder.newTrigger().withIdentity("Simple Trigger").startNow().build();
//	
//	Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
//}
