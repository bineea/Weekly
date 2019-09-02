package my.weekly.model.weekly;

import lombok.Getter;
import lombok.Setter;
import my.weekly.dao.entity.dict.CategoryType;
import my.weekly.model.BaseModel;

public class CategoryModel extends BaseModel {

    @Getter
    @Setter
    private String categoryItem;//分类项唯一标识
    @Getter
    @Setter
    private String categoryValue;//分类项名称
    @Getter
    @Setter
    private long categorySum;//数量
    @Getter
    @Setter
    private CategoryType categoryType;//分类类型
}
