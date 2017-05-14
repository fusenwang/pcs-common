package com.sure.communicate.utils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.sure.communicate.common.MsgHandlerBase;
import com.sure.communicate.protocal.tag.*;
import io.netty.util.internal.StringUtil;

public class ClassUtil {
	// msgType->请求、响应类的class对象  
	private static Map<String, Class> typeToMsgClassMap;  
	private static Map<String, Class> typeToHandlerClassMap;
	// 根据类型得到对应的消息类的class对象  
	public static Class getMsgClassByType(Byte type) {  
		if(type!=null){
			String hexStr=ByteConventerUtil.byte2HexStr(type.byteValue(),true);
			if(typeToMsgClassMap.containsKey(hexStr)){
				return typeToMsgClassMap.get(hexStr);
			}
		}
	    return null;  
	}  
	/**
	 * 根据类型得到对应的消息类的class对象  
	 * @param type
	 * @return
	 */
	public static Class getMsgClassByType(String type) {  
		if(!StringUtil.isNullOrEmpty(type)){
			if(typeToMsgClassMap.containsKey(type)){
				return typeToMsgClassMap.get(type);
			}
		}
	    return null;  
	}
	/**
	 * 根据类型得到对应的消息执行类的class对象，该类直接继承于MsgHandlerBase类  
	 * @param type
	 * @return
	 */
	public static Class getHandlerClassByType(String type) {  
		if(!StringUtil.isNullOrEmpty(type)){
			if(typeToHandlerClassMap.containsKey(type)){
				return typeToHandlerClassMap.get(type);
			}
		}
	    return null;  
	}
	/**
	 * 根据类型得到对应的消息执行类的class对象，该类直接继承于MsgHandlerBase类  
	 * @param type
	 * @return
	 */
	public static Class getHandlerClassByType(Byte type) {  
		if(type!=null){
			String hexStr=ByteConventerUtil.byte2HexStr(type.byteValue(),true);
			if(typeToHandlerClassMap.containsKey(hexStr)){
				return typeToHandlerClassMap.get(hexStr);
			}
		}
	    return null;  
	}
	/** 
	 * 初始化typeToMsgClassMap 
	 * 取得消息类的class文件 
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 */  
	public static void initTypeToMsgClassMap(Class interfaceName,String...pkgNames)   
	        throws ClassNotFoundException, IOException {  
	    Map<String, Class> tmpMap = new HashMap<String, Class>();  
	    Map<String, Class> handlerMap = new HashMap<String, Class>();  
	    Set<Class> classSet = getAllClassByInterface(interfaceName,pkgNames);  
	    if (classSet != null) {  
	        for (Class clazz : classSet) {  
	            if (clazz.isAnnotationPresent(CmdOrMsgAnnotation.class)) {  
	                CmdOrMsgAnnotation annotation = (CmdOrMsgAnnotation) clazz.getAnnotation(CmdOrMsgAnnotation.class);  
	                	 tmpMap.put(annotation.type().getValue(), clazz);  
	            }
	            if (clazz.isAnnotationPresent(CmdAnnotation.class)) {  
	            	CmdAnnotation annotation = (CmdAnnotation) clazz.getAnnotation(CmdAnnotation.class);  
	                tmpMap.put(annotation.type().getValue(), clazz);  
	            }
	            if (clazz.isAnnotationPresent(MsgAnnotation.class)) {  
	            	MsgAnnotation annotation = (MsgAnnotation) clazz.getAnnotation(MsgAnnotation.class);  
	                tmpMap.put(String.valueOf(annotation.type().getValue()), clazz);  
	            }  
	        }  
	    }  
	    typeToMsgClassMap = Collections.unmodifiableMap(tmpMap);  
	}
	/**
	 * 初始化typeToHandlerClassMap 
	 * @param interfaceName
	 * @param pkgNames
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public static void initTypeToHandlerClassMap(Class interfaceName,String...pkgNames)   
	        throws ClassNotFoundException, IOException {  
	    Map<String, Class> handlerMap = new HashMap<String, Class>();  
	    Set<Class> classSet = getAllClassByInterface(interfaceName,pkgNames);  
	    if (classSet != null) {  
	        for (Class clazz : classSet) {  
	            if (clazz.isAnnotationPresent(CmdOrMsgAnnotation.class)) {  
	                CmdOrMsgAnnotation annotation = (CmdOrMsgAnnotation) clazz.getAnnotation(CmdOrMsgAnnotation.class);  
	                if(MsgHandlerBase.class.isAssignableFrom(clazz)){
	                	handlerMap.put(annotation.type().getValue(), clazz);
	                }
	            }  
	        }  
	    }  
	    typeToHandlerClassMap=Collections.unmodifiableMap(handlerMap);
	}
	/**
	 *  取得指定包下所有实现了interfaceName接口的类  
	 * @param interfaceName 被实现的接口
	 * @param pkgNames 指定名包，如果为空，则获取interfaceName包下的所有实现了此接口的类
	 * @return
	 */
    public static Set<Class> getAllClassByInterface(Class interfaceName,String... pkgNames) {  
    	Set<Class>  returnClassList = null;  
            if(interfaceName.isInterface()) {
            	if(pkgNames==null||pkgNames.length==0){
            		 // 获取当前的包名  
                    String packageName = interfaceName.getPackage().getName();  
                    // 获取当前包下以及子包下所以的类  
                    Set<Class> allClass = getClassesByPkgName(packageName);  
                    if(allClass != null) {  
                        returnClassList = new HashSet<Class>();  
                        for(Class classes : allClass) {  
                            // 判断是否是同一个接口  
                            if(interfaceName.isAssignableFrom(classes)) {  
                                // 本身不加入进去  
                                if(!interfaceName.equals(classes)) {  
                                    returnClassList.add(classes);          
                                }  
                            }  
                        }  
                    } 
            	}else{
            		returnClassList = new HashSet<Class>();
            		for(String pkgName:pkgNames){
            			// 获取当前包下以及子包下所以的类  
                        Set<Class> allClass = getClassesByPkgName(pkgName);  
                        if(allClass != null) {  
                            for(Class classes : allClass) {  
                                // 判断是否是同一个接口  
                                if(interfaceName.isAssignableFrom(classes)) {  
                                    // 本身不加入进去  
                                    if(!interfaceName.equals(classes)) {  
                                        returnClassList.add(classes);          
                                    }  
                                }  
                            }  
                        } 
            		}
            	}
                
            }  
            return returnClassList;  
        }  
	public static Set<Class> getClassesByPkgName(String pkgName) {
		 //第一个class类的集合  
        Set<Class> classes = new HashSet<Class>();  
        //是否循环迭代  
        boolean recursive = true;  
        //获取包的名字 并进行替换  
        String packageDirName = pkgName.replace('.', '/');  
        //定义一个枚举的集合 并进行循环来处理这个目录下的things  
        Enumeration<URL> dirs;  
        try {  
            dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);  
            //循环迭代下去  
            while (dirs.hasMoreElements()){  
                //获取下一个元素  
                URL url = dirs.nextElement();  
                //得到协议的名称  
                String protocol = url.getProtocol();  
                //如果是以文件的形式保存在服务器上  
                if ("file".equals(protocol)) {  
                    //获取包的物理路径  
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");  
                    //以文件的方式扫描整个包下的文件 并添加到集合中  
                    findAndAddClassesInPackageByFile(pkgName, filePath, recursive, classes);  
                } else if ("jar".equals(protocol)){  
                    //如果是jar包文件   
                    //定义一个JarFile  
                    JarFile jar;  
                    try {  
                        //获取jar  
                        jar = ((JarURLConnection) url.openConnection()).getJarFile();  
                        //从此jar包 得到一个枚举类  
                        Enumeration<JarEntry> entries = jar.entries();  
                        //同样的进行循环迭代  
                        while (entries.hasMoreElements()) {  
                            //获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件  
                            JarEntry entry = entries.nextElement();  
                            String name = entry.getName();  
                            //如果是以/开头的  
                            if (name.charAt(0) == '/') {  
                                //获取后面的字符串  
                                name = name.substring(1);  
                            }  
                            //如果前半部分和定义的包名相同  
                            if (name.startsWith(packageDirName)) {  
                                int idx = name.lastIndexOf('/');  
                                //如果以"/"结尾 是一个包  
                                if (idx != -1) {  
                                    //获取包名 把"/"替换成"."  
                                	pkgName = name.substring(0, idx).replace('/', '.');  
                                }  
                                //如果可以迭代下去 并且是一个包  
                                if ((idx != -1) || recursive){  
                                    //如果是一个.class文件 而且不是目录  
                                    if (name.endsWith(".class") && !entry.isDirectory()) {  
                                        //去掉后面的".class" 获取真正的类名  
                                        String className = name.substring(pkgName.length() + 1, name.length() - 6);  
                                        try {  
                                            //添加到classes  
                                            classes.add(Class.forName(pkgName + '.' + className));  
                                        } catch (ClassNotFoundException e) {  
                                            e.printStackTrace();  
                                        }  
                                      }  
                                }  
                            }  
                        }  
                    } catch (IOException e) {  
                        e.printStackTrace();  
                    }   
                }  
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
         
        return classes;  
	}  
	 /** 
     * 以文件的形式来获取包下的所有Class 
     * @param packageName 
     * @param packagePath 
     * @param recursive 
     * @param classes 
     */  
    public static void findAndAddClassesInPackageByFile(String packageName, String packagePath, final boolean recursive, Set<Class> classes){  
        //获取此包的目录 建立一个File  
        File dir = new File(packagePath);  
        //如果不存在或者 也不是目录就直接返回  
        if (!dir.exists() || !dir.isDirectory()) {  
            return;  
        }  
        //如果存在 就获取包下的所有文件 包括目录  
        File[] dirfiles = dir.listFiles(new FileFilter() {  
        //自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)  
              public boolean accept(File file) {  
                return (recursive && file.isDirectory()) || (file.getName().endsWith(".class"));  
              }  
            });  
        //循环所有文件  
        for (File file : dirfiles) {  
            //如果是目录 则继续扫描  
            if (file.isDirectory()) {  
                findAndAddClassesInPackageByFile(packageName + "." + file.getName(),  
                                      file.getAbsolutePath(),  
                                      recursive,  
                                      classes);  
            }  
            else {  
                //如果是java类文件 去掉后面的.class 只留下类名  
                String className = file.getName().substring(0, file.getName().length() - 6);  
                try {  
                    //添加到集合中去  
                    classes.add(Class.forName(packageName + '.' + className));  
                } catch (ClassNotFoundException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
    }
    /* 
     * 取得某一类所在包的所有类名 不含迭代 
     */  
    public static String[] getPackageAllClassName(String classLocation, String packageName){  
        //将packageName分解  
        String[] packagePathSplit = packageName.split("[.]");  
        String realClassLocation = classLocation;  
        int packageLength = packagePathSplit.length;  
        for(int i = 0; i< packageLength; i++){  
            realClassLocation = realClassLocation + File.separator+packagePathSplit[i];  
        }  
        File packeageDir = new File(realClassLocation);  
        if(packeageDir.isDirectory()){  
            String[] allClassName = packeageDir.list();  
            return allClassName;  
        }  
        return null;  
    }  
}  

