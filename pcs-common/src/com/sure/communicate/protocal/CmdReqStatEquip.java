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
@CmdAnnotation(type = CmdType.REQ_STAT_EQUIP)
public class CmdReqStatEquip extends MsgAndExecBase{
	private CmdType[] cmdCodes;
	
	public CmdReqStatEquip(){
	}
	public CmdReqStatEquip(CmdType[] cmdCodes){
		this.cmdCodes=cmdCodes;
		setCmdCodes(cmdCodes);
	}
	public CmdType[] getCmdCodes() {
		if((cmdCodes==null||cmdCodes.length==0)&&data!=null&&data.length>0){
			cmdCodes=new CmdType[data.length];
			for(int i=0;i<cmdCodes.length;i++){
				cmdCodes[i]=CmdType.getCmdTypeByValue(ByteConventerUtil.byte2HexStr(data[i], true));
			}
		}
		return cmdCodes;
	}
	public void setCmdCodes(CmdType[] cmdCodes) {
		this.cmdCodes = cmdCodes;
		if(cmdCodes!=null&&cmdCodes.length>0){
			data=new byte[cmdCodes.length];
			for(int i=0;i<data.length;i++){
				data[i]=ByteConventerUtil.hexStr2Byte(cmdCodes[i].getValue());
			}
		}
	}
	@Override
	public byte[] encodeToBytes() {
		setCmdCodes(cmdCodes);
		return super.encodeToBytes();
	}
	@Override
	public void decodeToObject(byte[] buffer) {
		super.decodeToObject(buffer);
		cmdCodes=getCmdCodes();
	}
	@Override
	public String toString() {
		String tmp="";
		for(CmdType type:cmdCodes){
			tmp+=type.getValue();
		}
		return "获取设备状态指:指令编码-"+tmp+".";
	}
}
