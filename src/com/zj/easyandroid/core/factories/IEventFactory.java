package com.zj.easyandroid.core.factories;

import android.app.Activity;

import com.zj.easyandroid.core.Enum.Event;
import com.zj.easyandroid.core.EventHandler.IEventHandler;

/**
 * 事件处理工厂接口
 * @author 周健
 *
 */
public interface IEventFactory {
	public IEventHandler getEventHandler(Event event,
			Activity activity);
}
