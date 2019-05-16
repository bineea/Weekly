package my.weekly.dao.repo.jpa;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import my.weekly.dao.entity.Project;

public interface ProjectRepo extends JpaRepository<Project, String>, JpaSpecificationExecutor<Project> {

	Page<Project> findAll(Specification<Project> spec, Pageable pageable);
	
	@Query(value=" select p from Project p where p.name = ?1 or p.abbr = ?2")
	List<Project> findByNameOrAbbr(String name, String abbr);
}
