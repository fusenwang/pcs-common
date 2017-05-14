package com.sure.communicate.common;
import io.netty.channel.Channel;
public abstract class MsgHandlerBase implements Runnable {
	protected Channel channel;
	protected byte[] data;
	protected String handlerName;
	@Override
	public void run() {
		receiveMsgHandle();
	}
	public MsgHandlerBase(String handlerName) {
		this.handlerName = handlerName;
	}

	public String getThreadName() {
		return Thread.currentThread().getName();
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}
	/**
	 * 接收request调用业务逻辑处理并response,由具体的handler实现具体的逻辑
	 */
	public abstract void receiveMsgHandle();
	/**
	 * 发送消息或指令
	 * @param msg
	 */
    public abstract void sendMsg(Object msg);
	/**
	 * 输出消息到控制台
	 * 
	 * @param msg
	 */
	public void consoleMsg(String msg) {
		System.out.println(handlerName + "(" + Thread.currentThread().getName() + "):" + msg);
	}

	/**
	 * 输出错误消息到控制台
	 * 
	 * @param msg
	 */
	public void consoleErrMsg(String msg) {
		System.err.println(handlerName + "(" + Thread.currentThread().getName() + "):" + msg);
	}
}
