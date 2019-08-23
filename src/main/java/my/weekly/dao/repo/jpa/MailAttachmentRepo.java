package my.weekly.dao.repo.jpa;

import my.weekly.dao.entity.MailAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MailAttachmentRepo extends JpaRepository<MailAttachment, String>, JpaSpecificationExecutor<MailAttachment> {

}
