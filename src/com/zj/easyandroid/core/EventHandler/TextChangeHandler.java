package com.zj.easyandroid.core.EventHandler;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.zj.easyandroid.annotation.TextChangeListener;
import com.zj.easyandroid.core.Enum.TextChange;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

/**
 * TextChange事件处理类
 * 
 * @author 周健
 * 
 */
public class TextChangeHandler implements IEventHandler {

	private static final String TAG = "TextChangeHandler";

	private static TextChangeHandler instance;

	private Activity receiver;

	/**
	 * 已经添加的方法
	 */
	protected List<Method> methods = new ArrayList<Method>();

	private TextChangeHandler(Activity a) {
		receiver = a;
	}

	public static TextChangeHandler getInstance(Activity a) {
		if (instance == null) {
			instance = new TextChangeHandler(a);
		}
		return instance;
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
						if (changeMethod.getParameterTypes().length == 0) {
							changeMethod.setAccessible(true);
							changeMethod.invoke(receiver, new Object[] {});
							changeMethod.setAccessible(false);
						} else {
							changeMethod.invoke(receiver, s, start, before,
									count);
						}
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

						if (beforeMethod.getParameterTypes().length == 0) {
							beforeMethod.setAccessible(true);
							beforeMethod.invoke(receiver, new Object[] {});
							beforeMethod.setAccessible(false);
						} else {
							beforeMethod.invoke(receiver, s, start, count,
									after);
						}
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
						if (afterMethod.getParameterTypes().length == 0) {
							afterMethod.setAccessible(true);
							afterMethod.invoke(receiver, new Object[] {});
							afterMethod.setAccessible(false);
						} else {
							afterMethod.invoke(receiver, s);
						}
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
