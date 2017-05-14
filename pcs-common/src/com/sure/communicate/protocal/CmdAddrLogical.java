package com.sure.communicate.protocal;

import com.sure.communicate.protocal.tag.CmdAnnotation;
import com.sure.communicate.protocal.enumerate.CmdType;
import com.sure.communicate.utils.ByteConventerUtil;
import io.netty.util.internal.StringUtil;

/**
 * 数据结构之逻辑地址指令
 * @author franson
 *
 */
@CmdAnnotation(type = CmdType.ADDR_LOGICAL, version = "0x01")
public class CmdAddrLogical extends MsgAndExecBase {
	public CmdAddrLogical(String mac,String logicalAddr){
	this.mac=mac;
	this.logicalAddr=logicalAddr;
//	setBrMethod(brMethod);
	}
	private String mac;
	private String logicalAddr;
	/**
	 * 获取MAC地址
	 * @return
	 */
	public String getMac() {
		if(StringUtil.isNullOrEmpty(mac)&&data!=null&&data.length==12){
			mac="";
			for(int i=0;i<6;i++){
				mac+=ByteConventerUtil.byte2HexStr(data[i],false);
			}
		}
		return mac;
	}
	/**
	 * 获取逻辑地址(区域编码)
	 * @return
	 */
	public String getLogicalAddr(){
		if(StringUtil.isNullOrEmpty(logicalAddr)&&data!=null&&data.length==12){
			logicalAddr="";
			for(int i=6;i<12;i++){
				logicalAddr+=ByteConventerUtil.byte2HexStr(data[i],false);
			}
		}
		return logicalAddr;
	}
	public void setMac(String mac) {
		this.mac=mac;
		if(data==null||data.length==0){
			data=new byte[12];
		}
		//data数组中前6位表示MAC地址
		if(!StringUtil.isNullOrEmpty(mac)&&mac.length()==12){
		data[0]=ByteConventerUtil.hexStr2Byte(mac.substring(0,2));
		data[1]=ByteConventerUtil.hexStr2Byte(mac.substring(2,4));
		data[2]=ByteConventerUtil.hexStr2Byte(mac.substring(4,6));
		data[3]=ByteConventerUtil.hexStr2Byte(mac.substring(6,8));
		data[4]=ByteConventerUtil.hexStr2Byte(mac.substring(8,10));
		data[5]=ByteConventerUtil.hexStr2Byte(mac.substring(10));
		}
	}
	public void setLogicalAddr(String logicalAddr){
		this.logicalAddr=logicalAddr;
		if(data==null||data.length==0){
			data=new byte[12];
		}
		//data数组中后6位表示逻辑地址
		if(!StringUtil.isNullOrEmpty(logicalAddr)&&logicalAddr.length()==12){
		data[6]=ByteConventerUtil.hexStr2Byte(logicalAddr.substring(0,2));
		data[7]=ByteConventerUtil.hexStr2Byte(logicalAddr.substring(2,4));
		data[8]=ByteConventerUtil.hexStr2Byte(logicalAddr.substring(4,6));
		data[9]=ByteConventerUtil.hexStr2Byte(logicalAddr.substring(6,8));
		data[10]=ByteConventerUtil.hexStr2Byte(logicalAddr.substring(8,10));
		data[11]=ByteConventerUtil.hexStr2Byte(logicalAddr.substring(10));
		}
	}
	public CmdAddrLogical(){
	}

	@Override
	public byte[] encodeToBytes() {
		setMac(mac);
		setLogicalAddr(logicalAddr);
		return super.encodeToBytes();
	}

	@Override
	public String toString() {
		return "逻辑地址指令:MAC地址-" + mac + " 逻辑地址-" + logicalAddr;
	}

	@Override
	public void decodeToObject(byte[] buffer) {
		super.decodeToObject(buffer);
		if (data != null && data.length > 0) {
			mac = getMac();
			logicalAddr = getLogicalAddr();
		}
	}
}
