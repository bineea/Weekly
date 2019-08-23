package my.weekly.manager.daily;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import my.weekly.dao.entity.*;
import my.weekly.dao.repo.jpa.MailAttachmentRepo;
import my.weekly.manager.message.SendEmailManager;
import my.weekly.manager.poi.AbstractGenerateExcelManager;
import my.weekly.model.MyFinals;
import my.weekly.model.message.SendEmailInfo;
import my.weekly.model.message.SendEmailResult;
import my.weekly.model.poi.PoiWriteExcelInfo;
import my.weekly.model.weekly.WeeklyModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.hibernate.engine.jdbc.NonContextualLobCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import my.weekly.common.pub.MyManagerException;
import my.weekly.dao.entity.dict.DemandType;
import my.weekly.dao.repo.Spe.WeeklyDailyPageSpe;
import my.weekly.dao.repo.jpa.DailyRepo;
import my.weekly.dao.repo.jpa.DemandRepo;
import my.weekly.dao.repo.jpa.ProjectRepo;
import my.weekly.manager.AbstractManager;
import my.weekly.manager.LoginHelper;
import my.weekly.model.weekly.DailyModel;

@Service
public class DailyManagerImpl extends AbstractManager implements DailyManager, AbstractGenerateExcelManager<Daily> {

	@Autowired
	private DailyRepo dailyRepo;
	@Autowired
	private ProjectRepo projectRepo;
	@Autowired
	private DemandRepo demandRepo;
	@Autowired
	private MailAttachmentRepo mailAttachmentRepo;
	@Autowired
	private SendEmailManager sendEmailManager;
	
	@Override
	public Page<Daily> pageQuery(WeeklyDailyPageSpe spe) {
		return dailyRepo.findAll(spe.handleSpecification(), spe.getPageRequest());
	}

	@Override
	public Daily createDaily(DailyModel dailyModel, HttpServletRequest request) throws MyManagerException {
		validateDaily(dailyModel);
		Daily other = dailyRepo.findByDemandAndOperateDate(dailyModel.getDemandId(), dailyModel.getOperateDate());
		if(other != null)
			throw new MyManagerException("【" + other.getOperateDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "】已存在关于【" + other.getDemand().getTitle() + "】的日报，无法再次提交");
		//TODO 重复日志相似度校验
		User user = LoginHelper.getLoginUser(request);
		Daily daily = getDailyByModel(dailyModel, user);
		dailyRepo.save(daily);
		return daily;
	}
	
	private void validateDaily(DailyModel dailyModel) throws MyManagerException {
		if(!StringUtils.hasText(dailyModel.getProjectId()))
			throw new MyManagerException("所属项目不能为空");
		if(!StringUtils.hasText(dailyModel.getDemandId()))
			throw new MyManagerException("对应需求不能为空");
		if(!StringUtils.hasText(dailyModel.getOperateContent()))
			throw new MyManagerException("具体操作不能为空");
		if(dailyModel.getHandleStatus() == null)
			throw new MyManagerException("操作处理状态不能为空");
		if(dailyModel.getOperateDate() == null)
			throw new MyManagerException("操作处理时间不能为空");
		if(dailyModel.getOperateDate().isAfter(LocalDate.now()))
			throw new MyManagerException("无法执行处理属于未来的操作");
		
		Optional<Project> projectOpt = projectRepo.findById(dailyModel.getProjectId());
		if(!projectOpt.isPresent())
			throw new MyManagerException("未查询到所属项目信息");
		Optional<Demand> demandOpt = demandRepo.findById(dailyModel.getDemandId());
		if(!demandOpt.isPresent())
			throw new MyManagerException("未查询到对应需求信息");
		if(!demandOpt.get().getProject().getId().equals(projectOpt.get().getId()))
			throw new MyManagerException("对应需求与所属项目不匹配");
		if(!StringUtils.hasText(dailyModel.getSqlContent()) && demandOpt.get().getDemandType() == DemandType.SJKXG)
			throw new MyManagerException("sql语句不能为空");
		
	}

	private Daily getDailyByModel(DailyModel dailyModel, User user) {
		Daily daily = new Daily();
		daily.setOperateContent(StringUtils.trimAllWhitespace(dailyModel.getOperateContent()));
		daily.setSqlContent(StringUtils.trimAllWhitespace(dailyModel.getSqlContent()));
		daily.setHandleStatus(dailyModel.getHandleStatus());
		daily.setUser(user);
		daily.setOperateDate(dailyModel.getOperateDate());
		daily.setCreateTime(LocalDateTime.now());
		daily.setUpdateTime(LocalDateTime.now());
		daily.setDemand(demandRepo.findById(dailyModel.getDemandId()).orElse(null));
		return daily;
	}

	@Override
	public Daily findById(String id) {
		Assert.hasText(id,"id不能为空");
		return dailyRepo.findById(id).orElse(null);
	}

	@Transactional
	@Override
	public Daily modifyDaily(DailyModel dailyModel, HttpServletRequest request) throws MyManagerException {
		validateDaily(dailyModel);
		Optional<Daily> dopt = dailyRepo.findById(dailyModel.getDailyId());
		if(!dopt.isPresent())
			throw new MyManagerException("日报信息不存在");
		Daily other = dailyRepo.findByDemandAndOperateDate(dailyModel.getDemandId(), dailyModel.getOperateDate());
		if(other != null && !other.getId().equals(dailyModel.getDailyId()))
			throw new MyManagerException("【" + other.getOperateDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "】已存在关于【" + other.getDemand().getTitle() + "】的日报，无法再次提交");
		//TODO 重复日志相似度校验
		Daily daily = dopt.get();
		User user = LoginHelper.getLoginUser(request);
		if(!user.getId().equals(daily.getUser().getId()))
			throw new MyManagerException("只允许日报作者修改日报信息");
		daily.setOperateContent(StringUtils.trimAllWhitespace(dailyModel.getOperateContent()));
		daily.setSqlContent(StringUtils.trimAllWhitespace(dailyModel.getSqlContent()));
		daily.setHandleStatus(dailyModel.getHandleStatus());
		daily.setOperateDate(dailyModel.getOperateDate());
		daily.setUpdateTime(LocalDateTime.now());
		dailyRepo.save(daily);
		return daily;
	}

	@Override
	public void del(String id, HttpServletRequest request) throws MyManagerException {
		Optional<Daily> dopt = dailyRepo.findById(id);
		if(!dopt.isPresent())
			throw new MyManagerException("日报信息不存在");
		User user = LoginHelper.getLoginUser(request);
		if(!user.getId().equals(dopt.get().getUser().getId()))
			throw new MyManagerException("只允许日报作者删除日报信息");
		dailyRepo.delete(dopt.get());
	}

	@Override
	public MailAttachment combine(WeeklyModel model, HttpServletRequest request) throws MyManagerException, IOException {
		User user = LoginHelper.getLoginUser(request);
		if(user == null)
			throw new MyManagerException("用户信息异常，需重新登录");
		List<Daily> dailyList = dailyRepo.findByOperateDateAndUserAsc(model.getStartOpDate(), model.getEndOpDate(), user.getId());
		if(CollectionUtils.isEmpty(dailyList))
		    throw new MyManagerException("未查询到相关日报数据，无法生成周报");
		if(!dailyList.stream().map(d -> d.getId()).collect(Collectors.toList()).containsAll(model.getDailyIds()))
			throw new MyManagerException("数据异常，已选择日报数据与操作时间不匹配");
		PoiWriteExcelInfo info = initWriteInfo();
		info.setFileName(user.getName()
				+ DateTimeFormatter.ISO_LOCAL_DATE.format(model.getStartOpDate())
				+ "至"
				+ DateTimeFormatter.ISO_LOCAL_DATE.format(model.getStartOpDate())
				+ "海南省小客车保有量调控工作情况.xlsx");
		info.setSavePath(MyFinals.FILE_TMP_PATH + "weekly" + File.separator + user.getId());
		File f = generateExcel(info, dailyList.stream().filter(d -> model.getDailyIds().contains(d.getId())).collect(Collectors.toList()));
		MailAttachment wf = saveMailAttachment(f, user);
		return wf;
	}

	@Override
	public MailAttachment findFileById(String weeklyFileId) {
		return mailAttachmentRepo.findById(weeklyFileId).orElse(null);
	}

	@Override
	public SendEmailResult weekly2SendEmail(SendEmailInfo info, HttpServletRequest request) throws MyManagerException, IOException, MessagingException, SQLException {
		validateSendEmailInfo(info);
		info.setTemplet(null);
		sendEmailManager.sendEmailHandler(info);
		SendEmailResult result = sendEmailManager.saveSendEmailByInfo(info, request);
		return result;
	}

	private void validateSendEmailInfo(SendEmailInfo info) throws MyManagerException {
		SendEmailInfo.validateInfo(info);
		if(!CollectionUtils.isEmpty(info.getMailAttachmentIds())) {
			for(String fileId : info.getMailAttachmentIds()) {
				Optional<MailAttachment> mailAttachmentOpt = mailAttachmentRepo.findById(fileId);
				if(!mailAttachmentOpt.isPresent())
					throw new MyManagerException("附件信息异常，附件ID:"+fileId+"对应数据不存在");
			}
		}
	}

	private MailAttachment saveMailAttachment(File file, User user) throws IOException {
		MailAttachment mailAttachment = new MailAttachment();
		mailAttachment.setName(file.getName());
		mailAttachment.setFile(NonContextualLobCreator.INSTANCE.createBlob(new FileInputStream(file), file.length()));
		mailAttachment.setUser(user);
		mailAttachment.setCreateTime(LocalDateTime.now());
		mailAttachmentRepo.save(mailAttachment);
		return mailAttachment;
	}

	@Override
	public PoiWriteExcelInfo initWriteInfo() {
		PoiWriteExcelInfo info = new PoiWriteExcelInfo();
		info.setRowNo(1);
		info.setSheetNo(0);
		info.setTempletName("SystemMaintainTemplet.xlsx");
		return info;
	}

	@Override
	public void fillData(Row row, Daily daily) {
		Cell cell = getCell(row,0);
		cell.setCellValue(daily.getOperateDate().format(DateTimeFormatter.ofPattern("yyyy/M/d")));
		cell = getCell(row,1);
		cell.setCellValue("海南");
		cell = getCell(row,2);
		cell.setCellValue("调控办");
		cell = getCell(row,3);
		cell.setCellValue("摇号服务");
		cell = getCell(row,4);
		cell.setCellValue(daily.getDemand().getDemandType().getValue());
		cell = getCell(row,5);
		cell.setCellValue(daily.getDemand().getSummary());
		cell = getCell(row,6);
		cell.setCellValue(daily.getOperateContent());
		cell = getCell(row,7);
		cell.setCellValue("无");
		cell = getCell(row,8);
		cell.setCellValue(daily.getHandleStatus().getValue());
		cell = getCell(row,9);
		cell.setCellValue("否");
		cell = getCell(row,10);
		cell.setCellValue(daily.getUser().getName());
	}
}
