package my.weekly.web.weekly;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import my.weekly.dao.entity.dict.CategoryType;
import my.weekly.dao.entity.dict.DemandType;
import my.weekly.dao.repo.Spe.WeeklyDailyPageSpe;
import my.weekly.model.BaseModel;
import my.weekly.model.NoteModel;
import my.weekly.web.AbstractController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController extends AbstractController {

	private final String prefix = "weekly/";
	
	@RequestMapping(value = "weekly/homeIndex", method = RequestMethod.GET)
	public String weeklyHomeIndexGet(@ModelAttribute("spe") WeeklyDailyPageSpe spe) {
		return prefix + "homeIndex";
	}

	@RequestMapping(value = "weekly/homeCategory/{categoryType}/{categoryItem}", method = RequestMethod.GET)
	public String weeklyHomeCategoryGet(@ModelAttribute("spe") WeeklyDailyPageSpe spe,
										@PathVariable("categoryType") CategoryType categoryType,
										@PathVariable("categoryItem") String categoryItem,
										Model model) {
		switch (categoryType) {
			case USER_INFO:
				spe.setUserId(categoryItem);
				break;
			case DEMAND_INFO:
				spe.setDemandType(DemandType.valueOf(categoryItem));
				break;
			default:
				break;
		}
		model.addAttribute("categoryType", categoryType);
		return prefix + "homeCategory";
	}

}