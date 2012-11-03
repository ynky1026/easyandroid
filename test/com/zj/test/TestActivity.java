package com.zj.test;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.zj.easyandroid.R;
import com.zj.easyandroid.activities.EasyAndroidActivity;
import com.zj.easyandroid.annotation.Handler;
import com.zj.easyandroid.annotation.Listener;
import com.zj.easyandroid.annotation.Receiver;
import com.zj.easyandroid.core.Event;

public class TestActivity extends EasyAndroidActivity {

    @SuppressWarnings("unused")
    private static final String TAG = "TestActivity";

    private TextView tv;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        tv = ( TextView ) findViewById(R.id.tv);
    }



    @SuppressWarnings("unused")
    @Listener(id = R.id.btn, event = Event.CLICK)
    private void btnClick(View v) {
        Button btn = ( Button ) v;
        Toast.makeText(
            this,
            "button has been click" + " button's text is "
                + btn.getText().toString(), 2000).show();
        Intent it = new Intent("test");
        sendBroadcast(it);
    }



    @SuppressWarnings("unused")
    @Listener(id = R.id.btn1, event = Event.LONGCLICK)
    private void btnClick1(View v) {
        Button btn = ( Button ) v;
        Toast.makeText(
            this,
            "button has been click" + " button's text is "
                + btn.getText().toString(), 2000).show();
        Intent it = new Intent("test1");
        sendBroadcast(it);
    }



    @SuppressWarnings("unused")
    @Handler(what = 1)
    private void changeUi() {
        tv.setText("测试内容");
    }



    @SuppressWarnings("unused")
    @Receiver(action = "test")
    private void receive(Context c, Intent it) {
        Toast.makeText(this, "收到广播", 3000).show();
        sendMsgToBaseHd(1);
    }



    @SuppressWarnings("unused")
    @Receiver(action = "test1")
    private void receive1(Context c, Intent it) {
        Toast.makeText(this, "收到广播1", 3000).show();
    }
}
