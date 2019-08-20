package my.weekly.model.weekly;

import lombok.Getter;
import lombok.Setter;
import my.weekly.model.BaseModel;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public class WeeklyModel extends BaseModel {

    @NotNull
    @Getter
    @Setter
    private LocalDate startOpDate;
    @NotNull
    @Getter
    @Setter
    private LocalDate endOpDate;
    @Getter
    @Setter
    private String operateContent;
    @NotEmpty
    @Getter
    @Setter
    private List<String> dailyIdList;
}
