package com.pillow.panda;

import com.googlecode.jsonrpc4j.HttpException;
import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
import com.googlecode.jsonrpc4j.ProxyUtil;
import com.pillow.panda.RpcUtils.AutomatorService;
import com.pillow.panda.RpcUtils.Selector;
import com.pillow.panda.RpcUtils.UiObjectNotFoundException;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by qianyuxiang on 15/11/5.
 */
public class RpcServerHelper {

    private JsonRpcHttpClient client = null;
    private AutomatorService service = null;

    private boolean isInit = false;

    private String url = null;
    private Integer port = 0;

    public boolean init(String url, Integer port) {
        if(!isInit){
            this.url = url;
            this.port = port;

            String _ = String.format("http://%s:%d/jsonrpc/0", this.url, this.port);
            try {
                client = new JsonRpcHttpClient(new URL(_));
            }
            catch (MalformedURLException e) {
                return false;
            }
            service = ProxyUtil.createClientProxy(getClass().getClassLoader(), AutomatorService.class, client);

            isInit = true;
        }
        return this.isInit;
    }

    public String ping() throws HttpException {
        return this.service.ping();
    }

    public boolean isAlive() throws HttpException {
        return "pong".equals(this.ping());
    }

    public boolean exists(Selector s) throws UiObjectNotFoundException, MultiSelectedException, UninitException, HttpException {
        assertInit();

        return this.service.exist(s);
    }

    public int count(Selector s) throws UiObjectNotFoundException, MultiSelectedException, UninitException, HttpException {
        assertInit();

        return this.service.count(s);
    }

    public boolean click(Selector s) throws UninitException, UiObjectNotFoundException, MultiSelectedException, HttpException {
        assertInit();
        assertSelector(s);

        try {
            this.service.click(s);
            return true;
        }
        catch (UiObjectNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public boolean setText(Selector s, String text) throws UninitException, MultiSelectedException, UiObjectNotFoundException, HttpException {
        assertInit();
        assertSelector(s);

        return this.service.setText(s, text);
    }

    public void ASSERT(Selector s) throws UninitException, MultiSelectedException, UiObjectNotFoundException {
        try {
            assertInit();
            assertSelector(s);
        } catch (UninitException e) {
            e.printStackTrace();
            throw e;
        } catch (MultiSelectedException e) {
            e.printStackTrace();
            throw e;
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void assertInit() throws UninitException {
        if(!this.isInit)
            throw new UninitException("RpcServerHelper error : RpcServer not init yet");
    }

    public void assertSelector(Selector s, boolean bRetry) throws UiObjectNotFoundException, MultiSelectedException, UninitException, HttpException {
        if(!this.isInit)
            throw new UninitException("RpcServerHelper error : not init RpcServerHelper");

        int c = this.service.count(s);
        if(c == 0)
        {
            if(bRetry){
                boolean isOk = this.service.waitForExists(s, 10000);
                if(isOk)return;
                assertSelector(s, false);
            }
            else {
                throw new UiObjectNotFoundException("RpcServerHelper error : none can be clicked");
            }
        }

        if(c > 1)
            throw new MultiSelectedException("RpcServerHelper error : multi is selected");
    }

    public void assertSelector(Selector s) throws UiObjectNotFoundException, MultiSelectedException, UninitException {
        this.assertSelector(s, true);
    }

    public String getUrl() {
        return this.url;
    }

    public Integer getPort() {
        return this.port;
    }

    public AutomatorService getService() throws UninitException {
        assertInit();

        return this.service;
    }
}
