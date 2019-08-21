package my.weekly.dao.repo.jpa;

import my.weekly.dao.entity.SendEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SendEmailRepo extends JpaRepository<SendEmail, String>, JpaSpecificationExecutor<SendEmail> {

}
