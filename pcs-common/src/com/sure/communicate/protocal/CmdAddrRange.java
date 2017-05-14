package com.sure.communicate.protocal;

import com.sure.communicate.utils.ByteConventerUtil;
import com.sure.communicate.protocal.tag.CmdAnnotation;
import com.sure.communicate.protocal.enumerate.CmdType;
/**
 * 数据结构之开机指令 data从枚举类BrMethod中获取(一个字节)
 * 
 * @author franson
 *
 */
@CmdAnnotation(type = CmdType.ADDR_RANGE, version = "0x01")
public class CmdAddrRange extends MsgAndExecBase {
	public CmdAddrRange(String[] addrs) {
		this.addrs = addrs;
		setAddrs(addrs);
	}

	private String[] addrs;

	public String[] getAddrs() {
		if ((addrs == null || addrs.length == 0)) {
//			if(data==null&&Integer.toHexString(dataLength).toUpperCase().equals("FF"))
//			{
//				addrs=new String[]{Integer.toHexString(dataLength).toUpperCase()};
//			}
//			else{
				addrs=new String[data.length/6];
				int index=0;
				for(int i=0;i<addrs.length;i++){
					String tmpV="";
					for(int j=0;j<6&&index<data.length;j++){
						tmpV+=ByteConventerUtil.byte2HexStr(data[index++],false);
					}
					addrs[i]=tmpV;
				}
//			}
		}
		return addrs;
	}

	/**
	 * 当addrs值为{"ff"}时为全域地址，data数组为空，将dataLength置为0xFF
	 * 
	 * @param addrs
	 */
	public void setAddrs(String[] addrs) {
		this.addrs = addrs;
		data = null;
		if (addrs != null && addrs.length > 0) {
//			if (addrs.length == 1 && addrs[0].toUpperCase().equals("FF")) {
//				data = null;
//				dataLength = Short.valueOf(addrs[0], 16);
//			} else {
				data = new byte[6 * addrs.length];// 一组长度为6个字节
				int index = 0;
				for (String addr : addrs) {
					if (addr.length() == 12) {
						data[index++] = ByteConventerUtil.hexStr2Byte(addr.substring(0,2));
						data[index++] = ByteConventerUtil.hexStr2Byte(addr.substring(2,4));
						data[index++] = ByteConventerUtil.hexStr2Byte(addr.substring(4,6));
						data[index++] = ByteConventerUtil.hexStr2Byte(addr.substring(6,8));
						data[index++] = ByteConventerUtil.hexStr2Byte(addr.substring(8,10));
						data[index++] = ByteConventerUtil.hexStr2Byte(addr.substring(10));
					}
				}
				dataLength = (short) data.length;
//			}
		}
	}

	public CmdAddrRange() {
	}


	@Override
	public void decodeToObject(byte[] buffer) {
		super.decodeToObject(buffer);
		addrs = getAddrs();
	}

	@Override
	public String toString() {
		String str = "";
		for (String addr : addrs) {
			str += addr + " ";
		}
		return "寻址范围指令:编码组-" + str;
	}

	public static void main(String[] arg) {
//		CmdAddrRange on = new CmdAddrRange(new String[]{"ff"});
//		byte[] buffer = on.encodeToBytes();
//		on.decodeToObject(buffer);
////		System.out.println(buffer.toString());
//		System.out.println(on.toString());
		short s=255;
		System.out.println(Integer.toHexString(s).toUpperCase());
	}
}
