package com.zj.easyandroid.core.factories;

import android.app.Activity;

import com.zj.easyandroid.core.Enum.Event;
import com.zj.easyandroid.core.EventHandler.IEventHandler;

/**
 * �¼��������ӿ�
 * @author �ܽ�
 *
 */
public interface IEventFactory {
	public IEventHandler getEventHandler(Event event,
			Activity activity);
}
