package my.weekly.dao.entity.dict;

public enum DemandType {
	SJCX("数据查询", false) {},
	YWZX("业务咨询", false) {},
	XTLD("系统漏洞", true) {},
	XGNTJ("新功能添加", true) {},
	WDBX("文档编写", true) {},
	CJHY("参加会议", true) {},
	ZSSJXG("正式数据修改", true) {},
	QT("其他", true) {},
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
