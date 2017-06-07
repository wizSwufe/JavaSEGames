package com.bjsxt.tank;

import java.io.IOException;
import java.util.Properties;

public class PropertyMgr {

	static Properties props=new Properties();//���������ļ��� 
	
	static {//��̬������
		try {
			props.load(PropertyMgr.class.getClassLoader().getResourceAsStream("config/tank.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private PropertyMgr(){};//����ģʽ
	
	public static String getProperty(String key){
		return props.getProperty(key);
	}
	
}
