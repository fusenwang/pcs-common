package com.sure.communicate.protocal;

import com.sure.communicate.protocal.tag.CmdAnnotation;
import com.sure.communicate.protocal.enumerate.CmdType;
import com.sure.communicate.utils.ByteConventerUtil;
import io.netty.util.internal.StringUtil;

/**
 * 数据结构之开机指令
 * data从枚举类BrMethod中获取(一个字节)
 * @author franson
 *
 */
@CmdAnnotation(type = CmdType.BR_START, version = "0x01")
public class CmdBrStart extends MsgAndExecBase {
	public CmdBrStart(String brMethod){
		this.brMethod=brMethod;
		setBrMethod(brMethod);
	}
	private String brMethod;
	public String getBrMethod() {
		if(StringUtil.isNullOrEmpty(brMethod)&&data!=null&data.length>0){
			String hex=ByteConventerUtil.byte2HexStr(data[0],true);
			switch (hex) {
			case "0x80":
				brMethod="应急广播";
				break;
			case "0x81":
				brMethod="应急演练";
				break;
			case "0x82":
				brMethod="日常广播";
				break;
			case "0x83":
				brMethod="定时广播";
				break;
			case "0x85":
				brMethod="电话直播";
				break;
			case "0x86":
				brMethod="短信直播";
				break;
			case "0x87":
				brMethod="录音重播";
				break;
			case "0x88":
				brMethod="短信重播";
				break;
			default:
				break;
			}
		}
		return brMethod;
	}
	public void setBrMethod(String brMethod) {
		this.brMethod = brMethod;
		String methodValue = "0x00";
		switch (brMethod) {
		case "应急广播":
			methodValue="0x80";
			break;
		case "应急演练":
			methodValue="0x81";
			break;
		case "日常广播":
			methodValue="0x82";
			break;
		case "定时广播":
			methodValue="0x83";
			break;
		case "电话直播":
			methodValue="0x85";
			break;
		case "短信直播":
			methodValue="0x86";
			break;
		case "录音重播":
			methodValue="0x87";
			break;
		case "短信重播":
			methodValue="0x88";
			break;
		default:
			break;
		}
		data=new byte[]{ByteConventerUtil.hexStr2Byte(methodValue)};
	}
	public CmdBrStart(){
	}

	@Override
	public void decodeToObject(byte[] buffer) {
		super.decodeToObject(buffer);
		brMethod=getBrMethod();
//		ByteBuffer byteBuffer=ByteBuffer.wrap(buffer);
//		if(code.equals(ByteConventerUtil.byte2HexStr(byteBuffer.get(),true))){
//			short cmdLength= byteBuffer.getShort();
//			if(cmdLength==byteBuffer.remaining()){
//				dataLength=cmdLength;
//				data=new byte[cmdLength];
//				byteBuffer.get(data,0,cmdLength);
//			}
//		}
	}
	@Override
	public String toString() {
		return "开机指令:播发方式-"+brMethod;
	}
	public static void main(String[] arg){
		CmdBrStart on=new CmdBrStart("定时广播");
		byte[] buffer=on.encodeToBytes();
		System.out.println(buffer);
	}
}
