package com.zj.easyandroid.core;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.util.Log;

import com.zj.easyandroid.message.IMessageManager;
import com.zj.easyandroid.message.Message;

/**
 * 线程池处理类 simple introduction
 * 
 * <p>
 * detailed comment
 * 
 * @author zhoujian 2012-12-10
 * @see
 * @since 1.0
 */
public class ThreadPool {

    /**
     * TAG
     */
    private static final String TAG = "ThreadPool";

    /**
     * 线程处理类
     */
    private static ThreadPool instance;

    /**
     * 线程池
     */
    private ExecutorService es;



    private ThreadPool() {
        if (es == null) {
            // 创建线程池
            es = Executors.newCachedThreadPool();
        }
    }



    public static ThreadPool getInstance() {
        if (instance == null) {
            instance = new ThreadPool();
        }
        return instance;
    }



    /**
     * 将消息和他的处理着放入线程队列
     * 
     * @param manager
     * @param msg
     */
    public void putMessage(IMessageManager manager, Message msg) {
        if (msg.getAction().equals(Message.ACTION_ASYNC)) {// 异步处理消息
            syncThread thread = new syncThread(manager, msg);
            es.execute(thread);
        } else {//TODO 同步待处理
            
        }

    }



    /**
     * 释放资源
     */
    public void release() {
        if (es != null) {
            es.shutdown();
            es = null;
        }
    }

    /**
     * 异步处理消息的线程 simple introduction
     * 
     * <p>
     * detailed comment
     * 
     * @author zhoujian 2012-12-10
     * @see
     * @since 1.0
     */
    class syncThread extends Thread {

        private IMessageManager manager;

        private Message msg;



        public syncThread(IMessageManager manager, Message msg) {
            this.manager = manager;
        }



        @Override
        public void run() {
            try {
                manager.handleMessage(msg);
            } catch (Exception e) {
                Log.e(TAG, "message handle exception", e);
            }

        }
    }
}
