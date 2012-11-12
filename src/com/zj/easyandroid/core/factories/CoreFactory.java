package com.zj.easyandroid.core.factories;

/**
 * 核心的工厂接口
 * @author 周健
 *
 */
public class CoreFactory {

	/**
	 * 生产事件处理工厂
	 * @return
	 */
	public static IEventFactory getEventFactory() {
		return new EventFactory();
	}
}
