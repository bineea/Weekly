package my.weekly.model.weekly;

import my.weekly.dao.entity.dict.DemandType;
import my.weekly.dao.entity.dict.HandleStatus;
import my.weekly.model.BaseModel;

public class DemandModel extends BaseModel {
	private String projectId;
	private String title;
	private String summary;
	private DemandType demandType;
	private HandleStatus handleStatus;

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public DemandType getDemandType() {
		return demandType;
	}

	public void setDemandType(DemandType demandType) {
		this.demandType = demandType;
	}

	public HandleStatus getHandleStatus() {
		return handleStatus;
	}

	public void setHandleStatus(HandleStatus handleStatus) {
		this.handleStatus = handleStatus;
	}

}
