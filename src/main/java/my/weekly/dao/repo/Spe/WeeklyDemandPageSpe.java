package my.weekly.dao.repo.Spe;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import my.weekly.dao.entity.Demand;
import my.weekly.dao.entity.dict.HandleStatus;

public class WeeklyDemandPageSpe extends AbstractPageSpecification<Demand> {
	@Getter
	@Setter
	private String projectId;
	@Getter
	@Setter
	private HandleStatus handleStatus;
	@Getter
	@Setter
	private List<HandleStatus> handleStatues;
	@Getter
	@Setter
	private LocalDateTime startUpTime;
	@Getter
	@Setter
	private LocalDateTime endUpTime;
	
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
			if(startUpTime != null)
				predicateList.add(criteriaBuilder.greaterThanOrEqualTo(root.get("updateTime").as(LocalDateTime.class), startUpTime));
			if(endUpTime != null)
				predicateList.add(criteriaBuilder.lessThanOrEqualTo(root.get("updateTime").as(LocalDateTime.class), endUpTime));
			query.where(predicateList.stream().toArray(Predicate[]::new));
			query.orderBy(criteriaBuilder.desc(root.get("createTime").as(LocalDateTime.class)), criteriaBuilder.desc(root.get("id").as(String.class)));
			return query.getRestriction();
		};
		return spe;
	}

}
