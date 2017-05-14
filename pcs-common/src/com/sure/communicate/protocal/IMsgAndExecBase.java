package com.sure.communicate.protocal;

public interface IMsgAndExecBase {
	/**
	 * 编码-将消息实体转换为字节数组
	 * @return
	 */
  byte[] encodeToBytes();
  /**
   * 解码-将字节数组转换为消息实体
   * @return
   */
  void decodeToObject(byte[] buffer);
  /**
   * 获取版本号
   * @return
   */
  String getVersion();
}
