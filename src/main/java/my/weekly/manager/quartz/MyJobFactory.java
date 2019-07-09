package my.weekly.manager.quartz;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.stereotype.Component;

//TODO 尚未理解
@Component
public class MyJobFactory extends AdaptableJobFactory {
	
	@Autowired
	private AutowireCapableBeanFactory capableBeanFactory;
	
	@Override
	protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
		//调用父类的方法
        Object jobInstance = super.createJobInstance(bundle);
        //进行注入,这属于Spring的技术,不清楚的可以查看Spring的API.
        capableBeanFactory.autowireBean(jobInstance);
        return jobInstance;
	}
}
