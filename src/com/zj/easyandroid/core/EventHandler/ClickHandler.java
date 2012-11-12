package com.zj.easyandroid.core.EventHandler;

import java.lang.reflect.Method;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * ����¼�������
 * @author �ܽ�
 *
 */
public class ClickHandler implements IEventHandler {

	/**
	 * TAG
	 */
	private static final String TAG = "ClickHandler";

	/**
	 * ����ʱactivity����
	 */
	private Activity activity;

	public ClickHandler(Activity a) {
		this.activity = a;
	}

	@Override
	public void registerEvent(View v, final Method method) {
		v.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					method.setAccessible(true);
					if (method.getParameterTypes().length == 0) {
						method.invoke(activity, new Object[] {});
					} else {
						method.invoke(activity, v);
					}
					method.setAccessible(false);
				} catch (Exception e) {
					Log.e(TAG, "long click exception", e);
				}
			}
		});
	}

}
