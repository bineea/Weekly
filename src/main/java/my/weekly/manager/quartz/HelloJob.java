package my.weekly.manager.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import my.weekly.manager.AbstractManager;

@Component
public class HelloJob extends AbstractManager implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("Hello world~~~");
	}

}
