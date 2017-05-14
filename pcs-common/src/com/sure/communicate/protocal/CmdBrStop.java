package com.sure.communicate.protocal;

import com.sure.communicate.utils.ByteConventerUtil;
import com.sure.communicate.protocal.tag.CmdAnnotation;
import com.sure.communicate.protocal.enumerate.CmdType;
/**
 * 数据结构之关机指令
 * 该指令值已固化，初始化后即可使用
 * @author franson
 *
 */
@CmdAnnotation(type = CmdType.BR_STOP)
public class CmdBrStop extends MsgAndExecBase{
	public CmdBrStop(){
		data=new byte[]{ByteConventerUtil.hexStr2Byte("0xff")};
	}
	@Override
	public String toString() {
		return "关机指令.";
	}
}
