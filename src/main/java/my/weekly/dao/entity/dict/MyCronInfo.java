package my.weekly.dao.entity.dict;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum MyCronInfo {

    PER_FIVE_SEC("0/5 * * * * ?", "每5秒执行") {},
    ;

    @Getter
    private String cronStr;
    @Getter
    private String value;

}
