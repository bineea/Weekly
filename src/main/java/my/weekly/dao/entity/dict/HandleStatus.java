package my.weekly.dao.entity.dict;

public enum HandleStatus {
	NEW("未处理") {},
	DOING("处理中") {},
	DONE("已处理") {},
	;
	
	private String value;
	
	private HandleStatus(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
}
