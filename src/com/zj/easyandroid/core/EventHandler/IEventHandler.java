package com.zj.easyandroid.core.EventHandler;

import java.lang.reflect.Method;

import android.view.View;

/**
 * �¼�������
 * @author �ܽ�
 *
 */
public interface IEventHandler {

	public void registerEvent(View v,Method method);
}
