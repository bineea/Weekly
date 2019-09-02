package my.weekly.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class NoteModel extends BaseModel {

	@Getter
	@Setter
	private boolean success;
	@Getter
	@Setter
	private String msg;
	@Getter
	@Setter
	private Object resultObj;
	
}
