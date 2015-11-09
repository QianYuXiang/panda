import com.pillow.panda.AutomatorServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

/**
 * Created by qianyuxiang on 15/11/7.
 */
public class AutomatorServerTest {
    AutomatorServer as = null;

    @Before
    public void startUp() {

    }

    @After
    public void tearDown() {

    }

    @Test
    public void test() {

    }

    @Test
    public void testRegex() {

        String line = "192.168.0.1:5555\ttcp:9008\ttcp:9008";
        String[] words = line.split("\t");

        String serial = words[0];

        String[] _ = words[1].split("\\:");
        String device_port = _[1];

        _ = words[2].split("\\:");
        String local_port = _[1];

    }

    @Test
    public void testBinPath() {
        String path = (new File("bin", "bundle.jar")).getAbsolutePath();
        path = (new File("bin", "uiautomator-stub.jar")).getAbsolutePath();

        Integer k = 1;
    }
}
