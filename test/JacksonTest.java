import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pillow.panda.RpcUtils.DeviceInfo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;

import java.io.IOException;

/**
 * Created by qianyuxiang on 15/11/4.
 */
public class JacksonTest {
    @Before
    public void startUp() {

    }

    @After
    public void tearDown() {

    }

    @Test
    public void testJackson() {
        DeviceInfo d = new DeviceInfo();
        d.setCurrentPackageName("com.tencent.mm");

        JsonGenerator jsonGenerator = null;
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(System.out, JsonEncoding.UTF8);
            jsonGenerator.writeObject(d);
            if(jsonGenerator!=null)jsonGenerator.flush();
            if(!jsonGenerator.isClosed())jsonGenerator.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testJacksonObject2Str() {
        SimpleJacksonClass s = new SimpleJacksonClass();
        s.setX(1);
        //s.setY(2);

        p(s);


    }

    @Test
    public void testStr2JacksonObject() {
        String jsonStr = "{\"x\":1,\"y\":0,\"where\":\"123\"}";

        ObjectMapper om = new ObjectMapper();
        try {
            SimpleJacksonClass obj = om.readValue(jsonStr, SimpleJacksonClass.class);

            obj.getS();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void p(Object obj) {
        JsonGenerator jsonGenerator = null;
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(System.out, JsonEncoding.UTF8);
            jsonGenerator.writeObject(obj);
            if(jsonGenerator!=null)jsonGenerator.flush();
            if(!jsonGenerator.isClosed())jsonGenerator.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
