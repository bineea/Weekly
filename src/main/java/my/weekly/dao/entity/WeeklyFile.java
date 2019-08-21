package my.weekly.dao.entity;

import lombok.Getter;
import lombok.Setter;
import my.weekly.common.entity.StringUUIDEntity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Blob;
import java.time.LocalDateTime;

@Entity
@Table(name="weekly_weekly_file")
public class WeeklyFile extends StringUUIDEntity {
    @NotBlank
    @Getter(onMethod_={@Column(name="name")})
    @Setter
    private String name;
    @NotNull
    @Getter(onMethod_={@Column(name="file")})
    @Setter
    private Blob file;
    @NotBlank
    @Getter(onMethod_= {@Column(name="save_path")})
    @Setter
    private String savePath;
    @Getter(onMethod_={@ManyToOne,@JoinColumn(name="send_email_id")})
    @Setter
    private SendEmail sendEmail;
    @Getter(onMethod_={@Column(name="create_time")})
    @Setter
    private LocalDateTime createTime;
    @NotNull
    @Getter(onMethod_={@ManyToOne,@JoinColumn(name="user_id")})
    @Setter
    private User user;
}
