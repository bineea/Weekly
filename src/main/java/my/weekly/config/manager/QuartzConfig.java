package my.weekly.config.manager;

import my.weekly.dao.entity.dict.QuartzInfo;
import my.weekly.manager.acl.AclManager;
import my.weekly.manager.quartz.MyJobFactory;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

// @Bean注解修饰带参数的方法时，按照参数类型获取对应的参数bean
@Configuration
@Import(AppConfig.class)
public class QuartzConfig {

    @Autowired
    private MyJobFactory myJobFactory;

    //quartz 配置
    @Bean(name = "quartzProperties")
    public PropertiesFactoryBean loadQuartzProperties()
    {
        PropertiesFactoryBean propertiesFactory = new PropertiesFactoryBean();
        propertiesFactory.setLocation(new ClassPathResource("config/quartz.properties"));
        propertiesFactory.setFileEncoding("utf-8");
        return propertiesFactory;
    }

    @Bean(name = "schedulerFactoryBean", destroyMethod = "destroy")
    public SchedulerFactoryBean initSchedulerFactoryBean() throws IOException {
        SchedulerFactoryBean  schedulerFac = new SchedulerFactoryBean();
        schedulerFac.setOverwriteExistingJobs(false);
        schedulerFac.setAutoStartup(true);
        schedulerFac.setJobFactory(myJobFactory);
        schedulerFac.setQuartzProperties(loadQuartzProperties().getObject());
        schedulerFac.setStartupDelay(10);
        return schedulerFac;
    }

    //TODO 实现初始化及销毁的方法
    @Bean(name="scheduler")
    public Scheduler scheduleJob() throws SchedulerException, IOException {
        List<QuartzInfo> quartzInfoList = Arrays.asList(QuartzInfo.values());
        Scheduler scheduler = initSchedulerFactoryBean().getScheduler();
        for(QuartzInfo q : quartzInfoList) {
            JobDetail job = JobBuilder.newJob(q.getClazz())
                    .withIdentity(q.name()+"job", q.getJobGroup())
                    .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(q.name()+"Trigger", q.getTriggerGroup())
                    .withSchedule(CronScheduleBuilder.cronSchedule(q.getCronInfo().getCronStr()))
                    .forJob(q.name()+"job", q.getJobGroup())
                    .build();
            scheduler.scheduleJob(job, trigger);
        }
        return scheduler;
    }
}
