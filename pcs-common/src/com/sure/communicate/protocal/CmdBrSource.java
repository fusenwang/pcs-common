package com.sure.communicate.protocal;
import com.sure.communicate.utils.ByteConventerUtil;
import io.netty.util.internal.StringUtil;
import com.sure.communicate.protocal.tag.CmdAnnotation;
import com.sure.communicate.protocal.enumerate.CmdType;
/**
 * 数据结构之广播源级指令
 * 
 * @author franson
 *
 */
@CmdAnnotation(type = CmdType.BR_SOURCE)
public class CmdBrSource extends MsgAndExecBase {
	public CmdBrSource() {

	}

	public CmdBrSource(String level, String localCode) {
		this.level = level;
		this.localCode = localCode;
	}

	/**
	 * 级别
	 */
	private String level;
	/**
	 * 本级编码(十六进制字符串，每两个字符组成一个十六进制，暂不清楚此值)
	 */
	private String localCode;

	public String getLocalCode() {
		if (StringUtil.isNullOrEmpty(localCode) && data != null && data.length > 1) {
			// 本级编码取除data[0]后的值，注意此值可能是十六进制的字符串
			localCode = "";
			for (int i = 1; i < data.length; i++) {
				localCode += ByteConventerUtil.byte2HexStr(data[i], false);
			}
		}
		return localCode;
	}

	public void setLocalCode(String localCode) {
		this.localCode = localCode;
	}

	public String getLevel() {
		if (StringUtil.isNullOrEmpty(level) && data != null && data.length > 0) {
			String levelByte = ByteConventerUtil.byte2HexStr(data[0], true);
			switch (levelByte) {
			case "0x01":
				level = "省";
				break;
			case "0x02":
				level = "市";
				break;
			case "0x03":
				level = "县";
				break;
			case "0x04":
				level = "乡";
				break;
			case "0x05":
				level = "村";
				break;
			default:
				break;
			}
		}
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	@Override
	public byte[] encodeToBytes() {
		data = new byte[1 + localCode.length() / 2];
		if (!StringUtil.isNullOrEmpty(level)) {
			String hexStr = "";
			switch (level) {
			case "省":
				hexStr = "0x01";
				break;
			case "市":
				hexStr = "0x02";
				break;
			case "县":
				hexStr = "0x03";
				break;
			case "乡":
				hexStr = "0x04";
				break;
			case "村":
				hexStr = "0x05";
				break;
			default:
				break;
			}
			data[0] = ByteConventerUtil.hexStr2Byte(hexStr);
		}
		if (!StringUtil.isNullOrEmpty(localCode)) {
			for (int i = 1; i < data.length; i++) {
				if (2 * i == localCode.length()) {
					data[i] = ByteConventerUtil.hexStr2Byte(localCode.substring(2 * (i - 1)));
				} else {
					data[i] = ByteConventerUtil.hexStr2Byte(localCode.substring(2 * (i - 1), 2 * i));
				}
			}
		}
		return super.encodeToBytes();
	}

	@Override
	public String toString() {
		return "广播源级指令:发送级别-" + level + " 本机编码-" + localCode;
	}

	@Override
	public void decodeToObject(byte[] buffer) {
		super.decodeToObject(buffer);
		if (data != null && data.length > 0) {
			level = getLevel();
			localCode = getLocalCode();
		}
	}
}
