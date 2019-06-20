package my.weekly.dao.repo.jpa;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import my.weekly.dao.entity.Demand;

public interface DemandRepo extends JpaRepository<Demand, String>, JpaSpecificationExecutor<Demand> {

	Page<Demand> findAll(Specification<Demand> spec, Pageable pageable);
	
	@Query(value = " select d from Demand d where d.project.id = ?1 and d.title = ?2")
	Demand findByProjectAndTitle(String projectId, String title);
	
	@Query(value = " select d from Demand d where d.project.id = ?1 order by updateTime desc")
	List<Demand> findByProjectDesc(String projectId);
}
