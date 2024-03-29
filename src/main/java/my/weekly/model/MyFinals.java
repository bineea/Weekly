package my.weekly.model;

import java.io.File;
import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

public class MyFinals {
	
	public static String COOKIE_USER = "mycookieuser";

//	默认分页查询页码
	public static final int DEFAULT_PAGE_NUM = 0;
//	默认分页查询单页数量
	public static final int DEFAULT_PAGE_SIZE = 10;
//	默认分页查询单页文章数量
	public static final int DEFAULT_WEEKLY_SIZE = 3;
//	jstree请求根目录ID
	public static final String jsTreeRootReq = "#";
//	默认文章摘要字符长度
	public static final int DEFAULT_SUMMANY_LENGTH = 27;
//	Excel文件名后缀xlsx
	public static final String EXCEL_XLSX = "xlsx";
//	生成文件临时路径
	public static final String FILE_TMP_PATH = File.separator + "tmp" + File.separator;
	
//	手机号码正则
	public static final String mobileRegexp = "^1[0-9]{10}$";
	public static final Pattern mobilePattern = Pattern.compile(mobileRegexp);
//	邮箱地址正则
	public static final String emailRegexp = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$";
	public static final Pattern emailPattern = Pattern.compile(emailRegexp);
//	账号正则
	public static final String loginNameRegexp = "^[0-9A-Za-z]{3,18}$";
	public static final Pattern loginNamePattern = Pattern.compile(loginNameRegexp);
	
	/**
	 * 校验手机号码格式
	 * @param mobile
	 * @return
	 */
	public static boolean checkMobileFormat(String mobile) {
		if (!StringUtils.hasText(mobile)) return false;
		if (mobile.length() != 11) return false;
		return mobilePattern.matcher(mobile).matches();
	}
	
	/**
	 * 校验邮箱地址格式
	 * @param email
	 * @return
	 */
	public static boolean checkEmailFormat(String email) {
		if (!StringUtils.hasText(email)) return false;
		return emailPattern.matcher(email).matches();
	}
	
	/**
	 * 校验账号格式
	 * @param loginName
	 * @return
	 */
	public static boolean checkLoginNameFormat(String loginName) {
		if (!StringUtils.hasText(loginName)) return false;
		return loginNamePattern.matcher(loginName).matches();
	}
}
