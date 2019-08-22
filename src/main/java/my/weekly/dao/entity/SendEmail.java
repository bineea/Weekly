package my.weekly.dao.entity;

import lombok.Getter;
import lombok.Setter;
import my.weekly.common.entity.StringUUIDEntity;
import my.weekly.dao.entity.dict.EmailConfType;

import javax.persistence.*;
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
    @Getter(onMethod_= {@Column(name="account")})
    @Setter
    private String account; //账号
    @NotBlank
    @Getter(onMethod_= {@Column(name="recipients")})
    @Setter
    private String recipients; //收件地址
    @NotBlank
    @Getter(onMethod_= {@Column(name="content")})
    @Setter
    private String content; //内容
    @Getter(onMethod_= {@Column(name="subject")})
    @Setter
    private String subject; //主题
    @NotNull
    @Getter(onMethod_= {@ManyToOne, @JoinColumn(name="user_id")})
    @Setter
    private User user;
    @NotNull
    @Getter(onMethod_= {@Column(name="create_time")})
    @Setter
    private LocalDateTime createTime;
}
