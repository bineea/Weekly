package my.weekly.dao.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import my.weekly.common.entity.StringUUIDEntity;
import my.weekly.dao.entity.dict.Area;

@Entity
@Table(name = "weekly_project")
public class Project extends StringUUIDEntity {
	@NotNull(message = "项目所属区域不能为空")
	private Area area;
	
	@NotEmpty(message = "项目简称不能为空")
	@Size(max = 6)
	private String abbr;
	
	@NotEmpty(message = "项目名称不能为空")
	private String name;
	
	@NotEmpty(message = "项目描述不能为空")
	private String summary;
	
	private LocalDateTime createTime;
	
	//@Valid // 嵌套验证必须用@Valid
	private User user;

	@Enumerated(EnumType.STRING)
	@Column(name = "area")
	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	@Column(name = "abbr")
	public String getAbbr() {
		return abbr;
	}

	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "summary")
	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	@Column(name = "create_time")
	public LocalDateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

	@ManyToOne
	@JoinColumn(name = "user_id")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
