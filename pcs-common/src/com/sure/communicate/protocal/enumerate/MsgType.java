package com.sure.communicate.protocal.enumerate;
/**
 * 消息类型(用于软件平台间的通信,value取值1000之后的值)
 * @author franson
 *
 */
public enum MsgType{
	/**
	 * 未知指令类型
	 */
	UNKNOWN((short)1000)
	/**
	 * 心跳包
	 */
	,HEARTBREAK((short)1001)
	/**
	 *请求-播发
	 */
	,REQ_BR((short)1002)
	;
	private short value;

	public short getValue() {
		return value;
	}

	private MsgType(short value) {
		this.value = value;
	}
	public static MsgType getCmdTypeByValue(short value) {  
		 for(MsgType item : MsgType.values()){
		      if(item.getValue()==value){
		        return item;
		      }
		    }
		    return UNKNOWN;
    }  
}