package my.weekly.common.tools;

import org.springframework.util.Assert;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class ZipHelper {

    public static final int BUFFER = 1024;
    /**
     * 压缩二进制
     *
     * @param data
     * @return
     * @throws IOException
     */
    public static byte[] gzip(byte[] data) throws IOException
    {
        Assert.notNull(data, "data can not be null.");
        ByteArrayOutputStream out = new ByteArrayOutputStream(BUFFER);
        GZIPOutputStream gzip = new GZIPOutputStream(out);
        gzip.write(data);
        gzip.close();
        return out.toByteArray();
    }

    /**
     * 解压二进制
     *
     * @param data
     * @return
     * @throws IOException
     */
    public static byte[] unzip(byte[] data) throws IOException
    {
        Assert.notNull(data, "data can not be null.");
        ByteArrayOutputStream out = new ByteArrayOutputStream(BUFFER);
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        GZIPInputStream gunzip = new GZIPInputStream(in);
        byte[] buffer = new byte[BUFFER];
        int n;
        while ((n = gunzip.read(buffer)) >= 0)
        {
            out.write(buffer, 0, n);
        }
        gunzip.close();
        return out.toByteArray();
    }
}
