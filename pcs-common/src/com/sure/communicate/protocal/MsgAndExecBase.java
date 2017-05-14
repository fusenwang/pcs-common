package com.sure.communicate.protocal;

import java.nio.ByteBuffer;

import com.sure.communicate.protocal.tag.CmdAnnotation;
import com.sure.communicate.protocal.tag.CmdOrMsgAnnotation;
import com.sure.communicate.protocal.tag.MsgAnnotation;
import com.sure.communicate.utils.ByteConventerUtil;

import io.netty.util.internal.StringUtil;

public abstract class MsgAndExecBase implements IMsgAndExecBase {
	/**
	 * (指令或消息类)编码
	 */
	protected String code = getMsgAndExecType();
	/**
	 * 有效数据长度
	 */
	protected short dataLength;
	/**
	 * 有效数据
	 */
	protected byte[] data;

	public String getCode() {
		return code;
	}
	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public short getDataLength() {
		if (data != null)
			dataLength = (short) data.length;
		return dataLength;
	}

	/**
	 * 通过注解获取该类的指令或执行编码
	 * 
	 * @return
	 */
	protected String getMsgAndExecType() {
		if (this.getClass().isAnnotationPresent(CmdOrMsgAnnotation.class)) {
			CmdOrMsgAnnotation anno = this.getClass().getAnnotation(CmdOrMsgAnnotation.class);
			return anno.type().getValue();
		} else if (this.getClass().isAnnotationPresent(CmdAnnotation.class)) {
			CmdAnnotation anno = this.getClass().getAnnotation(CmdAnnotation.class);
			return anno.type().getValue();
		}else if (this.getClass().isAnnotationPresent(MsgAnnotation.class)) {
			MsgAnnotation anno = this.getClass().getAnnotation(MsgAnnotation.class);
			return String.valueOf(anno.type().getValue());
		}
		return null;
	}
	/**
	 * buffer为去掉指令编码和指令长度后的数据
	 */
	public void decodeToObject(byte[] buffer) {
		data = buffer;
	}

	/**
	 * 将对象编码为字节数组
	 */
	@Override
	public byte[] encodeToBytes() {
		int bufferLength = 1 + 2 + (data==null?0:data.length);// 指令编码+指令长度+指令数据
		ByteBuffer buffer = ByteBuffer.allocate(bufferLength);
		if (!StringUtil.isNullOrEmpty(getMsgAndExecType())) {
			buffer.put(ByteConventerUtil.hexStr2Byte(getMsgAndExecType()));
		} else {
			buffer.put(new byte[] { 0 });
		}
		buffer.putShort(getDataLength());
		if (data != null)
			buffer.put(data);
		return buffer.array();
	}

	/**
	 * 获取该类的指令的版本号
	 * 
	 * @return
	 */
	@Override
	public String getVersion() {
		if (this.getClass().isAnnotationPresent(CmdOrMsgAnnotation.class)) {
			CmdOrMsgAnnotation anno = this.getClass().getAnnotation(CmdOrMsgAnnotation.class);
			return anno.version();
		}
		return "0x0";
	}
}
