package my.weekly.dao.entity.dict;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum CategoryType {

    USER_INFO("用户信息分类") {},
    DEMAND_INFO("需求信息分类") {},
    ;

    @Getter
    private String value;
}
