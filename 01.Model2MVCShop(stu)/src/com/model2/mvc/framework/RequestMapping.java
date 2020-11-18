package com.model2.mvc.framework;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

//공용 모듈
//중간 관리자 역할
public class RequestMapping {
	
	private static RequestMapping requestMapping;
	private Map<String, Action> map;
	private Properties properties;
	
	private RequestMapping(String resources) {
		map = new HashMap<String, Action>();
		InputStream in = null;
		try{
			in = getClass().getClassLoader().getResourceAsStream(resources);
			//ClassLoader의 모든경로에서 /없이 파일 읽기.
			properties = new Properties();
			properties.load(in);
			//load()=>맵핑되어있는 텍스트형태 파일을 읽어와서 key,value값으로 파싱해준다.
			//메서드의 이름 그대로 파일의 내용을 읽어서 키-값의 형태로 분류해서 맵에 보관한다.
			
		}catch(Exception ex){
			System.out.println(ex);
			throw new RuntimeException("actionmapping.properties 파일 로딩 실패 :"  + ex);
		}finally{
			if(in != null){
				try{ in.close(); } catch(Exception ex){}
			}
		}
	}
	
	//동일한 자신의 인스턴스를 return하는 static Method
	//여기서 인스턴스 생성, 두번 세번 getInstance()해 줘도 한번 인스턴스 생성한걸로 계속사용한다
	//인사부장은 단 한명이면 되니까
	public synchronized static RequestMapping getInstance(String resources){
		if(requestMapping == null){
			requestMapping = new RequestMapping(resources);
		}
		return requestMapping;
	}
	
	//path에 맞는 action생산
	public Action getAction(String path){
		Action action = map.get(path);
		if(action == null){
			String className = properties.getProperty(path);
			//path는 확인해보면 우리가 앞전에 login.do home.do 이렇게 했던것임
			//위에서 맵핑한거 저장해놨던거 key(path)값을 통해 value를 찾는거임
			System.out.println("prop : " + properties);
			System.out.println("path : " + path);			
			System.out.println("className : " + className);
			className = className.trim();
			try{
				Class c = Class.forName(className);
				//className(value)값을 받아 객체로 생성해 return
				Object obj = c.newInstance();
				//동적으로 넘어오는 인자에 따라 다른 객체 생성 가능
				if(obj instanceof Action){ //obj객체타입이 Action될 수 있으면(Action의 하위인지 아닌지)
					map.put(path, (Action)obj);
					action = (Action)obj;
				}else{
					throw new ClassCastException("Class형변환시 오류 발생  ");
				}
			}catch(Exception ex){
				System.out.println(ex);
				throw new RuntimeException("Action정보를 구하는 도중 오류 발생 : " + ex);
			}
		}
		return action;
	}
}