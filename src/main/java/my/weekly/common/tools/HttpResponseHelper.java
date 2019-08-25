package my.weekly.common.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.zip.GZIPOutputStream;

public class HttpResponseHelper {

	protected static Logger logger = LoggerFactory.getLogger(HttpResponseHelper.class);
	private static final int BUFFER = 1024;
	private static final int MAX_MEM_SIZE = 1024 * 1024 * 16;// 16M

	public static void responseJson(String jsonStr, final HttpServletResponse response) throws IOException
	{
		setJsonResponseHeader(response);
		response.getWriter().print(jsonStr);
		response.getWriter().flush();
		response.getWriter().close();
	}
	
	public static void showPicture(InputStream input,HttpServletResponse response) throws IOException
	{
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Expires", "0");
		response.setContentType("image/jpeg");
		OutputStream  output = response.getOutputStream();
		try
		{
			byte[] b = new byte[BUFFER];
			while(input.read(b) > -1)
			{
				output.write(b);
				b = new byte[BUFFER];
			}
			output.flush();
		}
		finally
		{
			if(input != null)
			{
				input.close();
				input = null;
			}
			if(output != null)
			{
				output.close();
				output = null;
			}
		}
	}

	/**
	 * 会根据客户端判断是否支持gzip而启动gzip
	 * 
	 * @param jsonStr
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public static void responseJson(String jsonStr, HttpServletRequest request, HttpServletResponse response)
			throws IOException
	{
		boolean supportGzip = isClientSupportGzip(request);
		if (supportGzip) response.setHeader("Content-Encoding", "gzip");
		PrintWriter writer = supportGzip
				? new PrintWriter(new BufferedWriter(
						new OutputStreamWriter(new GZIPOutputStream(response.getOutputStream()), "UTF-8")))
				: response.getWriter();
		setJsonResponseHeader(response);
		writer.print(jsonStr);
		writer.close();
	}

	public static void downloadFile(InputStream input, String fileName, HttpServletRequest request, HttpServletResponse response, boolean enableGzip) throws IOException {
		Assert.notNull(input, "文件流不能为空");
		Assert.hasText(fileName, "文件名称不能为空");
		setDownloadHeader(fileName, request, response);
		OutputStream output = null;
		InputStream fis = null;
		try
		{
			fis = input;
			output = response.getOutputStream();
			boolean useGzip = enableGzip && isClientSupportGzip(request);
			int size = fis.available();
			if (useGzip)
			{
				response.setHeader("Content-Encoding", "gzip");
				if (size <= MAX_MEM_SIZE)
				{
					byte[] bytes = ZipHelper.gzip(FileHelper.copyToByteArray(fis));
					response.setContentLength(bytes.length);// 压缩后的长度
					fis = new ByteArrayInputStream(bytes);
				}
				else
				{
					output = new GZIPOutputStream(response.getOutputStream());
				}
			}
			else
			{
				response.setContentLength(size);
			}
			byte[] b = new byte[BUFFER];
			int i = 0;
			while ((i = fis.read(b)) > 0)
			{
				output.write(b, 0, i);
			}
			output.flush();
		}
		finally
		{
			if (fis != null)
			{
				fis.close();
				fis = null;
			}
			if (output != null)
			{
				output.close();
				output = null;
			}
		}
	}

	private static void setDownloadHeader(String fileName, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		Assert.hasText(fileName, "文件名称不能为空");
		String ua = request.getHeader("user-agent"); // 获取终端类型
		if (ua == null) ua = " User-Agent: Mozilla/4.0 (compatible; MSIE 6.0;) ";
		boolean isIE = ua.toLowerCase().indexOf(" msie ") != -1;

		response.setCharacterEncoding("UTF-8");
		response.setHeader("Cache-Control", "must-revalidate,post-check=0,pre-check=0");
		response.setContentType("application/octet-stream;charset=ISO8859-1");
		if (isIE) response.setContentType("application/x-download");// 设置为下载application/x-download
		// 解决中文乱码
		response.setHeader("Content-Disposition",
				"attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO8859-1"));

	}

	private static boolean isClientSupportGzip(HttpServletRequest request)
	{
		String headEncoding = request.getHeader("Accept-Encoding");
		boolean supportGzip = headEncoding != null && (headEncoding.toLowerCase().indexOf("gzip") != -1);
		return supportGzip;
	}

	private static void setJsonResponseHeader(HttpServletResponse response)
	{
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Expires", "0");
		response.setContentType("application/json");
	}
}
