package my.weekly.dao.entity.dict;

public enum Area {
	BEIJING("北京") {},
	HAINAN("海南") {},
	;
	
	private String value;
	
	private Area(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
}
