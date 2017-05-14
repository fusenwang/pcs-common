package com.sure.communicate.protocal;

import com.sure.communicate.protocal.tag.MsgAnnotation;
import com.sure.communicate.utils.ByteConventerUtil;
import com.sure.communicate.protocal.enumerate.MsgType;
/**
 * 数据结构之心跳包(系统之间使用)
 * @author franson
 *
 */
@MsgAnnotation(type = MsgType.HEARTBREAK)
public class MsgHeartBreak extends MsgAndExecBase{
	public MsgHeartBreak(){
	}
	@Override
	public String toString() {
		return "心跳包.";
	}
	public static void main(String[] arg){
		MsgHeartBreak msgHeartBreak=new MsgHeartBreak();//？？？消息头长度不对
		System.out.println(ByteConventerUtil.bytes2HexStr(msgHeartBreak.encodeToBytes(),false));
	}
}
