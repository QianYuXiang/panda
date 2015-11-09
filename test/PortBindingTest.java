import com.pillow.panda.AutomatorServer;
import org.junit.Test;

/**
 * Created by qianyuxiang on 15/11/7.
 */
public class PortBindingTest {
    @Test
    public void test() {
        boolean isAvailable = AutomatorServer.isPortAvailable(9008);
        isAvailable = AutomatorServer.isPortAvailable("127.0.0.1", 9008);
        isAvailable = AutomatorServer.isPortAvailable("0.0.0.0", 9008);

        int p = AutomatorServer.nextAvailablePort("localhost", 9008);

        System.out.println(p);
    }
}
