import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

/**
 * Created by qianyuxiang on 15/11/4.
 */
public class CharsetTest {

    @Before
    public void startUp() {

    }

    @After
    public void tearDown() {

    }

    @Test
    public void testCharset() throws UnsupportedEncodingException {
        String bears = "三只小熊";
        byte[] bytes = bears.getBytes();

        String x = new String(bears.getBytes("utf-8"),"utf-8");
        bytes = x.getBytes();

        String result = new String(x.getBytes(), "utf-8" );
        bytes = result.getBytes();

        System.out.println( "三只小熊".equals( result ));
    }

    @Test
    public void testTrim() {
        String a = "  \n";
        String res = a.trim();

        System.out.println(res);
    }

    @Test
    public void testSplit() {
        String a = "USER     PID   PPID  VSIZE  RSS     WCHAN    PC         NAME\n" +
                "root      1491  1489  460604 26440 ffffffff b75ead11 S uiautomator";
        String[] _ = a.split("\\s+");

        String __ = _[0];
    }
}
