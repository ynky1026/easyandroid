package com.zj.easyandroid.message;

public class Message {

    /**
     * ����ϢҪִ�еĶ���
     */
    private String Action;
    
    /**
     * ���� ��ʾ����Ϣ�첽ִ��
     */
    public static final String ACTION_ASYNC = "async";
    
    /**
     * ���� ��ʾ����Ϣͬ��ִ��
     */
    public static final String ACTION_SYNC = "sync";
    
    /**
     * ����Ϣͬ��ִ��ʱ����
     */
    private Object lock;
    
    /**
     * �Ƿ������߳�
     */
    private boolean isThread = true;
    
    /**
     * ��Ϣ�а���������
     */
    private Object data;

    public String getAction() {
        return Action;
    }

    public void setAction(String action) {
        Action = action;
    }

    public Object getLock() {
        return lock;
    }

    public void setLock(Object lock) {
        this.lock = lock;
    }

    public boolean isThread() {
        return isThread;
    }

    public void setThread(boolean isThread) {
        this.isThread = isThread;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
    
    
}
