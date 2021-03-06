package com.zj.easyandroid.activities;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;

import com.zj.easyandroid.annotation.Listener;
import com.zj.easyandroid.annotation.Receiver;
import com.zj.easyandroid.core.Enum.Event;
import com.zj.easyandroid.core.EventHandler.IEventHandler;
import com.zj.easyandroid.core.factories.CoreFactory;

public class EasyAndroidActivity extends Activity {

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
	private SparseArray<Method> msgMethod = new SparseArray<Method>();

	/**
	 * 运行时类型
	 */
	private Class<?> cls;

	/**
	 * 是否已经初始化
	 */
	private boolean isInit;

	/**
	 * hd
	 */
	protected BaseHandler baseHandler;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		baseHandler = new BaseHandler(msgMethod, this);
	}

	protected <T extends View> T getViewById(int id) {
		@SuppressWarnings("unchecked")
		T t = (T) findViewById(id);
		return t;
	}

	@Override
	protected void onStart() {
		super.onStart();
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
	 * 
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
		View v = findViewById(id);
		Event event = listener.event();

		IEventHandler handler = CoreFactory.getEventFactory().getEventHandler(
				event, this);
		if (handler == null) {
			return;
		}
		handler.registerEvent(v, method);
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
			Object o = EasyAndroidActivity.this;
			try {
				method.setAccessible(true);
				method.invoke(cls.cast(o), context, intent);
				method.setAccessible(false);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 通用handler
	 * @author Administrator
	 *
	 */
	private static class BaseHandler extends Handler {

		private WeakReference<SparseArray<Method>> wrMsgMethod;

		private WeakReference<EasyAndroidActivity> wrMActivity;

		public BaseHandler(SparseArray<Method> msgMethod,
				EasyAndroidActivity mActivity) {
			wrMsgMethod = new WeakReference<SparseArray<Method>>(msgMethod);
			wrMActivity = new WeakReference<EasyAndroidActivity>(mActivity);
		}

		@Override
		public void handleMessage(Message msg) {

			try {
				int i = msg.what;
				SparseArray<Method> msgMethod = wrMsgMethod.get();
				EasyAndroidActivity mActivity = wrMActivity.get();
				Method method = msgMethod.get(i);
				Object o = mActivity;
				method.setAccessible(true);
				method.invoke(mActivity.getClass().cast(o));
				method.setAccessible(false);
			} catch (Exception e) {
				Log.e(TAG, "handle message exception", e);
			}

		}
	}
}