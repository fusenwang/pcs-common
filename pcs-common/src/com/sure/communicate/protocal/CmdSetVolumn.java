package com.sure.communicate.protocal;

import com.sure.communicate.utils.ByteConventerUtil;
import com.sure.communicate.protocal.tag.CmdAnnotation;
import com.sure.communicate.protocal.enumerate.CmdType;
/**
 * 数据结构之音量调整指令
 * data为1个byte的取值范围为0到64的字节数组
 * @author franson
 *
 */
@CmdAnnotation(type = CmdType.SET_VOLUMN)
public class CmdSetVolumn extends MsgAndExecBase{
	public CmdSetVolumn(){
	}
	public CmdSetVolumn(short volumn){
		setVolumn(volumn);
	}
	/**
	 * 音量值
	 */
	private Short volumn;
    public short getVolumn() {
    	if(volumn==null&&data!=null&&data.length>0){
    		if(data.length>1)
    		  volumn=(short) ByteConventerUtil.byte2int(data);
    		else if(data.length==1)
    		{
    			String hex= ByteConventerUtil.byte2HexStr(data[0],false);
    			volumn=Short.valueOf(hex, 16);
    		}
    	}
		return volumn;
	}
	public void setVolumn(short volumn) {
		if(volumn<0||volumn>64)
			throw new IllegalArgumentException("违法的参数值volumn,该参数仅接受0到64的值");
		this.volumn = volumn;
		data=new byte[]{ByteConventerUtil.hexStr2Byte(Integer.toHexString((int)this.volumn))};
	}

	@Override
	public void decodeToObject(byte[] buffer) {
		super.decodeToObject(buffer);
		volumn=getVolumn();
	}
@Override
public String toString() {
	return "音量设置指令:音量值-"+volumn;
}
}
