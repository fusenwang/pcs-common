package com.sure.communicate.protocal.enumerate;
public enum MsgSourceType{
	/**
	 * 未知来源
	 */
	UNKNOWN("0x00")
	/**
	 *终端设备
	 */
	,HD_EQUIP("0xA8")
	/**
	 *分发系统
	 */
	,ST_DISPATCH("0xA9")
	;
	private String value;

	public String getValue() {
		return value;
	}

	private MsgSourceType(String value) {
		this.value = value;
	}
	public static MsgSourceType getMsgSourceTypeByValue(String hexStr) {  
		 for(MsgSourceType item : MsgSourceType.values()){
		      if(item.getValue().equals(hexStr)){
		        return item;
		      }
		    }
		    return UNKNOWN;
   }  
}
