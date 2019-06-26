package my.weekly.dao.repo.jpa;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import my.weekly.dao.entity.Daily;

public interface DailyRepo extends JpaRepository<Daily, String>, JpaSpecificationExecutor<Daily>{

	Page<Daily> findAll(Specification<Daily> spec, Pageable pageable);
	
	@Query(value = " select d from Daily d where d.demand.id = ?1 order by createTime desc")
	List<Daily> findByDemandDesc(String demandId);
	
	@Query(value = " select d from Daily d where d.demand.id = ?1 and d.user.id = ?2 order by createTime desc")
	List<Daily> findByDemandAndUserDesc(String demandId, String userId);
	
	@Query(value = " select d from Daily d where d.demand.id = ?1 and operateDate = ?2")
	Daily findByDemandAndOperateDate(String demandId, LocalDate operateDate);
}
