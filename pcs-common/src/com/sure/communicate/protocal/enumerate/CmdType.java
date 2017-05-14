package com.sure.communicate.protocal.enumerate;
public enum CmdType{
	/**
	 * 未知指令类型
	 */
	UNKNOWN("0x00")
	/**
	 *指令-开机
	 */
	,BR_START("0x08")
	/**
	 *指令-关机
	 */
	,BR_STOP("0x09")
	/**
	 * 指令-寻址范围
	 */
	,ADDR_RANGE("0x10")
	/**
	 * 指令-广播源级
	 */
    ,BR_SOURCE("0x26")
	/**
	 *指令-音量调整
	 */
	,SET_VOLUMN("0x03")
	/**
	 * 指令-ip地址
	 */
	,ADDR_IP("0x80")
	/**
	 * 指令-端口
	 */
	,ADDR_PORT("0x81")
	/**
	 * 指令-逻辑地址
	 */
	,ADDR_LOGICAL("0x04")
	/**
	 * 指令-获取设备状态
	 */
	,REQ_STAT_EQUIP("0xB0");
	private String value;

	public String getValue() {
		return value;
	}

	private CmdType(String value) {
		this.value = value;
	}
	public static CmdType getCmdTypeByValue(String value) {  
		 for(CmdType item : CmdType.values()){
		      if(item.getValue().equals(value)){
		        return item;
		      }
		    }
		    return UNKNOWN;
    }  
}
