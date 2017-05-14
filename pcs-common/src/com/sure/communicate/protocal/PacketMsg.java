package com.sure.communicate.protocal;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sure.communicate.protocal.MsgAndExecBase;
import com.sure.communicate.protocal.enumerate.MsgSourceType;
import com.sure.communicate.protocal.tag.CmdOrMsgAnnotation;
import com.sure.communicate.utils.ByteConventerUtil;
import com.sure.communicate.utils.ClassUtil;

/**
 * 任务基类
 * 
 * @author franson
 *
 */
@CmdOrMsgAnnotation(type = MsgSourceType.ST_DISPATCH)
public class PacketMsg extends MsgAndExecBase {
	protected static Gson gson=new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
	private Object msgObj;
	private byte[] signkey = new byte[] { 0, 0, 0, 0 };
	public PacketMsg() {
	}
	public void setMsgObj(Object msgObj) {
		this.msgObj = msgObj;
	}
	public Object getMsgObj() {
		return msgObj;
	}
	public static PacketMsg decodeToObj(byte[] buffer) {
		PacketMsg ret=null;
		if (buffer != null && buffer.length >= 2) {
			ret=new PacketMsg();
			ret.decodeToObject(buffer);
		}
		return ret;
	}
   /**
    *srcType+msgType+dataLength+data
    */
	@Override
	public void decodeToObject(byte[] buffer) {
		if (buffer != null && buffer.length >= 2) {
			ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
			if(ByteConventerUtil.hexStr2Byte(code)==byteBuffer.get()){//validate the srcType
				short msgType = byteBuffer.getShort();
				Class cls = ClassUtil.getMsgClassByType(String.valueOf(msgType));
				if (cls != null) {
					short dataLength=byteBuffer.getShort();
					byte[] tmp = new byte[dataLength];
					byteBuffer.get(tmp,0,dataLength);
					String cnt = new String(tmp, Charset.forName("UTF-8"));
					msgObj = gson.fromJson(cnt, cls);
				}
			}
		}
	}
   
	@Override
	public byte[] encodeToBytes() {
		if (msgObj != null&&msgObj instanceof MsgAndExecBase) {
			String msgType= ((MsgAndExecBase)msgObj).code;//short且值从1000后取值
			String jsonStr = gson.toJson(msgObj);
			byte[] dataBuffer = jsonStr.getBytes(Charset.forName("UTF-8"));
			int length=1+2+2+dataBuffer.length+signkey.length;//srcType+mstType(short型值)+有效数据长度(占2个字节)+有效数据+签名
			ByteBuffer ret=ByteBuffer.allocate(length);
			ret.put(ByteConventerUtil.hexStr2Byte(code));
			ret.putShort(Short.valueOf(msgType));
			ret.putShort((short)dataBuffer.length);
			ret.put(dataBuffer);
			ret.put(signkey);
			return ret.array();
		}
		return null;
	}
}

// response{
// id,ip,port,
// }4