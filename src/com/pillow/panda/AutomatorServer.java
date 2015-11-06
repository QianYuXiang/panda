package com.pillow.panda;

import com.pillow.panda.AdbUtils.Adb;
import com.pillow.panda.RpcUtils.Selector;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by qianyuxiang on 15/11/3.
 */
public class AutomatorServer {
    private Map<String, String> jar_file = null;
    private Map<String, String> apk_file = null;
    private int sdk = 0;

    private Adb adb = null;
    private int device_port;
    private int local_port;

    public AutomatorServer() {
        this.jar_file = new HashMap<String, String>();
        this.jar_file.put("bundle.jar", "bin/bundle.jar");
        this.jar_file.put("uiautomator-stub.jar", "bin/uiautomator-stub.jar");

        this.apk_file = new HashMap<String, String>();
        this.apk_file.put("app-uiautomator.apk", "bin/app-uiautomator.apk");
        this.apk_file.put("app-uiautomator-test.apk", "bin/app-uiautomator-test.apk");

        String _ = System.getenv("UIAUTOMATOR_DEVICE_PORT");
        if( _ != null ){
            this.device_port = Integer.parseInt(_);
        }
        else {
            this.device_port = 9008;
        }

        _ = System.getenv("UIAUTOMATOR_LOCAL_PORT");
        if( _ != null ){
            this.local_port = Integer.parseInt(_);
        }
        else {
            this.local_port = 9008;
        }
    }

    public void init(String _serial, int _local_port, int _device_port, String _adb_server_host, int _adb_server_port) {
        this.adb = new Adb(_serial, _adb_server_host, String.format("%d", _adb_server_port));
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
