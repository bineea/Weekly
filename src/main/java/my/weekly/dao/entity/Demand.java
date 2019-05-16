package my.weekly.dao.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import my.weekly.common.entity.StringUUIDEntity;
import my.weekly.dao.entity.dict.DemandType;
import my.weekly.dao.entity.dict.HandleStatus;

@Entity
@Table(name = "weekly_demand")
public class Demand extends StringUUIDEntity {
	private String title;
	private String summary;
	private DemandType demandType;
	private LocalDateTime createTime;
	private LocalDateTime updateTime;
	private HandleStatus handleStatus = HandleStatus.NEW ;
	private User user;
	private Project project;

	@Column(name = "title")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}


	@Column(name = "summary")
	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "demand_type")
	public DemandType getDemandType() {
		return demandType;
	}

	public void setDemandType(DemandType demandType) {
		this.demandType = demandType;
	}

	@Column(name = "create_time")
	public LocalDateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

	@Column(name = "update_time")
	public LocalDateTime getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(LocalDateTime updateTime) {
		this.updateTime = updateTime;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "handle_status")
	public HandleStatus getHandleStatus() {
		return handleStatus;
	}

	public void setHandleStatus(HandleStatus handleStatus) {
		this.handleStatus = handleStatus;
	}

	@ManyToOne
	@JoinColumn(name = "user_id")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToOne
	@JoinColumn(name = "project_id")
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

}
