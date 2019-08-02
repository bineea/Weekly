package my.weekly.dao.entity.dict;

import lombok.AllArgsConstructor;
import lombok.Getter;
import my.weekly.manager.quartz.FuckJob;
import my.weekly.manager.quartz.HelloJob;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;

// 一个trigger只能对应一个job；但是一个job可以对应多个trigger
@AllArgsConstructor
public enum QuartzInfo {

    HELLOWORLD(HelloJob.class, "hello_job","hello_trigger", MyCronInfo.PER_FIVE_SEC) {},
    FUCKWORLD(FuckJob.class, "fuck_job","fuck_trigger", MyCronInfo.PER_FIVE_SEC) {},
    ;

    @Getter
    private Class clazz;
    @Getter
    private String jobGroup;
    @Getter
    private String triggerGroup;
    @Getter
    private MyCronInfo cronInfo;
}
