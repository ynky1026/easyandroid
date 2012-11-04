package com.zj.easyandroid.activities;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ActivityGroup;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;

import com.zj.easyandroid.annotation.Listener;
import com.zj.easyandroid.annotation.Receiver;
import com.zj.easyandroid.core.Enum.Event;

public class EasyAndroidActivityGroup extends ActivityGroup{

    /**
     * TAG
     */
    private static final String TAG = "EasyAndroidActivity";

    /**
     * 通用广播接收器
     */
    private List<BaseBroadcastReceiver> receivers = new ArrayList<BaseBroadcastReceiver>();

    /**
     * 用于存储广播action和对应method的map
     */
    private Map<String, Method> actionMethod = new HashMap<String, Method>();

    /**
     * 用于存储消息what和对应method的map
     */
    private Map<Integer, Method> msgMethod = new HashMap<Integer, Method>();

    /**
     * 运行时类型
     */
    private Class< ? > cls;

    /**
     * 是否已经初始化
     */
    private boolean isInit;

    /**
     * hd
     */
    protected Handler baseHandler;



    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseHandler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                try {
                    int i = msg.what;
                    Method method = msgMethod.get(i);
                    Object o = EasyAndroidActivityGroup.this;
                    method.setAccessible(true);
                    method.invoke(cls.cast(o));
                    method.setAccessible(false);
                } catch (Exception e) {
                    Log.e(TAG, "handleMessage exception", e);
                }
            };
        };
    }



    @Override
    protected void onResume() {
        super.onResume();
        cls = this.getClass();
        Log.d(TAG, cls.getName());
        init();
    }



    /**
     * 初始化
     */
    private void init() {
        if (isInit) {
            return;
        }
        try {
            Method[] methods = this.getClass().getDeclaredMethods();
            for (final Method method : methods) {
                boolean isListenerAnnotation = method
                    .isAnnotationPresent(Listener.class);
                if (isListenerAnnotation) {
                    setListeners(method);
                }
                boolean isReceiver = method.isAnnotationPresent(Receiver.class);
                if (isReceiver) {
                    registerRecevier(method);
                }
                boolean isHandler = method
                    .isAnnotationPresent(com.zj.easyandroid.annotation.Handler.class);
                if (isHandler) {
                    initHandler(method);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "init exception", e);
        }

        isInit = true;
    }



    /**
     * 初始化handler
     * @param method
     */
    private void initHandler(Method method) {
        com.zj.easyandroid.annotation.Handler hd = method
            .getAnnotation(com.zj.easyandroid.annotation.Handler.class);
        int what = hd.what();
        msgMethod.put(what, method);
    }



    /**
     * 动态设置监听
     */
    private void setListeners(final Method method) {
        Listener listener = method.getAnnotation(Listener.class);
        int id = listener.id();
        Event event = listener.event();
        View v = ( View ) this.findViewById(id);
        final Object o = this;
        switch (event) {
            case CLICK:
                v.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        try {
                            method.setAccessible(true);
                            method.invoke(cls.cast(o), v);
                            method.setAccessible(false);
                        } catch (Exception e) {
                            Log.e(TAG, "long click exception", e);
                        }
                    }
                });
                break;
            case LONGCLICK:
                v.setOnLongClickListener(new OnLongClickListener() {

                    @Override
                    public boolean onLongClick(View v) {
                        try {
                            method.setAccessible(true);
                            method.invoke(cls.cast(o), v);
                            method.setAccessible(false);
                        } catch (Exception e) {
                            Log.e(TAG, "click exception", e);
                        }
                        return false;
                    }
                });
                break;
            case TOUCH:
                v.setOnTouchListener(new OnTouchListener() {

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        try {
                            method.setAccessible(true);
                            method.invoke(cls.cast(o), v, event);
                            method.setAccessible(false);
                        } catch (Exception e) {
                            Log.e(TAG, "touch exception", e);
                        }
                        return false;
                    }
                });
                break;
            case FOCUSCHANG:
                v.setOnFocusChangeListener(new OnFocusChangeListener() {

                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        try {
                            method.setAccessible(true);
                            method.invoke(cls.cast(o), v, hasFocus);
                            method.setAccessible(false);
                        } catch (Exception e) {
                            Log.e(TAG, "focus change exception", e);
                        }
                    }
                });
                break;
            default:
                break;
        }
    }



    /**
     * 动态注册监听
     */
    private void registerRecevier(final Method method) {
        Receiver annotation = method.getAnnotation(Receiver.class);
        String action = annotation.action();
        actionMethod.put(action, method);
        BaseBroadcastReceiver receiver = new BaseBroadcastReceiver();
        IntentFilter ift = new IntentFilter(action);
        receivers.add(receiver);
        registerReceiver(receiver, ift);
    }



    /**
     * 向通用handler发送消息
     * 
     * @param what
     */
    protected final void sendMsgToBaseHd(int what) {
        Message msg = new Message();
        msg.what = what;
        baseHandler.sendMessage(msg);
    }



    @Override
    public void finish() {
        super.finish();
        for (BaseBroadcastReceiver receiver : receivers) {
            unregisterReceiver(receiver);
            receiver = null;
        }
    }

    /**
     * simple introduction 通用广播接收器
     * <p>
     * detailed comment
     * 
     * @author zhoujian 2012-8-28
     * @see
     * @since 1.0
     */
    private class BaseBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Method method = actionMethod.get(action);
            Object o = EasyAndroidActivityGroup.this;
            try {
                method.setAccessible(true);
                method.invoke(cls.cast(o), context, intent);
                method.setAccessible(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
