package my.weekly.web.weekly;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import my.weekly.dao.repo.Spe.WeeklyDailyPageSpe;
import my.weekly.manager.quartz.TestScheduler;
import my.weekly.web.AbstractController;

@Controller
public class HomeController extends AbstractController {

	private final String prefix = "weekly/";
	
	@Autowired
	private TestScheduler testScheduler;
	
	@RequestMapping(value = "weekly/homeIndex", method = RequestMethod.GET)
	public String weeklyHomeIndexGet(@ModelAttribute("spe") WeeklyDailyPageSpe spe) {
		
		try {
			testScheduler.test();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return prefix + "homeIndex";
	}
	
}
