/**
 * Created by qianyuxiang on 15/11/3.
 */

import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
import com.googlecode.jsonrpc4j.ProxyUtil;
import com.pillow.panda.RpcUtils.AutomatorService;
import com.pillow.panda.RpcUtils.DeviceInfo;
import com.pillow.panda.RpcUtils.Selector;
import com.pillow.panda.RpcUtils.UiObjectNotFoundException;
import org.junit.*;

import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;

public class WorkFlowTest {
    private JsonRpcHttpClient client = null;
    private AutomatorService service = null;

    @Before
    public void startup() throws MalformedURLException {
        client = new JsonRpcHttpClient(new URL("http://localhost:9008/jsonrpc/0"));
        service = ProxyUtil.createClientProxy(getClass().getClassLoader(), AutomatorService.class, client);
    }

    @After
    public void tearDown() {

    }

    @Test
    public void testClick() {
        Selector obj = new Selector();
        obj.setClassName("android.widget.EditText");
        obj.setIndex(1);
        obj.setPackageName("com.tencent.mm");

        Selector btn = new Selector();
        btn.setIndex(0);
        btn.setText("登录");
        btn.setClassName("android.widget.Button");
        btn.setPackageName("com.tencent.mm");

        try {
            service.setText(obj, "qyx938499");
            service.click(btn);
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testPing() {
        String pong = service.ping();
        System.out.println(pong);
    }

    @Test
    public void testDeviceInfo() {
        DeviceInfo di = service.deviceInfo();

        System.out.print(di.getSdkInt());
    }

    @Test
    public void testGetAllWatcher() {
        String[] watchers = service.getWatchers();

        System.out.println(watchers.length);
    }

    @Test
    public void testAddFriends() {
        Selector s = new Selector();
        s.setIndex(0);
        s.setClassName("android.widget.ImageView");
        s.setPackageName("com.tencent.mm");
        s.setInstance(0);

        int c = service.count(s);

        Selector addFriendBtn = new Selector();
        addFriendBtn.setIndex(1);
        addFriendBtn.setText("添加朋友");
        addFriendBtn.setClassName("android.widget.TextView");
        addFriendBtn.setPackageName("com.tencent.mm");

        c = service.count(addFriendBtn);

        Selector contactText = new Selector();
        contactText.setIndex(1);
        contactText.setText("微信号/QQ号/手机号");
        contactText.setClassName("android.widget.TextView");
        contactText.setPackageName("com.tencent.mm");

        try {
            service.click(s);
            boolean exists = service.waitForExists(addFriendBtn, 10000);
            if(exists) {
                service.click(addFriendBtn);

                exists = service.waitForExists(contactText, 10000);
                if(exists)service.setText(contactText, "15699763608");
            }
            else
                System.out.println("超时");
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
    }

}
