package com.zj.easyandroid.message;

/**
 * 消息类 此类用于将提交后台处理的数据 与动作等信息封装到一起 note:这里有些属性可能暂时不会用到 保留方便以后扩展 simple
 * introduction
 * 
 * <p>
 * detailed comment
 * 
 * @author zhoujian 2012-12-10
 * @see
 * @since 1.0
 */
public class Message {

    /**
     * 该消息要执行的动作
     */
    private String Action = Message.ACTION_ASYNC;

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
