package my.weekly.dao.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import my.weekly.common.entity.StringUUIDEntity;
import my.weekly.dao.entity.dict.HandleStatus;

@Entity
@Table(name = "weekly_daily")
public class Daily extends StringUUIDEntity {
	private String operateContent;
	private String sqlContent;
	private HandleStatus handleStatus;
	private User user;
	private LocalDate operateDate;
	private LocalDateTime createTime;
	private LocalDateTime updateTime;
	private Demand demand;

	@Column(name = "operate_content")
	public String getOperateContent() {
		return operateContent;
	}

	public void setOperateContent(String operateContent) {
		this.operateContent = operateContent;
	}

	@Column(name = "sql_content")
	public String getSqlContent() {
		return sqlContent;
	}

	public void setSqlContent(String sqlContent) {
		this.sqlContent = sqlContent;
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

	@Column(name = "operate_date")
	public LocalDate getOperateDate() {
		return operateDate;
	}

	public void setOperateDate(LocalDate operateDate) {
		this.operateDate = operateDate;
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

	@ManyToOne
	@JoinColumn(name = "demand_id")
	public Demand getDemand() {
		return demand;
	}

	public void setDemand(Demand demand) {
		this.demand = demand;
	}

}
