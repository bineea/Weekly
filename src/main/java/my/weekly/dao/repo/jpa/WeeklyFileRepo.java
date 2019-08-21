package my.weekly.dao.repo.jpa;

import my.weekly.dao.entity.WeeklyFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface WeeklyFileRepo extends JpaRepository<WeeklyFile, String>, JpaSpecificationExecutor<WeeklyFile> {

}
