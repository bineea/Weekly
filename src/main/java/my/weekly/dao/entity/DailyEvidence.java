package my.weekly.dao.entity;

import java.sql.Blob;
import java.time.LocalDateTime;

import my.weekly.common.entity.StringUUIDEntity;

public class DailyEvidence extends StringUUIDEntity {
	private Daily daily;
	private Blob evidence;
	private LocalDateTime createTime;

	public Daily getDaily() {
		return daily;
	}

	public void setDaily(Daily daily) {
		this.daily = daily;
	}

	public Blob getEvidence() {
		return evidence;
	}

	public void setEvidence(Blob evidence) {
		this.evidence = evidence;
	}

	public LocalDateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

}
