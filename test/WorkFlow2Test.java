import com.pillow.panda.MultiSelectedException;
import com.pillow.panda.RpcServerHelper;
import com.pillow.panda.RpcUtils.Selector;
import com.pillow.panda.RpcUtils.UiObjectNotFoundException;
import com.pillow.panda.UninitException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by qianyuxiang on 15/11/5.
 */
public class WorkFlow2Test {
    RpcServerHelper rsh = null;

    @Before
    public void startUp() {
        rsh = new RpcServerHelper();
        rsh.init("localhost", 9008);
    }

    @After
    public void tearDown() {

    }

    @Test
    public void clickPlus() {
        Selector s = new Selector();
        s.setIndex(0);
        s.setClassName("android.widget.ImageView");
        s.setPackageName("com.tencent.mm");
        s.setInstance(0);

        try {
            rsh.click(s);
        } catch (UninitException e) {
            e.printStackTrace();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        } catch (MultiSelectedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void clickAddFriends() {

    }
}
