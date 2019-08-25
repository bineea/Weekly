package my.weekly.common.tools;

import org.springframework.util.Assert;
import org.springframework.util.StreamUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileHelper {

    public static final int BUFFER_SIZE = StreamUtils.BUFFER_SIZE;

    public static byte[] copyToByteArray(InputStream in) throws IOException {
        if (in == null) {
            return new byte[0];
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream(BUFFER_SIZE);
        copy(in, out);
        return out.toByteArray();
    }

    public static int copy(InputStream in, OutputStream out) throws IOException {
        Assert.notNull(in, "No InputStream specified");
        Assert.notNull(out, "No OutputStream specified");
        try {
            return StreamUtils.copy(in, out);
        }
        finally {
            try {
                in.close();
            }
            catch (IOException ex) {
            }
            try {
                out.close();
            }
            catch (IOException ex) {
            }
        }
    }

}
