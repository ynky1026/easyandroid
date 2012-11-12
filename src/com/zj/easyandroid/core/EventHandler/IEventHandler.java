package com.zj.easyandroid.core.EventHandler;

import java.lang.reflect.Method;

import android.view.View;

/**
 * 事件处理类
 * @author 周健
 *
 */
public interface IEventHandler {

	public void registerEvent(View v,Method method);
}
