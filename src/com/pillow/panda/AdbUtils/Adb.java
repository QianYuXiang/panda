package com.pillow.panda.AdbUtils;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by qianyuxiang on 15/10/30.
 */
public class Adb {

    private String __adb_cmd = null;
    private String default_serial = null;
    private String adb_server_host = null;
    private String adb_server_port = null;
    private List<String> adb_host_port_options = null;

    private Pattern pattern = Pattern.compile("(\\d+)\\.(\\d+)\\.(\\d+)");

    public Adb() {
        this(null, null, null);
    }

    public Adb(String _serial, String _adb_server_host, String _adb_server_port) {
        if( _serial == null )
            this.default_serial = System.getProperty("ANDROID_SERIAL", null);
        else
            this.default_serial = _serial;

        if( _adb_server_host == null )
            this.adb_server_host = "localhost";
        else
            this.adb_server_host = _adb_server_host;

        if(_adb_server_port == null)
            this.adb_server_port = "5037";
        else
            this.adb_server_port = _adb_server_port;

        if( !"localhost".equals(adb_server_host) && !"127.0.0.1".equals(adb_server_host) ) {
            adb_host_port_options.add("-H");
            adb_host_port_options.add(adb_server_host);
        }

        if( !"5037".equals(adb_server_port) ){
            adb_host_port_options.add("-P");
            adb_host_port_options.add(adb_server_port);
        }

    }

    public String adb() throws EnvironmentError {
        String filename;
        String osName = System.getProperty("os.name");
        if( "nt".equals(osName) )
            filename = "adb.exe";
        else
            filename = "adb";

        File fAdbCmd = null;
        if(__adb_cmd == null){
            String androidHome = System.getenv("ANDROID_HOME");
            if(androidHome != null) {
                fAdbCmd = new File(new File(androidHome, "platform-tools"), filename);
                if( !fAdbCmd.exists() )
                    throw new EnvironmentError(String.format("Adb not found in $ANDROID_HOME path: %s", androidHome));
            }
            else
            {
                fAdbCmd = new File(new File(".", "bin"), filename);
                if( !fAdbCmd.exists() )
                    throw new EnvironmentError(String.format("$ANDROID_HOME environment not set and not find in bin directory"));
            }

            this.__adb_cmd = fAdbCmd.getAbsolutePath();
        }

        return __adb_cmd;
    }

    public Process raw_cmd(List<String> args) throws EnvironmentError, IOException {
        List<String> retCmd = new ArrayList<String>();
        retCmd.add(this.adb());
        if(adb_host_port_options != null && adb_host_port_options.size() != 0)
            retCmd.addAll(this.adb_host_port_options);
        if(args != null && args.size() != 0)
            retCmd.addAll(args);

        String[] _ = new String[retCmd.size()];
        int index = 0;
        for(String __ : retCmd){
            _[index++] = __;
        }

        return Runtime.getRuntime().exec(_);
    }

    public Map<String, String> devices() throws IOException, EnvironmentError {
        Map<String, String> ret = new HashMap<String, String>();

        Process p = this.raw_cmd(Collections.singletonList("devices"));
        BufferedReader input = new BufferedReader( new InputStreamReader(p.getInputStream()) );

        int flag = 0;

        String line;
        while ( (line = input.readLine()) != null ) {
            if( flag == 0 && "List of devices attached".equals(line) ){
                flag = 1;
                continue;
            }

            if( flag == 1 ){
                String newLine = line.trim();
                if(newLine == null || "".equals(newLine))continue;

                String[] words = newLine.split("\t");
                if(words.length < 2)continue;
                ret.put(words[0], words[1]);
            }
        }

        return ret;
    }

    public String device_serial() throws IOException, EnvironmentError {
        if(default_serial == null) {
            Map<String, String> devices = this.devices();
            Set<String> keys = devices.keySet();
            if(keys.size() == 1){
                for(String k : keys) {
                    default_serial = k;
                }
            }
            else if(keys.size() == 0){
                throw new EnvironmentError("Devices not attached");
            }
            else {
                throw new EnvironmentError("Multiple devices attached but default android serial not set.");
            }
        }
        return default_serial;
    }

    public Process cmd(List<String> args) throws IOException, EnvironmentError {
        String serial = this.device_serial();
        if(serial != null && !"".equals(serial)){
            List<String> _others = new ArrayList<String>();
            if(serial.contains(" ")) {
                serial = String.format("'%s'", serial);
            }
            _others.add("-s");
            _others.add(serial);
            _others.addAll(args);
            return this.raw_cmd(_others);

        }
        else{
            return this.raw_cmd(args);
        }
    }

    public List<String> execute(List<String> args) throws IOException, EnvironmentError {
        Process p = this.cmd(args);

        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
        List<String> ret = new ArrayList<String>();

        String line = null;
        while( (line=br.readLine()) != null ) {
            ret.add(line);
        }

        return ret;
    }

    public boolean forward(int local_port, int device_port) throws IOException, EnvironmentError, InterruptedException {
        List<String> _ = new ArrayList<String>();
        _.add("forward");
        _.add(String.format("tcp:%d", local_port));
        _.add(String.format("tcp:%d", device_port));
        return this.cmd(_).waitFor() == 0;
    }

    public static class Version {
        private int majorNumber = 0;
        private int minorNumber = 0;
        private int buildNumber = 0;

        private String version;

        public Version(String _version) {
            String[] __v = _version.split("\\.");
            if(__v.length >= 3){
                majorNumber = Integer.parseInt(__v[0]);
                minorNumber = Integer.parseInt(__v[1]);
                buildNumber = Integer.parseInt(__v[2]);
            }
        }

        public boolean setVersion(String _version) {
            String[] __v = _version.split("\\.");
            if(__v.length >= 3){
                majorNumber = Integer.parseInt(__v[0]);
                minorNumber = Integer.parseInt(__v[1]);
                buildNumber = Integer.parseInt(__v[2]);
                return true;
            }
            return false;
        }

        public Version(int majorNumber, int minorNumber, int revisionNumber) {
            this.majorNumber = majorNumber;
            this.minorNumber = minorNumber;
            this.buildNumber = revisionNumber;
        }

        public int getMajorNumber() {
            return majorNumber;
        }

        public void setMajorNumber(int majorNumber) {
            this.majorNumber = majorNumber;
        }

        public int getMinorNumber() {
            return minorNumber;
        }

        public void setMinorNumber(int minorNumber) {
            this.minorNumber = minorNumber;
        }

        public int getBuildNumber() {
            return buildNumber;
        }

        public void setBuildNumber(int buildNumber) {
            this.buildNumber = buildNumber;
        }
    };

    public Version version() throws EnvironmentError, IOException {
        Version ret = new Version(0, 0, 0);

        Process p = this.raw_cmd( Collections.singletonList("version") );
        InputStream is = p.getInputStream();
        InputStreamReader isr = new InputStreamReader( is );
        BufferedReader br = new BufferedReader(isr);

        String line = null;
        while( (line = br.readLine() ) != null ){
            Matcher m = pattern.matcher(line);
            if(m.find() && m.groupCount() >= 3){
                String _ = m.group(0);
                String majorVersionNumber = m.group(1);
                String minorVersionNumber = m.group(2);
                String buildVersionNumber = m.group(3);

                ret.setVersion(_);
                break;
            }
        }

        return ret;
    }

    public List<String> forward_list() throws EnvironmentError, IOException {
        List<String> ret = new ArrayList<String>();

        Version _v = this.version();
        if( _v.getMajorNumber() <= 1 && _v.getMinorNumber() <= 0 && _v.getBuildNumber() < 31 )
            throw new EnvironmentError("Low adb version");

        List<String> _cmd = new ArrayList<String>();
        _cmd.add("forward");
        _cmd.add("--list");

        Process p = this.raw_cmd(_cmd);
        BufferedReader _br = new BufferedReader(new InputStreamReader(p.getInputStream()));

        String line = null;
        while ( (line = _br.readLine()) != null ){
            ret.add(line);
        }

        return ret;
    }
}
