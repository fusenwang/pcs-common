package com.sure.communicate.protocal;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.sure.communicate.protocal.MsgAndExecBase;
import com.sure.communicate.protocal.enumerate.MsgType;
import com.sure.communicate.protocal.tag.MsgAnnotation;

/**
 * 任务基类
 * 
 * @author franson
 *
 */
@MsgAnnotation(type = MsgType.REQ_BR)
public class MsgBrRequest extends MsgAndExecBase {
	protected Gson gson;
	public MsgBrRequest() {
		gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
	}
	@Expose
	private String id;// 任务号(分发)
	@Expose
	private String status;// 任务状态:start、doing、end(分发)
	@Expose
	private String time;// 任务时间(分发)
	@Expose
	private String srcIp;// (分发)
	@Expose
	private String srcPort;// (分发)
	private String type;// 任务类型:日常广播(daily)，应急广播(emergency)，定时广播(timing)
	private short volumn;// 播放音量
	@Expose
	private String logicalAddr;// 播发请求源(分发)
	private String[] targetLogicalAddr;// 播放目标终端逻辑地址(也称区域地址)

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getSrcIp() {
		return srcIp;
	}

	public void setSrcIp(String srcIp) {
		this.srcIp = srcIp;
	}

	public String getSrcPort() {
		return srcPort;
	}

	public void setSrcPort(String srcPort) {
		this.srcPort = srcPort;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLogicalAddr() {
		return logicalAddr;
	}

	public void setLogicalAddr(String logicalAddr) {
		this.logicalAddr = logicalAddr;
	}

	public String[] getTargetLogicalAddr() {
		return targetLogicalAddr;
	}

	public void setTargetLogicalAddr(String[] targetLogicalAddr) {
		this.targetLogicalAddr = targetLogicalAddr;
	}

	public short getVolumn() {
		return volumn;
	}

	public void setVolumn(short volumn) {
		this.volumn = volumn;
	}
	@Override
	public String toString() {
		return "MsgBrRequest [id=" + id + ", status=" + status + ", time=" + time + ", srcIp=" + srcIp + ", srcPort="
				+ srcPort + ", type=" + type + ", volumn=" + volumn + ", logicalAddr=" + logicalAddr
				+ ", targetLogicalAddr=" + Arrays.toString(targetLogicalAddr) + "]";
	}
	@Override
	public byte[] encodeToBytes() {
		String jsonStr = gson.toJson(this);
		data = jsonStr.getBytes(Charset.forName("UTF-8"));
		int bufferLength = 2 + 2 + (data == null ? 0 : data.length);//objType(两个字节的short值)+数据长度+数据
		ByteBuffer buffer = ByteBuffer.allocate(bufferLength);
		buffer.putShort(Short.valueOf(code));// 添加消息类型值，注意消息类型值为占两个字节的short型
		buffer.putShort(getDataLength());
		if (data != null)
			buffer.put(data);
		return buffer.array();
	}
	@Override
	public void decodeToObject(byte[] buffer) {
		if(buffer!=null&&buffer.length>=4){
			ByteBuffer byteBuffer=ByteBuffer.wrap(buffer);
			if(byteBuffer.getShort()==Short.valueOf(code)){//validate the objType
				short dataLength=byteBuffer.getShort();
				if(dataLength>0){
					byte[] tmp=new byte[dataLength];
					byteBuffer.get(tmp, 0,dataLength);
					String strCnt=new String(tmp, Charset.forName("UTF-8"));
					gson.fromJson(strCnt, this.getClass());
				}
			}
		}
	}
}

// response{
// id,ip,port,
// }