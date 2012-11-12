package com.zj.easyandroid.core.EventHandler;

import java.lang.reflect.Method;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.View.OnFocusChangeListener;

/**
 * focus change事件处理类
 * @author 周健
 *
 */
public class FocusChangeHandler implements IEventHandler {

	/**
	 * TAG
	 */
	private static final String TAG = "ClickHandler";

	/**
	 * 运行时activity对象
	 */
	private Activity activity;

	public FocusChangeHandler(Activity a) {
		this.activity = a;
	}

	@Override
	public void registerEvent(View v, final Method method) {
		v.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				try {
					method.setAccessible(true);
					if (method.getParameterTypes().length == 0) {
						method.invoke(activity, new Object[] {});
					} else {
						method.invoke(activity, v, hasFocus);
					}
					method.setAccessible(false);
				} catch (Exception e) {
					Log.e(TAG, "focus change exception", e);
				}
			}
		});
	}

}
