package my.weekly.dao.repo.Spe;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import my.weekly.dao.entity.Daily;
import my.weekly.dao.entity.dict.HandleStatus;

public class WeeklyDailyPageSpe extends AbstractPageSpecification<Daily> {
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private String operateContent;
	private String userId;
	private String demandId;
	private HandleStatus handleStatus;
	
	@Override
	public Specification<Daily> handleSpecification() {
		Specification<Daily> spe = (root, query, criteriaBuilder) -> {
			List<Predicate> predicateList = new ArrayList<Predicate>();
			if(StringUtils.hasText(operateContent))
				predicateList.add(criteriaBuilder.like(root.get("operateContent").as(String.class), like(operateContent)));
			if(StringUtils.hasText(userId))
				predicateList.add(criteriaBuilder.equal(root.get("user").get("id").as(String.class), userId));
			if(StringUtils.hasText(demandId))
				predicateList.add(criteriaBuilder.equal(root.get("demand").get("id").as(String.class), demandId));
			if(handleStatus != null)
				predicateList.add(criteriaBuilder.equal(root.get("handleStatus"), handleStatus));
			if(startTime != null)
				predicateList.add(criteriaBuilder.lessThanOrEqualTo(root.get("createTime").as(LocalDateTime.class), startTime));
			if(endTime != null)
				predicateList.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createTime").as(LocalDateTime.class), endTime));
			query.where(predicateList.stream().toArray(Predicate[]::new));
			query.orderBy(criteriaBuilder.desc(root.get("createTime").as(LocalDateTime.class)), criteriaBuilder.desc(root.get("id").as(String.class)));
			return query.getRestriction();
		};
		return spe;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	public String getOperateContent() {
		return operateContent;
	}

	public void setOperateContent(String operateContent) {
		this.operateContent = operateContent;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDemandId() {
		return demandId;
	}

	public void setDemandId(String demandId) {
		this.demandId = demandId;
	}

	public HandleStatus getHandleStatus() {
		return handleStatus;
	}

	public void setHandleStatus(HandleStatus handleStatus) {
		this.handleStatus = handleStatus;
	}

}
