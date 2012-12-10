package com.zj.easyandroid.message;

/**
 * 消息处理的抽象类 使用时 客户端程序员需继承此类并实现handleMessage方法 在次方法中 进行客户端自己的后台逻辑
 * simple introduction
 *
 * <p>detailed comment
 * @author zhoujian 2012-12-10
 * @see
 * @since 1.0
 */
public abstract class IMessageManager {
    /**
     * 发送消息 跟android的机制一样 谁发送的消息谁处理
     * 
     * @param msg
     */
    public final void sendMessage(Message msg) {
        MessageHandler mh = MessageHandler.getInstance();
        mh.putMessage(msg, this);
    }



    /**
     * 处理消息
     * 
     * @param msg
     */
    public abstract void handleMessage(Message msg);
}
