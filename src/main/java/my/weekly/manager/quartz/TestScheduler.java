package my.weekly.manager.quartz;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Component;

import my.weekly.manager.AbstractManager;

@Component
public class TestScheduler extends AbstractManager {

	public void test() throws Exception {
		 SchedulerFactory schedFact = new StdSchedulerFactory();
		 Scheduler scheduler = schedFact.getScheduler();
		 scheduler.start();
		 // define the job and tie it to our HelloJob class
		  JobDetail job = JobBuilder.newJob(HelloJob.class)
		      .withIdentity("myJob", "group1")
		      .build();

		  // Trigger the job to run now, and then every 40 seconds
		  Trigger trigger = TriggerBuilder.newTrigger()
		      .withIdentity("myTrigger", "group1")
		      .withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?"))
		      .forJob("myJob", "group1")
		      .build();	

		  // Tell quartz to schedule the job using our trigger
		  scheduler.scheduleJob(job, trigger);
	}

}
