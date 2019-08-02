package my.weekly.manager.quartz;

import my.weekly.manager.AbstractManager;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

@Component
public class FuckJob extends AbstractManager implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("Fuck world~~~");
	}

}
