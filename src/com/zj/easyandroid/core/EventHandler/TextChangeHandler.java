package com.zj.easyandroid.core.EventHandler;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.zj.easyandroid.annotation.TextChangeListener;
import com.zj.easyandroid.core.Enum.TextChange;

/**
 * TextChange事件处理类
 * 
 * @author 周健
 * 
 */
public class TextChangeHandler implements IEventHandler {

	private static final String TAG = "TextChangeHandler";

	private Activity receiver;

	/**
	 * 已经添加的方法
	 */
	protected List<Method> methods = new ArrayList<Method>();

	public TextChangeHandler(Activity a) {
		receiver = a;
	}

	@Override
	public void registerEvent(View v, Method method) {
		methods.add(method);

		if (v instanceof EditText) {
			((EditText) v).addTextChangedListener(new TextWatcher() {

				@Override
				public void onTextChanged(CharSequence s, int start,
						int before, int count) {
					try {
						Method changeMethod = null;
						for (Method m : methods) {
							if (!m.isAnnotationPresent(TextChangeListener.class)) {
								changeMethod = m;
								break;
							} else {
								TextChangeListener l = m
										.getAnnotation(TextChangeListener.class);
								if (l.callback() == TextChange.ON) {
									changeMethod = m;
									break;
								}
							}
						}
						if (changeMethod == null) {
							return;
						}
						changeMethod.setAccessible(true);
						if (changeMethod.getParameterTypes().length == 0) {
							changeMethod.invoke(receiver, new Object[] {});
						} else {
							changeMethod.invoke(receiver, s, start, before,
									count);
						}
						changeMethod.setAccessible(false);
					} catch (Exception e) {
						Log.e(TAG, "registerEvent error", e);
					}
				}

				@Override
				public void beforeTextChanged(CharSequence s, int start,
						int count, int after) {
					try {
						Method beforeMethod = null;
						for (Method m : methods) {
							if (m.isAnnotationPresent(TextChangeListener.class)) {
								TextChangeListener l = m
										.getAnnotation(TextChangeListener.class);
								if (l.callback() == TextChange.BEFORE) {
									beforeMethod = m;
									break;
								}
							}
						}
						if (beforeMethod == null) {
							return;
						}
						beforeMethod.setAccessible(true);
						if (beforeMethod.getParameterTypes().length == 0) {
							beforeMethod.invoke(receiver, new Object[] {});
						} else {
							beforeMethod.invoke(receiver, s, start, count,
									after);
						}
						beforeMethod.setAccessible(false);
					} catch (Exception e) {
						Log.e(TAG, "registerEvent error", e);
					}
				}

				@Override
				public void afterTextChanged(Editable s) {
					try {
						Method afterMethod = null;
						for (Method m : methods) {
							if (m.isAnnotationPresent(TextChangeListener.class)) {
								TextChangeListener l = m
										.getAnnotation(TextChangeListener.class);
								if (l.callback() == TextChange.AFTER) {
									afterMethod = m;
									break;
								}
							}
						}
						if (afterMethod == null) {
							return;
						}
						afterMethod.setAccessible(true);
						if (afterMethod.getParameterTypes().length == 0) {
							afterMethod.invoke(receiver, new Object[] {});
						} else {
							afterMethod.invoke(receiver, s);
						}
						afterMethod.setAccessible(false);
					} catch (Exception e) {
						Log.e(TAG, "registerEvent error", e);
					}
				}
			});
		} else {
			Log.e(TAG, "registerEvent error");
		}
	}
}
