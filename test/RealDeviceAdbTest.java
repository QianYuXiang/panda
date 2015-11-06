/**
 * Created by qianyuxiang on 15/10/30.
 */

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pillow.panda.AdbUtils.Adb;
import com.pillow.panda.AdbUtils.EnvironmentError;
import org.junit.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class RealDeviceAdbTest {
    private Adb _adb = null;

    @Before
    public void setUp() {
        _adb = new Adb();
    }

    @After
    public void tearDown() {

    }

    @Test
    public void testAdb() throws EnvironmentError {
        String xx = _adb.adb();
        System.out.println(xx);
    }

    @Test
    public void testForwardList() throws EnvironmentError, IOException {
        List<String> __ = _adb.forward_list();

        for(String _ : __) {
            System.out.println(_);
        }
    }

    @Test
    public void testVersion() throws EnvironmentError, IOException {
        Adb.Version v = _adb.version();

        System.out.println(v.getMajorNumber());
        System.out.println(v.getMinorNumber());
        System.out.println(v.getBuildNumber());
    }

    @Test
    public void testDevices() throws IOException, EnvironmentError {
        Map<String, String> _m = _adb.devices();

        for(Map.Entry<String, String> entry : _m.entrySet()) {
            String _s = String.format("%s : %s" , entry.getKey(), entry.getValue());
            System.out.println(_s);
        }
    }

    @Test
    public void testJackson() {
        JsonGenerator jsonGenerator = null;
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(System.out, JsonEncoding.UTF8);
            jsonGenerator.writeObject(new JacksonClass(1, 2, 3));
            if(jsonGenerator!=null)jsonGenerator.flush();
            if(!jsonGenerator.isClosed())jsonGenerator.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

};