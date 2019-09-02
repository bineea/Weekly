package my.weekly.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.util.Base64Utils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;

import my.weekly.common.pub.CommonAbstract;
import my.weekly.common.pub.MyManagerException;
import my.weekly.common.tools.HttpResponseHelper;
import my.weekly.common.tools.JsonUtil;
import my.weekly.model.NoteModel;

public abstract class AbstractController extends CommonAbstract {
	
	private static final String HEADER_NOTE = "header_note";//addSuccess
	private static final String HEADER_ERROR = "header_error";//MyManagerException
	private static final String HEADER_CVE = "header_cve";//BindException,ConstraintViolationException
	private static final String HEADER_JUMP = "header_jump";//window.location.href

	public void addSuccess(HttpServletResponse response, String message) throws IOException {
		logger.debug("success:{}", message);
		response.setHeader(HEADER_NOTE, Base64Utils.encodeToString(message.getBytes()));
	}
	
	public void toJump(HttpServletResponse response, String uri) throws IOException {
		logger.debug("to jump:{}", uri);
		response.setHeader(HEADER_JUMP, Base64Utils.encodeToString(uri.getBytes()));
	}
	
	@ExceptionHandler(value = MyManagerException.class)
	public void handleMyManagerException(MyManagerException e, HttpServletResponse response) throws IOException {
		logger.debug("handle MyManagerException:{}", e.getMessage());
		NoteModel model = new NoteModel(false, "数据处理异常", e.getMessage());
		jsonResponse(HEADER_ERROR, model.toJson(), response);
	}
	
	//FIXME 无法显示BindException异常信息，但是可以捕获。需解决！！！
	@ExceptionHandler(value = BindException.class)
	public void handleBindException(BindException e, HttpServletResponse response) throws IOException {
		logger.debug("handle BindException:{}", e.getMessage());
		NoteModel model = new NoteModel(false, "数据校验异常", fieldErrors2Json(e.getBindingResult()));
		jsonResponse(HEADER_CVE, model.toJson(), response);
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	public void handleConstraintViolationException(ConstraintViolationException cve, HttpServletResponse response)
			throws IOException
	{
		logger.debug("handle ConstraintViolationException:{}", cve.getMessage());
		jsonResponse(HEADER_CVE, violation2Json(cve), response);
	}
	
	protected final void jsonResponse(String header, String msg, HttpServletResponse response) throws IOException
	{
		response.setHeader(header, Base64Utils.encodeToString(header.getBytes()));
		HttpResponseHelper.responseJson(msg, response);
	}
	
	protected final String fieldErrors2Json(BindingResult result)
	{
		if (!result.hasFieldErrors()) throw new RuntimeException("没有字段错误");
		try
		{
			Map<String, String> map = new HashMap<String, String>();
			for (FieldError error : result.getFieldErrors())
			{
				String msg = error.getDefaultMessage();
				if (error.isBindingFailure()) msg = "数据的类型错误";
				map.put(error.getField(), hasText(msg) ? msg : "数据(" + error.getRejectedValue() + ")错误");
			}
			return JsonUtil.writeValueAsString(map);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			logger.error("", e);
			throw new RuntimeException(e.getMessage());
		}
	}
	
	protected final String violation2Json(ConstraintViolationException cve)
	{
		Set<ConstraintViolation<?>> cvs = cve.getConstraintViolations();
		StringBuilder sb = new StringBuilder("{");
		for (ConstraintViolation<?> cv : cvs)
		{
			sb.append("\"").append(cv.getPropertyPath());
			sb.append("\":\"").append(cv.getMessage()).append("\",");
		}
		if (sb.length() > 1) sb.deleteCharAt(sb.length() - 1);
		sb.append("}");
		return sb.toString();
	}
	
}
