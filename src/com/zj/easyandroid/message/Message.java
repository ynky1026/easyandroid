package com.zj.easyandroid.message;

public class Message {

    /**
     * 该消息要执行的动作
     */
    private String Action;
    
    /**
     * 动作 表示此消息异步执行
     */
    public static final String ACTION_ASYNC = "async";
    
    /**
     * 动作 表示此消息同步执行
     */
    public static final String ACTION_SYNC = "sync";
    
    /**
     * 当消息同步执行时的锁
     */
    private Object lock;
    
    /**
     * 是否启动线程
     */
    private boolean isThread = true;
    
    /**
     * 消息中包含的数据
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
