package my.weekly.dao.entity.dict;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum EmailConfType {

    TAIJI_EMAIL("smtp.mail.taiji.com.cn", 25, "smtp", "@mail.taiji.com.cn") {},
    QQ_EMAIL("smtp.qq.com", 465, "smtp", "@qq.com") {},
    WY_163_EMAIL("smtp.163.com", 25, "smtp", "@163.com") {},
    FOX_EMAIL("smtp.foxmail.com", 25, "smtp", "@foxmail.com") {},
    ;

    @Getter
    private String host;
    @Getter
    private int port;
    @Getter
    private String protocol;
    @Getter
    private String Suffix;

}
