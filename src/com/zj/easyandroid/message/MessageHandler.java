package com.zj.easyandroid.message;

import android.util.Log;

import com.zj.easyandroid.core.ThreadPool;

/**
 * 消息处理类 simple introduction
 * 
 * <p>
 * detailed comment
 * 
 * @author zhoujian 2012-12-10
 * @see
 * @since 1.0
 */
public class MessageHandler {

    private static final String TAG = "MessageHandler";

    private static MessageHandler instance;

    private ThreadPool pool;



    private MessageHandler() {
        pool = ThreadPool.getInstance();
    }



    public static MessageHandler getInstance() {
        if (instance == null) {
            instance = new MessageHandler();
        }
        return instance;
    }



    /**
     * 将消息放入队列
     * 
     * @param msg
     * @param manager
     */
    public void putMessage(Message msg, IMessageManager manager) {
        try {
            pool.putMessage(manager, msg);
        } catch (Exception e) {
            Log.e(TAG, "put Message exception", e);
        }
    }



    /**
     * 释放资源
     */
    public void release() {
        if (pool != null) {
            pool.release();
            pool = null;
        }
    }
}
