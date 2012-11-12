package com.zj.easyandroid.core.EventHandler;

import java.lang.reflect.Method;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.View.OnLongClickListener;

/**
 * 长按事件处理类
 * @author 周健
 *
 */
public class LongClickHandler implements IEventHandler {

	/**
	 * TAG
	 */
	private static final String TAG = "LongClickHandler";

	/**
	 * 运行时activity
	 */
	private Activity activity;

	public LongClickHandler(Activity a) {
		this.activity = a;
	}

	@Override
	public void registerEvent(View v, final Method method) {
		v.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				try {
					method.setAccessible(true);
					if (method.getParameterTypes().length == 0) {
						method.invoke(activity, new Object[] {});
					} else {
						method.invoke(activity, v);
					}
					method.setAccessible(false);
				} catch (Exception e) {
					Log.e(TAG, "click exception", e);
				}
				return false;
			}
		});

	}

}
