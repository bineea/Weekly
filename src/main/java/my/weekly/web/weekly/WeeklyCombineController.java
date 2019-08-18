package my.weekly.web.weekly;

import my.weekly.web.AbstractController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("weekly")
public class WeeklyCombineController extends AbstractController {

    private final String prefix = "weekly/";

    @RequestMapping(value="/daily/combine", method= RequestMethod.GET)
    public String dailyCombineGet(HttpServletRequest request) {

        return prefix + "weekly/manage";
    }

    @RequestMapping(value="/daily/combine", method=RequestMethod.POST)
    public String dailyCombinePost(HttpServletRequest request, HttpServletResponse response) {

        return prefix + "weekly/queryResult";
    }

    @RequestMapping(value="/daily/weekly", method=RequestMethod.POST)
    public void daily2WeeklyPost(HttpServletRequest request, HttpServletResponse response) {

    }
}
