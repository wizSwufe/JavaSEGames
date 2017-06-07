package com.bjsxt.tank;

import java.io.IOException;
import java.util.Properties;

public class PropertyMgr {

	static Properties props=new Properties();//辅助配置文件类 
	
	static {//静态代码区
		try {
			props.load(PropertyMgr.class.getClassLoader().getResourceAsStream("config/tank.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private PropertyMgr(){};//单例模式
	
	public static String getProperty(String key){
		return props.getProperty(key);
	}
	
}
