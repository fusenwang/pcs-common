package com.sure.communicate.protocal.tag;
import java.lang.annotation.ElementType;  
import java.lang.annotation.Retention;  
import java.lang.annotation.RetentionPolicy;  
import java.lang.annotation.Target;  
import com.sure.communicate.protocal.enumerate.MsgType;
/** 
 * 指令和执行类注解 
 * cmdType
 * @author franson 
 * 
 */  
  
@Target(ElementType.TYPE)  
@Retention(RetentionPolicy.RUNTIME)  
public @interface MsgAnnotation{  
	/**
	 * 指令类型(1个字节)
	 * @return
	 */
	MsgType type(); 
	/**
	 * 版本号(1个字节)，缺省为1
	 * @return
	 */
	String version() default "0x1";
}  