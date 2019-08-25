package my.weekly.common.entity;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonFilter;

import my.weekly.common.tools.JsonUtil;

@JsonFilter(value = JsonUtil.FILTER_NAME)
public abstract class BaseEntity {

	public final String toJson() {
		return toJson(false);
	}
	
	public final String toJson(boolean format) {
		
		try {
			String jsonStr = JsonUtil.entityToJson(this);
			return format ? JsonUtil.formatJson(jsonStr) : jsonStr;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
