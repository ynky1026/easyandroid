package com.zj.easyandroid.message;

/**
 * ��Ϣ����ĳ����� ʹ��ʱ �ͻ��˳���Ա��̳д��ಢʵ��handleMessage���� �ڴη����� ���пͻ����Լ��ĺ�̨�߼�
 * simple introduction
 *
 * <p>detailed comment
 * @author zhoujian 2012-12-10
 * @see
 * @since 1.0
 */
public abstract class IMessageManager {
    /**
     * ������Ϣ ��android�Ļ���һ�� ˭���͵���Ϣ˭����
     * 
     * @param msg
     */
    public final void sendMessage(Message msg) {
        MessageHandler mh = MessageHandler.getInstance();
        mh.putMessage(msg, this);
    }



    /**
     * ������Ϣ �ͻ��˳���Ա�ڴ˷��������ҵ���߼� ���Լ�ѡ���ʵ���ʽ���ؽ��������
     * 
     * @param msg
     */
    public abstract void handleMessage(Message msg);
}
