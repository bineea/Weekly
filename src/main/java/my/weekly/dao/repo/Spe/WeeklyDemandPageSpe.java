package my.weekly.dao.repo.Spe;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import my.weekly.dao.entity.Demand;
import my.weekly.dao.entity.dict.HandleStatus;

public class WeeklyDemandPageSpe extends AbstractPageSpecification<Demand> {
	private String projectId;
	private HandleStatus handleStatus;
	private List<HandleStatus> handleStatues;
	
	@Override
	public Specification<Demand> handleSpecification() {
		Specification<Demand> spe = (root, query, criteriaBuilder) -> {
			List<Predicate> predicateList = new ArrayList<Predicate>();
			if(StringUtils.hasText(projectId))
				predicateList.add(criteriaBuilder.equal(root.get("project").get("id").as(String.class), projectId));
			if(handleStatus != null)
				predicateList.add(criteriaBuilder.equal(root.get("handleStatus"), handleStatus));
			if(handleStatues != null && !handleStatues.isEmpty())
				predicateList.add(criteriaBuilder.in(root.get("handleStatus")).value(handleStatues));
			query.where(predicateList.stream().toArray(Predicate[]::new));
			query.orderBy(criteriaBuilder.desc(root.get("createTime").as(LocalDateTime.class)), criteriaBuilder.desc(root.get("id").as(String.class)));
			return query.getRestriction();
		};
		return spe;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public HandleStatus getHandleStatus() {
		return handleStatus;
	}

	public void setHandleStatus(HandleStatus handleStatus) {
		this.handleStatus = handleStatus;
	}

	public List<HandleStatus> getHandleStatues() {
		return handleStatues;
	}

	public void setHandleStatues(List<HandleStatus> handleStatues) {
		this.handleStatues = handleStatues;
	}

}
