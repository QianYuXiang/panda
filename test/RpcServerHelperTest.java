import com.googlecode.jsonrpc4j.HttpException;
import com.pillow.panda.MultiSelectedException;
import com.pillow.panda.RpcServerHelper;
import com.pillow.panda.RpcUtils.ObjInfo;
import com.pillow.panda.RpcUtils.Selector;
import com.pillow.panda.RpcUtils.UiObjectNotFoundException;
import com.pillow.panda.SelectorPool;
import com.pillow.panda.UninitException;
import org.apache.http.protocol.HTTP;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by qianyuxiang on 15/11/8.
 */
public class RpcServerHelperTest {
    private RpcServerHelper rsh = null;

    @Before
    public void startUp() {
        rsh = new RpcServerHelper();
        rsh.init("localhost", 9008);
    }

    @Test
    public void testEasy() {
        try {
            String pong = rsh.ping();
            boolean isAlive = rsh.isAlive();
        } catch (HttpException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testMedium() {
        int c = -1;

        Selector plusBtn = new Selector();
        plusBtn.setIndex(0);
        plusBtn.setClassName("android.widget.ImageView");
        plusBtn.setPackageName("com.tencent.mm");

        Selector searchBtn = new Selector();
        searchBtn.setIndex(0);
        searchBtn.setClassName("android.widget.TextView");
        searchBtn.setPackageName("com.tencent.mm");
        searchBtn.setDescription("搜索");

        try {
            c = rsh.getService().count(searchBtn);
        } catch (UninitException e) {
            e.printStackTrace();
        }

        plusBtn.setChildOrSiblingSelector(new Selector[]{searchBtn});

        c = -1;
        try {
            c = rsh.getService().count(plusBtn);
        } catch (UninitException e) {
            e.printStackTrace();
        } catch (HttpException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testDump() {
        String window = null;
        try {
            window = rsh.getService().dumpWindowHierarchy(false);
        } catch (UninitException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void objInfo() {
        Selector plusBtn = new Selector();
        plusBtn.setIndex(0);
        plusBtn.setClassName("android.widget.ImageView");
        plusBtn.setPackageName("com.tencent.mm");

        ObjInfo[] objInfos;
        try {
            objInfos = rsh.getService().objInfoOfAllInstances(plusBtn);
        } catch (UninitException e) {
            e.printStackTrace();
        } catch (HttpException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getUiObject() {
        Selector plusBtn = new Selector();
        plusBtn.setIndex(0);
        plusBtn.setClassName("android.widget.ImageView");
        plusBtn.setPackageName("com.tencent.mm");
        plusBtn.setInstance(0);

        String id;
        try {
            id = rsh.getService().getUiObject(plusBtn);
            rsh.getService().removeUiObject(id);
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        } catch (UninitException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFlow() throws Exception {
        try {
            String[] searchList = new String[]{"15623423434", "15913137919", "15867190699", "13401122164",
            "13812533551", "13381723331", "13533829901", "18688660973", "13632398654", "18565841684"};

            rsh.click( SelectorPool.plusBtnSelector() );
            rsh.click( SelectorPool.addBtnLineSelector() );
            rsh.click( SelectorPool.searchBtnSelector() );

            for(String searchContent : searchList) {
                if( rsh.exists( SelectorPool.clearBtnSelector() ))
                    rsh.click( SelectorPool.clearBtnSelector() );

                rsh.setText( SelectorPool.topSearchEditSelector(), searchContent);
                rsh.click( SelectorPool.searchLineSelector(searchContent) );

                int retry = 5;
                int count = 0;
                while(retry > 0){
                    count = rsh.getService().count(SelectorPool.noUserFoundSelector());
                    if(count == 1) {
                        rsh.click( SelectorPool.okBtnSelector() );
                        break;
                    }
                    else if(count > 2) {
                        throw new Exception("never will be reach");
                    }
                    else {
                        Thread.sleep(1000);
                    }

                    --retry;
                }

                if(count == 1) {
                    continue;
                }

                if(rsh.exists( SelectorPool.videoChatSelector() )) {
                    rsh.click( SelectorPool.retBtnSelector() );
                    System.out.println(String.format("该用户: %s 已经添加了", searchContent));
                    continue;
                }


                rsh.click( SelectorPool.addBtnSelector() );
                rsh.click( SelectorPool.sendBtnSelector() );

                boolean isRetBtnOk = rsh.getService().waitForExists(SelectorPool.retBtnSelector(), 10000);
                if(isRetBtnOk){
                    rsh.click(SelectorPool.retBtnSelector());
                }
                else {
                    throw new Exception("time out");
                }
            }





        } catch (UninitException e) {
            e.printStackTrace();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        } catch (MultiSelectedException e) {
            e.printStackTrace();
        }

    }


}
