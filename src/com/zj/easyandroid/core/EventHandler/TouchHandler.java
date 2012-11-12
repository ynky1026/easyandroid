package com.zj.easyandroid.core.EventHandler;

import java.lang.reflect.Method;

import android.app.Activity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

/**
 * touch事件处理类
 * @author 周健
 *
 */
public class TouchHandler implements IEventHandler {

	/**
	 * TAG
	 */
	private static final String TAG = "ClickHandler";

	/**
	 * 运行时activity对象
	 */
	private Activity activity;

	public TouchHandler(Activity a) {
		this.activity = a;
	}

	@Override
	public void registerEvent(View v, final Method method) {
		v.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				try {
					method.setAccessible(true);
					if (method.getParameterTypes().length == 0) {
						method.invoke(activity, new Object[] {});
					} else {
						method.invoke(activity, v, event);
					}
					method.setAccessible(false);
				} catch (Exception e) {
					Log.e(TAG, "touch exception", e);
				}
				return false;
			}
		});
	}

}
