package com.zj.easyandroid.core;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.util.Log;

import com.zj.easyandroid.message.IMessageManager;
import com.zj.easyandroid.message.Message;

/**
 * �̳߳ش����� simple introduction
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
	 * �̴߳�����
	 */
	private static ThreadPool instance;

	/**
	 * �̳߳�
	 */
	private ExecutorService es;

	private ThreadPool() {
		if (es == null) {
			// �����̳߳�
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
	 * ����Ϣ�����Ĵ����ŷ����̶߳���
	 * 
	 * @param manager
	 * @param msg
	 */
	public void putMessage(IMessageManager manager, Message msg) {
		if (msg.getAction().equals(Message.ACTION_ASYNC)) {// �첽������Ϣ
			AsyncThread thread = new AsyncThread(manager, msg);
			es.execute(thread);
		} else {// TODO ͬ��������

		}

	}

	/**
	 * �ͷ���Դ
	 */
	public void release() {
		if (es != null) {
			es.shutdown();
			es = null;
		}
	}

	/**
	 * �첽������Ϣ���߳� simple introduction
	 * 
	 * <p>
	 * detailed comment
	 * 
	 * @author zhoujian 2012-12-10
	 * @see
	 * @since 1.0
	 */
	class AsyncThread extends Thread {

		private IMessageManager manager;

		private Message msg;

		public AsyncThread(IMessageManager manager, Message msg) {
			this.manager = manager;
			this.msg = msg;
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
