package com.zj.easyandroid.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ��Ϣ������ simple introduction
 * 
 * <p>
 * detailed comment
 * 
 * @author zhoujian 2012-12-10
 * @see
 * @since 1.0
 */
public class MessageHandler {

    private static MessageHandler instance;

    /**
     * ��Ϣ������ļ���
     */
    private List<IMessageManager> list = new ArrayList<IMessageManager>();

    /**
     * ��Ϣ����������Ϣ�Ķ�Ӧ�б�
     */
    private Map<IMessageManager, Message> map = new HashMap<IMessageManager, Message>();



    private MessageHandler() {
        MyLooper looper = new MyLooper();
        looper.start();
    }



    public static MessageHandler getInstance() {
        if (instance == null) {
            instance = new MessageHandler();
        }
        return instance;
    }



    /**
     * ����Ϣ�������
     * @param msg
     * @param manager
     */
    public void putMessage(Message msg, IMessageManager manager) {
        list.add(manager);
        map.put(manager, msg);
    }

    class MyLooper extends Thread {
        @Override
        public void run() {

        }
    }
}
