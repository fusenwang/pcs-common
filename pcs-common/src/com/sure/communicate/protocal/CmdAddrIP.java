package com.sure.communicate.protocal;

import com.sure.communicate.utils.ByteConventerUtil;
import io.netty.util.internal.StringUtil;
import com.sure.communicate.protocal.enumerate.CmdType;
import com.sure.communicate.protocal.tag.CmdAnnotation;
/**
 * 数据结构之开机指令
 * data从枚举类BrMethod中获取(一个字节)
 * @author franson
 *
 */
@CmdAnnotation(type = CmdType.ADDR_IP, version = "0x01")
public class CmdAddrIP extends MsgAndExecBase {
	public CmdAddrIP(String ip){
		this.ip=ip;
		setIp(ip);
	}
	private String ip;
	public String getIp() {
		if(StringUtil.isNullOrEmpty(ip)&&data!=null&data.length==4){
			ip=ByteConventerUtil.unsignedByteToInt(data[0])+".";
			ip+=ByteConventerUtil.unsignedByteToInt(data[1])+".";
			ip+=ByteConventerUtil.unsignedByteToInt(data[2])+".";
			ip+=ByteConventerUtil.unsignedByteToInt(data[3]);
		}
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
		data=new byte[4];
		String tmpIp=ip;
		String tmp=tmpIp.substring(0,tmpIp.indexOf('.'));
		data[0]=ByteConventerUtil.hexStr2Byte(Integer.toHexString(Integer.valueOf(tmp)));
		tmp+=".";
		tmpIp=tmpIp.substring(tmp.length());
		tmp=tmpIp.substring(0,tmpIp.indexOf('.'));
		data[1]=ByteConventerUtil.hexStr2Byte(Integer.toHexString(Integer.valueOf(tmp)));
		tmp+=".";
		tmpIp=tmpIp.substring(tmp.length());
		tmp=tmpIp.substring(0,tmpIp.indexOf('.'));
		data[2]=ByteConventerUtil.hexStr2Byte(Integer.toHexString(Integer.valueOf(tmp)));
		tmp+=".";
		tmpIp=tmpIp.substring(tmp.length());
		data[3]=ByteConventerUtil.hexStr2Byte(Integer.toHexString(Integer.valueOf(tmpIp)));
	}
	public CmdAddrIP(){
	}

	@Override
	public void decodeToObject(byte[] buffer) {
		super.decodeToObject(buffer);
		ip=getIp();
	}
	@Override
	public String toString() {
		return "ip指令:ip地址-"+ip;
	}
	public static void main(String[] arg){
	}
}
