package my.weekly.dao.repo.Spe;

import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.Specification;

import my.weekly.dao.entity.Project;

public class WeeklyProjectPageSpe extends AbstractPageSpecification<Project> {

	@Override
	public Specification<Project> handleSpecification() {
		Specification<Project> spe = (root, query, criteriaBuilder) -> {
			query.orderBy(criteriaBuilder.desc(root.get("createTime").as(LocalDateTime.class)), criteriaBuilder.desc(root.get("id").as(String.class)));
			return query.getRestriction();
		};
		return spe;
	}

}
