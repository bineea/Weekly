package my.weekly.dao.entity.dict;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum MyCronInfo {

    PER_FIVE_SEC("0/5 * * * * ?", "每5秒钟执行一次") {},
    PER_TEN_MINUTE("0 0/10 * * * ?", "每10分钟执行一次") {},
    PER_ONE_HOUR("0 0 0/1 * * ?", "每1小时执行一次") {},
    ;

    @Getter
    private String cronStr;
    @Getter
    private String value;

}
