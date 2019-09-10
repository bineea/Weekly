package my.weekly.dao.repo.Spe;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import lombok.Getter;
import lombok.Setter;
import my.weekly.dao.entity.dict.DemandType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import my.weekly.dao.entity.Daily;
import my.weekly.dao.entity.dict.HandleStatus;

public class WeeklyDailyPageSpe extends AbstractPageSpecification<Daily> {
	@Getter
	@Setter
	private LocalDateTime startTime;
	@Getter
	@Setter
	private LocalDateTime endTime;
	@Getter
	@Setter
	private String operateContent;
	@Getter
	@Setter
	private String userId;
	@Getter
	@Setter
	private String demandId;
	@Getter
	@Setter
	private DemandType demandType;
	@Getter
	@Setter
	private HandleStatus handleStatus;
	@Getter
	@Setter
	private LocalDate startOpDate;
	@Getter
	@Setter
	private LocalDate endOpDate;
	
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
			if(demandType != null)
				predicateList.add(criteriaBuilder.equal(root.get("demand").get("demandType"), demandType));
			if(handleStatus != null)
				predicateList.add(criteriaBuilder.equal(root.get("handleStatus"), handleStatus));
			if(startTime != null)
				predicateList.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createTime").as(LocalDateTime.class), startTime));
			if(endTime != null)
				predicateList.add(criteriaBuilder.lessThanOrEqualTo(root.get("createTime").as(LocalDateTime.class), endTime));
			if(startOpDate != null)
				predicateList.add(criteriaBuilder.greaterThanOrEqualTo(root.get("operateDate").as(LocalDate.class), startOpDate));
			if(endOpDate != null)
				predicateList.add(criteriaBuilder.lessThanOrEqualTo(root.get("operateDate").as(LocalDate.class), endOpDate));
			query.where(predicateList.stream().toArray(Predicate[]::new));
			query.orderBy(criteriaBuilder.desc(root.get("operateDate").as(LocalDate.class)), criteriaBuilder.desc(root.get("id").as(String.class)));
			return query.getRestriction();
		};
		return spe;
	}

}
