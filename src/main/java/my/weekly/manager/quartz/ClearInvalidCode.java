package my.weekly.manager.quartz;

import my.weekly.manager.AbstractManager;
import my.weekly.manager.acl.CheckCodeMapHolder;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClearInvalidCode extends AbstractManager implements Job {

    @Autowired
    private CheckCodeMapHolder checkCodeMapHolder;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("-------------------clear invalid cache start------------------------");
        for(String key : CheckCodeMapHolder.cache.keySet()) {
            if(!checkCodeMapHolder.cacheValidate(key)) {
                CheckCodeMapHolder.cache.remove(key);
            }
        }
        logger.info("-------------------clear invalid cache end------------------------");
    }
}
