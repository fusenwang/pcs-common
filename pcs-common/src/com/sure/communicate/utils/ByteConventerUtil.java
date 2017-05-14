package com.sure.communicate.utils;
public class ByteConventerUtil { 
	  /** 
	   * 将一个单字节的byte转换成32位的int 
	   * 
	   * @param b 
	   *      byte 
	   * @return convert result 
	   */ 
	  public static int unsignedByteToInt(byte b) { 
	    return (int) b & 0xFF; 
	  } 
	 
	  /** 
	   * 将一个4byte的数组转换成32位的int 
	   * 
	   * @param buf 
	   *      bytes buffer 
	   * @param byte[]中开始转换的位置 
	   * @return convert result 
	   */ 
	  public static long unsigned4BytesToInt(byte[] buf, int pos) { 
	    int firstByte = 0; 
	    int secondByte = 0; 
	    int thirdByte = 0; 
	    int fourthByte = 0; 
	    int index = pos; 
	    firstByte = (0x000000FF & ((int) buf[index])); 
	    secondByte = (0x000000FF & ((int) buf[index + 1])); 
	    thirdByte = (0x000000FF & ((int) buf[index + 2])); 
	    fourthByte = (0x000000FF & ((int) buf[index + 3])); 
	    index = index + 4; 
	    return ((long) (firstByte << 24 | secondByte << 16 | thirdByte << 8 | fourthByte)) & 0xFFFFFFFFL; 
	  } 
	 
	  /** 
	   * 将16位的short转换成byte数组 
	   * 
	   * @param s 
	   *      short 
	   * @return byte[] 长度为2 
	   * */ 
	  public static byte[] shortToByteArray(short s) { 
	    byte[] targets = new byte[2]; 
	    for (int i = 0; i < 2; i++) { 
	      int offset = (targets.length - 1 - i) * 8; 
	      targets[i] = (byte) ((s >>> offset) & 0xff); 
	    } 
	    return targets; 
	  } 
	 
	  /** 
	   * 将32位整数转换成长度为4的byte数组 
	   * 
	   * @param s 
	   *      int 
	   * @return byte[] 
	   * */ 
	  public static byte[] intToByteArray(int s) { 
	    byte[] targets = new byte[2]; 
	    for (int i = 0; i < 4; i++) { 
	      int offset = (targets.length - 1 - i) * 8; 
	      targets[i] = (byte) ((s >>> offset) & 0xff); 
	    } 
	    return targets; 
	  } 
	 
	  /** 
	   * long to byte[] 
	   * 
	   * @param s 
	   *      long 
	   * @return byte[] 
	   * */ 
	  public static byte[] longToByteArray(long s) { 
	    byte[] targets = new byte[2]; 
	    for (int i = 0; i < 8; i++) { 
	      int offset = (targets.length - 1 - i) * 8; 
	      targets[i] = (byte) ((s >>> offset) & 0xff); 
	    } 
	    return targets; 
	  } 
	 
	/**
	 * 32位int转换为长度为4的字节数组byte[]
	 * @param res 
	 * @return
	 */
	  public static byte[] int2byte(int res) { 
	    byte[] targets = new byte[4]; 
	    targets[0] = (byte) (res & 0xff);// 最低位 
	    targets[1] = (byte) ((res >> 8) & 0xff);// 次低位 
	    targets[2] = (byte) ((res >> 16) & 0xff);// 次高位 
	    targets[3] = (byte) (res >>> 24);// 最高位,无符号右移。 
	    return targets; 
	  } 
	 
	  /** 
	   * 将长度为2的byte数组转换为16位int(暂时注释掉了原始的代码，后面重新验证移位算法) 
	   * 
	   * @param res 
	   *      byte[] 
	   * @return int 
	   * */ 
	  public static int byte2int(byte[] res) { 
//	    // res = InversionByte(res); 
//	    // 一个byte数据左移24位变成0x??000000，再右移8位变成0x00??0000 
//	    int targets = (res[0] & 0xff) | ((res[1] << 8) & 0xff00); // | 表示安位或 
//	    return targets; 
			int ret=0;
			if(res!=null&&res.length>0){
				ret=res[0]*256+unsignedByteToInt(res[1]);
			}
			return ret;
	  } 
	  /**
	   * 将16进制的字符串转换为1个字节
	   * @param hex
	   * @return
	   */
	  public static byte hexStr2Byte(String hex){
		  String tmp=hex;
		  if(tmp.startsWith("0x")||tmp.startsWith("0X")){
			  tmp=tmp.substring(2);
		  }
		 return Integer.valueOf(tmp, 16).byteValue(); 
	  }
	  /** 
	   * 将一个单字节的Byte转换成十六进制的字符串(不包含前缀0x) 
	   * 
	   * @param b 
	   *      byte 
	   * @return convert result 
	   */ 
	  public static String byte2HexStr(byte b,boolean isContains0x) { 
	    int i = b & 0xFF; 
	    String tmpValue=Integer.toHexString(i).toUpperCase();
	    if(tmpValue.length()==1){
	    	tmpValue="0"+tmpValue;
	    }
	    return isContains0x?"0x"+tmpValue:tmpValue; 
	  } 
	  /**
	   * 将字节数组转换为十六进制形式的字符串
	   * @param buffer
	   * @param isContains0x
	   * @return
	   */
	  public static String bytes2HexStr(byte[] buffer,boolean isContains0x) { 
		  String ret="";  
		  for(byte b:buffer){
		    	ret+=byte2HexStr(b,isContains0x)+" ";
		    }
		    return ret.trim(); 
		  } 
	  public static void main(String[] arg){
		  short value=04;
//		  String aString="0xf1";
		  byte b=hexStr2Byte(String.valueOf((int)value));
		  String bString=byte2HexStr(b,true);
		  System.out.println(b);
		  System.out.println(bString);
	  }
	} 