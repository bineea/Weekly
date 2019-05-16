package my.weekly.dao.entity.dict;

public enum HandleStatus {
	NEW("未处理") {},
	DOING("处理中") {},
	DONE("处理完成") {},
	;
	
	private String value;
	
	private HandleStatus(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
}
