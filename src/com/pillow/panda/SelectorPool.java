package com.pillow.panda;

import com.pillow.panda.RpcUtils.Selector;

/**
 * Created by qianyuxiang on 15/11/8.
 */
public class SelectorPool {
    public static String pn = "com.tencent.mm"; // tencent package name

    static public Selector plusBtnSelector() {
        Selector s = new Selector();
        s.setClassName("android.widget.ImageView");
        s.setInstance(0);
        s.setPackageName(pn);

        return s;
    }

    static public Selector addBtnLineSelector() {
        Selector s = new Selector();
        s.setClassName("android.widget.TextView");
        s.setText("添加朋友");
        s.setPackageName(pn);
        s.setInstance(0);

        return s;
    }

    static public Selector searchBtnSelector() {
        Selector s = new Selector();
        s.setClassName("android.widget.TextView");
        s.setText("微信号/QQ号/手机号");
        s.setPackageName("com.tencent.mm");

        return s;
    }

    static public Selector topSearchEditSelector() {
        Selector s = new Selector();

        s.setClassName("android.widget.EditText");
        s.setPackageName("com.tencent.mm");

        return s;
    }

    static public Selector clearBtnSelector() {
        Selector s = new Selector();
        s.setClassName("android.widget.TextView");
        s.setClassName("android.widget.ImageButton");
        s.setDescription("清除");

        return s;
    }

    static public Selector searchLineSelector(String searchContent) {
        Selector s = new Selector();

        s.setClassName("android.widget.TextView");
        s.setText(String.format("搜索:%s", searchContent));
        s.setPackageName(pn);

        return s;
    }

    static public Selector noUserFoundSelector() {
        Selector s = new Selector();

        s.setIndex(0);
        s.setText("用户不存在");
        s.setClassName("android.widget.TextView");

        return s;
    }

    static public Selector okBtnSelector() {
        Selector s = new Selector();

        s.setIndex(0);
        s.setText("确定");
        s.setClassName("android.widget.Button");

        return s;
    }

    static public Selector addBtnSelector() {
        Selector s = new Selector();

        s.setClassName("android.widget.Button");
        s.setText("添加到通讯录");
        s.setPackageName(pn);

        return s;
    }

    static public Selector sendBtnSelector() {
        Selector s = new Selector();

        s.setClassName("android.widget.TextView");
        s.setText("发送");
        s.setPackageName(pn);

        return s;
    }

    static public Selector retBtnSelector() {
        Selector s = new Selector();

        s.setClassName("android.widget.ImageView");
        s.setPackageName(pn);
        s.setIndex(0);
        s.setDescription("返回");

        return s;
    }

    static public Selector videoChatSelector() {
        Selector s = new Selector();

        s.setIndex(1);
        s.setText("视频聊天");
        s.setClassName("android.widget.Button");
        s.setPackageName(pn);

        return s;
    }

}
