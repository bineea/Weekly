package my.weekly.manager.quartz;

import my.weekly.dao.entity.Demand;
import my.weekly.dao.entity.dict.HandleStatus;
import my.weekly.dao.repo.Spe.WeeklyDemandPageSpe;
import my.weekly.manager.AbstractManager;
import my.weekly.manager.daily.DemandManager;
import my.weekly.model.MyFinals;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Component
public class UpdateDemandStatus extends AbstractManager implements Job {

    @Autowired
    private DemandManager demandManager;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("-------------------update demand status start------------------------");
        WeeklyDemandPageSpe spe = new WeeklyDemandPageSpe();
        spe.setPageNo(0);
        spe.setPageSize(1000);
        spe.setEndUpTime(LocalDateTime.now().plusMinutes(-10));
        Page<Demand> page = demandManager.pageQuery(spe);
        do {
            for(Demand demand : page.getContent()) {
                demandManager.updateStatus(demand);
            }
            page = demandManager.pageQuery(spe);
        } while (page.getTotalElements() > 0);
        logger.info("-------------------update demand status end------------------------");
    }
}
