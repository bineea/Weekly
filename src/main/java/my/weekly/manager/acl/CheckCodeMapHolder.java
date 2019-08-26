package my.weekly.manager.acl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
public class CheckCodeMapHolder {
    public static Map<String, CheckCodeModel> cache = new HashMap<>();

    public void handleCheckCode(String email, String checkCode, int intervalSec) {
        cache.put(email, new CheckCodeModel(checkCode, LocalDateTime.now(), intervalSec));
    }

    public void delCheckCode(String email) {
        cache.remove(email);
    }

    public boolean checkCodeValidate(String email, String checkCode) {
        CheckCodeModel model = cache.get(email);
        if(model != null
                && checkCode.equalsIgnoreCase(model.getCheckCode())
                && model.getCreateTime().plusSeconds(model.getIntervalSec()).isAfter(LocalDateTime.now()) )
            return true;
        return false;
    }

    public boolean cacheValidate(String email) {
        CheckCodeModel model = cache.get(email);
        if(model != null
                && model.getCreateTime().plusSeconds(model.getIntervalSec()).isAfter(LocalDateTime.now()) )
            return true;
        return false;
    }
}

@AllArgsConstructor
class CheckCodeModel {
    @Getter
    @Setter
    private String checkCode;//随机码
    @Getter
    @Setter
    private LocalDateTime createTime;//创建时间
    @Getter
    @Setter
    private int intervalSec;//间隔秒
}