package com.pillow.panda;

import com.pillow.panda.AdbUtils.Adb;
import com.pillow.panda.AdbUtils.EnvironmentError;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.http.client.HttpClient;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by qianyuxiang on 15/11/3.
 */
public class AutomatorServer {
    private Map<String, String> jar_file = null;
    private Map<String, String> apk_file = null;
    private Integer sdk = 0;

    private Adb adb = null;
    private Integer device_port;
    private Integer local_port;

    private Process uiautomatorProcess = null;

    private RpcServerHelper rpcServerHelper = null;

    public AutomatorServer() {
        this.jar_file = new HashMap<String, String>();

        String bundleJarPath = (new File("bin", "bundle.jar")).getAbsolutePath();
        String stubJarPath = (new File("bin", "uiautomator-stub.jar")).getAbsolutePath();
        this.jar_file.put("bundle.jar", bundleJarPath);
        this.jar_file.put("uiautomator-stub.jar", stubJarPath);

        this.apk_file = new HashMap<String, String>();

        String appUiautomatorApkPath = (new File("bin", "app-uiautomator.apk")).getAbsolutePath();
        String appUiautomatorTestApkPath = (new File("bin", "app-uiautomator-test.apk")).getAbsolutePath();
        this.apk_file.put("app-uiautomator.apk", appUiautomatorApkPath);
        this.apk_file.put("app-uiautomator-test.apk", appUiautomatorTestApkPath);

        String _ = System.getenv("UIAUTOMATOR_DEVICE_PORT");
        if( _ != null && !"".equals(_) ){
            this.device_port = Integer.parseInt(_);
        }
        else {
            this.device_port = Config.DEVICE_PORT;
        }

        _ = System.getenv("UIAUTOMATOR_LOCAL_PORT");
        if( _ != null && !"".equals(_)){
            this.local_port = Integer.parseInt(_);
        }
        else {
            this.local_port = Config.LOCAL_PORT;
        }
    }

    public void init() throws Exception {
        this.init(null, null, null, null, null);
    }

    // _local_port can be zero and then it will be decided by system
    public void init(String _serial, Integer _local_port, Integer _device_port, String _adb_server_host, Integer _adb_server_port) throws Exception {

        String osName = System.getProperty("os.name");
        if(osName!=null && !"nt".equals(osName)){
            boolean isOk = Adb.connectHaimawanOnMacOs();
            if(!isOk)throw new Exception("在非nt系统上识别海马玩模拟器失败");
        }

        this.adb = new Adb(_serial, _adb_server_host, _adb_server_port);

        if(_device_port != null)
            this.device_port = _device_port;
        else{
            this.device_port = Config.DEVICE_PORT;
        }

        if(_local_port != null)
            this.local_port = _local_port;
        else{
            try {
                List<String> fl = this.adb.forward_list();

                for(String line : fl) {
                    line = line.trim();
                    if(line != null && !"".equals(line) && !"\n".equals(line)) {
                        String[] words = line.split("\t");

                        if(words.length < 3)throw new Exception("the words in the line less than 3");

                        String serial = words[0];

                        if(!serial.equals(this.adb.device_serial()))continue;

                        String device = words[1];
                        String local = words[2];

                        String[] _1 = device.split("\\:");

                        if(_1.length < 2)throw new Exception("the words in device port strings less than 2");
                        String device_port = _1[1];

                        String[] _2 = local.split("\\:");
                        if(_2.length < 2)throw new Exception("the words in local port string less than 2");

                        String local_port = _2[1];

                        if(Integer.parseInt(local_port) == this.device_port) {
                            this.local_port = Integer.parseInt(device_port);
                            break;
                        }

                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                this.local_port = AutomatorServer.nextAvailablePort(_adb_server_host, this.local_port);
            }
        }
    }

    public boolean push(/*输出参数*/List<String> _p) {
        try{
            for(Map.Entry<String, String> entry : this.jar_file.entrySet()) {
                _p.add(entry.getKey());
                List<String> params = new ArrayList<String>();
                params.add("push");
                params.add(entry.getValue());
                params.add("/data/local/tmp/");
                return this.adb.cmd(params).waitFor() == 0;
            }
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        } catch (EnvironmentError environmentError) {
            environmentError.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean install() {
        try {
            for(Map.Entry<String, String> entry : this.apk_file.entrySet()) {
                List<String> params = new ArrayList<String>();
                params.add("install");
                params.add("-rt");
                params.add(entry.getValue());
                return this.adb.cmd(params).waitFor() == 0;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (EnvironmentError environmentError) {
            environmentError.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean start(Double timeout) throws Exception {
        List<String> params = new ArrayList<String>();
        try {
            if(this.sdk_version() < 18){
                List<String> _ = new ArrayList<String>();
                boolean isOk = this.push(_);
                if(!isOk)return false;

                params.add("shell");
                params.add("uiautomator");
                params.add("runtest");
                for(String p : _){
                    params.add(p);
                }
                params.add("-c");
                params.add("com.github.uiautomatorstub.Stub");
            }
            else {
                boolean isOk = this.install();
                if(!isOk)return false;

                params.add("shell");
                params.add("am");
                params.add("instrument");
                params.add("-w");
                params.add("com.github.uiautomator.test/android.support.test.runner.AndroidJUnitRunner");
            }

            this.uiautomatorProcess = this.adb.cmd(params);
            this.adb.forward(this.local_port, this.device_port);

            boolean isOk = rpcServerHelper.init(this.adb.getAdbServerHost(), this.adb.getAdbServerPort());
            if(!isOk)return false;

            while (!rpcServerHelper.isAlive() && timeout > 0) {
                Thread.sleep(100);
                timeout -= 0.1;
            }

            if(!rpcServerHelper.isAlive())throw new Exception("Rpc Server not started");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (EnvironmentError environmentError) {
            environmentError.printStackTrace();
            return false;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean start() throws Exception {
        return this.start((double) 5.0);
    }

    public boolean stop() throws Exception {
        String uri = String.format("http://%s:%d/stop", this.rpcServerHelper.getUrl(), this.rpcServerHelper.getPort());
        GetMethod gm = new GetMethod(uri);
        try {
            String res = gm.getResponseBodyAsString();
            if(!"Server stopped!!!".equals(res))return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        this.uiautomatorProcess.destroy();

        // 强制杀死
        Integer pid = this.automatorPid();
        if(pid==-1)return true;
        List<String> params = new ArrayList<String>();
        params.add("shell");
        params.add("kill");
        params.add("-9");
        params.add(pid.toString());

        this.adb.execute(params);
        return true;
    }

    public RpcServerHelper getRpcServerHelper() {
        return this.rpcServerHelper;
    }

    public Integer automatorPid() {
        List<String> params = new ArrayList<String>();
        params.add("shell");
        params.add("ps");
        params.add("-C");
        params.add("uiautomator");

        Integer pid = -1;

        List<String> resList = null;
        try {
            resList = this.adb.execute(params);
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        } catch (EnvironmentError environmentError) {
            environmentError.printStackTrace();
            return -1;
        }

        int index = 0;
        int lineIndex = 0;

        for(String line : resList) {
            if(lineIndex == 0) {
                String[] words = line.split("\\s+");
                for(String x : words) {
                    if("PID".equalsIgnoreCase(x))
                        break;
                    else
                        ++index;
                }

                if(index >= words.length)
                    return -1;
            }
            else {
                String[] _ = line.split("\\s+");
                String pidNumber = _[index];
                pid = Integer.parseInt(pidNumber);
            }
            ++lineIndex;
        }

        return pid;
    }

    public int sdk_version() throws IOException, EnvironmentError {
        if(this.sdk == 0) {
            List<String> params = new ArrayList<String>();
            params.add("shell");
            params.add("getprop");
            params.add("ro.build.version.sdk");
            this.adb.execute(params);
        }

        return this.sdk;
    }

    static public int nextAvailablePort(String host, int port) {
        int _ = port;
        while( !isPortAvailable(host, _) ) {
            ++_;
        }
        return _;
    }

    static void bindPort(String host, int port) throws IOException {
        Socket s = new Socket();
        s.bind( new InetSocketAddress(host, port) );
        s.close();
    }

    static public boolean isPortAvailable(int port) {
        Socket s = new Socket();

        try {
            bindPort("0.0.0.0", port);
            bindPort(InetAddress.getLocalHost().getHostAddress(), port);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    static public boolean isPortAvailable(String host, int port) {
        Socket s = new Socket();

        try {
            bindPort(host, port);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

}
