package my.weekly.dao.entity.dict;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum EmailConfType {

    TAIJI_EMAIL("mail.taiji.com.cn", 587, "smtp", false, false, "@mail.taiji.com.cn") {},
    QQ_EMAIL("smtp.qq.com", 465, "smtp", true, true, "@qq.com") {},
    WY_163_EMAIL("smtp.163.com", 465, "smtp", true, true, "@163.com") {},
    FOX_EMAIL("smtp.qq.com", 465, "smtp", true, true, "@foxmail.com") {},
    ;

    @Getter
    private String host;//邮件服务器地址
    @Getter
    private int port;//端口号
    @Getter
    private String protocol;//协议
    @Getter
    private boolean sslEnable;//是否SSL加密
    @Getter
    private boolean acountHasSuffix;//账号是否需要邮箱后缀
    @Getter
    private String suffix;//邮箱后缀

}
