package com.zj.easyandroid.core.factories;

/**
 * ���ĵĹ����ӿ�
 * @author �ܽ�
 *
 */
public class CoreFactory {

	/**
	 * �����¼�������
	 * @return
	 */
	public static IEventFactory getEventFactory() {
		return new EventFactory();
	}
}
