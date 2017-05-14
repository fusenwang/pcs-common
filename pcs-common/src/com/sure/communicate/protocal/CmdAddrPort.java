package com.sure.communicate.protocal;

import com.sure.communicate.utils.ByteConventerUtil;
import io.netty.util.internal.StringUtil;
import com.sure.communicate.protocal.tag.CmdAnnotation;
import com.sure.communicate.protocal.enumerate.CmdType;
/**
 * 数据结构之开机指令
 * data从枚举类BrMethod中获取(一个字节)
 * @author franson
 *
 */
@CmdAnnotation(type = CmdType.ADDR_PORT, version = "0x01")
public class CmdAddrPort extends MsgAndExecBase {
	public CmdAddrPort(String port){
		this.port=port;
		setPort(port);
	}
	private String port;
	public String getPort() {
		if(StringUtil.isNullOrEmpty(port)&&data!=null&data.length==2){
			port=String.valueOf(ByteConventerUtil.byte2int(data));
		}
		return port;
	}
	public void setPort(String port) {
		this.port = port;
		data=ByteConventerUtil.shortToByteArray(Short.valueOf(port));
	}
	public CmdAddrPort(){
	}

	@Override
	public void decodeToObject(byte[] buffer) {
		super.decodeToObject(buffer);
		port=getPort();
	}
	@Override
	public String toString() {
		return "端口指令:端口-"+port;
	}
	public static void main(String[] arg){
//		CmdAddrPort port=new CmdAddrPort("8082");
//		byte[] buffer= port.encodeToBytes();
//		CmdAddrPort a=new CmdAddrPort();
//		a.decodeToObject(buffer);
//		System.out.println(a.toString());
		String port="8090";
		int a=Integer.valueOf(port);
		byte[] buffer=ByteConventerUtil.shortToByteArray((short)a);
		int b= ByteConventerUtil.byte2int(buffer);
		System.out.println(b);
//		byte b1= (byte) (a/256);
//		byte b2=(byte) (a%256);
//		int c2=a%256;
//		int k2= ByteConventerUtil.unsignedByteToInt(b2);
//		int d=b1*256+k2;//左移8位
////		int b2= (8082-b1*256);
//		int a1=b1*256;
//		String l1= ByteConventerUtil.byte2HexStr(b1, false);
//		String l2= ByteConventerUtil.byte2HexStr(b2,false);
//		int a1= Integer.valueOf(l1, 16);
//		int a2=Integer.valueOf(l2, 16);
	
//		int c= ByteConventerUtil.byte2int(new byte[]{b1,b2});
//		System.out.println(a1);
//		byte[] bytes=ByteConventerUtil.shortToByteArray(Integer.valueOf(port));
//		short a=Short.valueOf(port);
//		int b=Integer.valueOf(port);
//		System.out.println(a+" "+b);
	}
}
