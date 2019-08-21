package my.weekly.dao.entity;

import lombok.Getter;
import lombok.Setter;
import my.weekly.common.entity.StringUUIDEntity;
import my.weekly.dao.entity.dict.EmailConfType;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="weekly_send_email")
public class SendEmail extends StringUUIDEntity {
    @NotBlank
    @Getter
    @Setter
    private String account; //账号
    @NotBlank
    @Getter
    @Setter
    private String recipients; //收件地址
    @NotBlank
    @Getter
    @Setter
    private String content; //内容
    @Getter
    @Setter
    private String title; //标题
    @NotNull
    @Getter
    @Setter
    private User user;
    @NotNull
    @Getter
    @Setter
    private LocalDateTime createTime;
}
