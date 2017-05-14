package com.sure.communicate.protocal;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.google.common.primitives.Bytes;
import com.sure.communicate.utils.ByteConventerUtil;
import com.sure.communicate.utils.ClassUtil;
import com.sure.communicate.protocal.tag.CmdOrMsgAnnotation;
import com.sure.communicate.protocal.enumerate.MsgSourceType;
/**
 * 指令数据包(下发/接收终端设备的数据包)
 * 
 * @author franson
 *
 */
@CmdOrMsgAnnotation(type = MsgSourceType.HD_EQUIP)
public class PacketCmd extends MsgAndExecBase {
//	private byte cmdHead = ByteConventerUtil.hexStr2Byte(getMsgAndExecType());
//	protected final String MSG_HEAD="0xA8";
	private short brCmdLen;
	private byte[] cmdPool;
	private byte[] signkey = new byte[] { 0, 0, 0, 0 };

	private MsgAndExecBase[] msgs;

	public MsgAndExecBase[] getMsgs() {
		return msgs;
	}

	public void setMsgs(MsgAndExecBase[] msgs) {
		this.msgs = msgs;
	}

	@Override
	public byte[] encodeToBytes() {
		if (msgs != null && msgs.length > 0) {
			// 指令池中的指令数据
			List<Byte> tmpCmdPool = new ArrayList<Byte>();
			for (MsgAndExecBase msg : msgs) {
				byte[] tmp = msg.encodeToBytes();
				for (int i = 0; i < tmp.length; i++) {
					tmpCmdPool.add(tmp[i]);
				}
			}
			cmdPool = Bytes.toArray(tmpCmdPool);
			brCmdLen = (short) cmdPool.length;
			int bufferLength = 1+2 + brCmdLen + 4;//srcType+指令包头长度+指令池有效数据长度+签名长度
			ByteBuffer buffer = ByteBuffer.allocate(bufferLength);
			buffer.put(ByteConventerUtil.hexStr2Byte(code));
			buffer.putShort(brCmdLen);
			buffer.put(cmdPool);
			buffer.put(signkey);
			return buffer.array();
		}
		return null;
	}
	public static PacketCmd decodeToObj(byte[] buffer) {
		PacketCmd ret=null;
		if(buffer!=null&&buffer.length>0){
			ret=new PacketCmd();
			ret.decodeToObject(buffer);
		}
		return ret;
	}
	 /**
	    *srcType+dataLength+cmdPool
	    */
	@Override
	public void decodeToObject(byte[] buffer) {
		ByteBuffer tmpBuffer = ByteBuffer.wrap(buffer);
		if (ByteConventerUtil.hexStr2Byte(code) == tmpBuffer.get()) {// validate the srcType
			brCmdLen = tmpBuffer.getShort();
			if (brCmdLen > 0) {
				cmdPool = new byte[brCmdLen];
				tmpBuffer.get(cmdPool, 0, brCmdLen);
				byte[] tmpSignKey = new byte[4];
				tmpBuffer.get(tmpSignKey);
				if (Arrays.equals(signkey,tmpSignKey)) {// validate the signkey
					tmpBuffer.clear();
					tmpBuffer=ByteBuffer.wrap(cmdPool);
					List<MsgAndExecBase> revcMsgs=new ArrayList<MsgAndExecBase>();
					while(tmpBuffer.hasRemaining()){
						Class cmdClass= ClassUtil.getMsgClassByType(tmpBuffer.get());
						if(cmdClass!=null){
							short cmdLength=tmpBuffer.getShort();
							byte[] cmdData=null;
								cmdData=new byte[cmdLength];
								tmpBuffer.get(cmdData, 0,cmdLength);
							try {
								MsgAndExecBase cmd = (MsgAndExecBase) cmdClass.newInstance();
								cmd.dataLength=cmdLength;
								cmd.decodeToObject(cmdData);
								revcMsgs.add(cmd);
							} catch (InstantiationException | IllegalAccessException e) {
								e.printStackTrace();
							}
						}
					}
					msgs=revcMsgs.toArray(new MsgAndExecBase[0]);
				}
			}
		}
	}
	@Override
	public String toString() {
		String cnt= "收到"+msgs.length+"个指令信息.\r\n";
		for (MsgAndExecBase msg : msgs) {
			cnt+=msg.toString()+"\r\n";
		}
		return cnt;
	}
	public static void main(String[] args){
		//构造一个请求广播的指令集
		PacketCmd packet = new PacketCmd();
		List<MsgAndExecBase> msgs = new ArrayList<MsgAndExecBase>();
		CmdBrStart cmdBrStart = new CmdBrStart("应急广播");
		CmdBrSource source = new CmdBrSource("村", "000011112255");
		CmdAddrIP srCmdAddrIP = new CmdAddrIP("192.168.1.2");
		CmdAddrPort srCmdAddrPort = new CmdAddrPort("8082");
		CmdAddrRange range = new CmdAddrRange(new String[] { "000011112201","000011112202" });//指定两个终端播放
		CmdSetVolumn cmdSetVolumn = new CmdSetVolumn((short) 63);
		msgs.add(cmdBrStart);
		msgs.add(source);
		msgs.add(srCmdAddrIP);
		msgs.add(srCmdAddrPort);
		msgs.add(range);	
		msgs.add(cmdSetVolumn);	
//		CmdAddrLogical cmdAddrLogical = new CmdAddrLogical("124121110012","000011112101");
		//		msgs.add(cmdBrStart);
//		msgs.add(cmdAddrLogical);
//		msgs.add(srCmdAddrIP);
//		msgs.add(srCmdAddrPort);
//		msgs.add(range);
//		msgs.add(source);
		packet.setMsgs(msgs.toArray(new MsgAndExecBase[0]));
		byte[] buffer= packet.encodeToBytes();
		System.out.println(ByteConventerUtil.bytes2HexStr(buffer,false));
//		CmdTurnOff cmdTurnOff = new CmdTurnOff();
//		CmdSetVolumn cmdSetVolumn = new CmdSetVolumn((short) 63);
//		CmdAddrIP ip = new CmdAddrIP("192.168.1.2");
//		CmdAddrPort port = new CmdAddrPort("8082");
//		CmdReqStatEquip cmdReqStatEquip = new CmdReqStatEquip(new CmdType[]{CmdType.SET_VOLUMN,CmdType.ADDR_LOGICAL});
//		msgs.add(cmdTurnOn);
//		msgs.add(cmdTurnOff);
//		msgs.add(cmdSetVolumn);
//		msgs.add(range);
//		msgs.add(ip);
//		msgs.add(port);
//		msgs.add(source);
//		msgs.add(cmdAddrLogical);
//		msgs.add(cmdReqStatEquip);
//		packet.setMsgs(msgs.toArray(new MsgAndExecBase[0]));
	}
}
