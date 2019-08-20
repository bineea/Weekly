package my.weekly.dao.entity.dict;

public enum DemandType {
	SJCX("数据查询", false) {},
	YWZX("业务咨询", false) {},
	ZBPZXC("指标配置现场", false) {},
	SQSH("申请审核", false) {},
	TGFB("通告发布", false) {},
	XTLD("系统漏洞", true) {},
	XGNTJ("新功能添加", true) {},
	SJKXG("数据库修改", true) {},
	WDBX("文档编写", true) {},
	CJHY("参加会议", true) {},
	XCBZ("现场保障", true) {},
	YCCL("异常处理", true) {},
	SBJX("设备检修", true) {},
	SJTS("数据推送", true) {},
	WBJGT("委办局沟通", true) {},
	;

	private String value;
	private boolean handleDone;
	
	private DemandType(String value, boolean handleDone) {
		this.value = value;
		this.handleDone = handleDone;
	}

	public String getValue() {
		return value;
	}

	public boolean isHandleDone() {
		return handleDone;
	}
	
}
