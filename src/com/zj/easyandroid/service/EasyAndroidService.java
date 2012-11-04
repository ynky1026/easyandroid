package com.zj.easyandroid.service;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

import com.zj.easyandroid.annotation.Receiver;

public class EasyAndroidService extends Service {

	/**
	 * TAG
	 */
	public static final String TAG = "EasyAndroidService";
	
	/**
     * 运行时类型
     */
    private Class< ? > cls;
	
	/**
     * 用于存储广播action和对应method的map
     */
    private Map<String, Method> actionMethod = new HashMap<String, Method>();

    /**
     * 通用广播接收器
     */
    private List<BaseBroadcastReceiver> receivers = new ArrayList<BaseBroadcastReceiver>();

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		cls = this.getClass();
		try {
			Method[] methods = this.getClass().getDeclaredMethods();
			for (final Method method : methods) {
				boolean isReceiver = method.isAnnotationPresent(Receiver.class);
				if (isReceiver) {
					registerRecevier(method);
				}
			}
		} catch (Throwable e) {
			Log.e(TAG, "service start error", e);
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
            Object o = EasyAndroidService.this;
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
