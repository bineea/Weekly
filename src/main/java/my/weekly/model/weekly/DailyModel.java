package my.weekly.model.weekly;

import java.time.LocalDate;

import my.weekly.dao.entity.dict.HandleStatus;
import my.weekly.model.BaseModel;

public class DailyModel extends BaseModel {
	private String projectId;
	private String demandId;
	private String operateContent;
	private String sqlContent;
	private HandleStatus handleStatus;
	private LocalDate operateDate;

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getDemandId() {
		return demandId;
	}

	public void setDemandId(String demandId) {
		this.demandId = demandId;
	}

	public String getOperateContent() {
		return operateContent;
	}

	public void setOperateContent(String operateContent) {
		this.operateContent = operateContent;
	}

	public String getSqlContent() {
		return sqlContent;
	}

	public void setSqlContent(String sqlContent) {
		this.sqlContent = sqlContent;
	}

	public HandleStatus getHandleStatus() {
		return handleStatus;
	}

	public void setHandleStatus(HandleStatus handleStatus) {
		this.handleStatus = handleStatus;
	}

	public LocalDate getOperateDate() {
		return operateDate;
	}

	public void setOperateDate(LocalDate operateDate) {
		this.operateDate = operateDate;
	}

}
