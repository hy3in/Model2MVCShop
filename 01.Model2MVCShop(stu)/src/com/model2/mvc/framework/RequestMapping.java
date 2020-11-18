package com.model2.mvc.framework;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

//���� ���
//�߰� ������ ����
public class RequestMapping {
	
	private static RequestMapping requestMapping;
	private Map<String, Action> map;
	private Properties properties;
	
	private RequestMapping(String resources) {
		map = new HashMap<String, Action>();
		InputStream in = null;
		try{
			in = getClass().getClassLoader().getResourceAsStream(resources);
			//ClassLoader�� ����ο��� /���� ���� �б�.
			properties = new Properties();
			properties.load(in);
			//load()=>���εǾ��ִ� �ؽ�Ʈ���� ������ �о�ͼ� key,value������ �Ľ����ش�.
			//�޼����� �̸� �״�� ������ ������ �о Ű-���� ���·� �з��ؼ� �ʿ� �����Ѵ�.
			
		}catch(Exception ex){
			System.out.println(ex);
			throw new RuntimeException("actionmapping.properties ���� �ε� ���� :"  + ex);
		}finally{
			if(in != null){
				try{ in.close(); } catch(Exception ex){}
			}
		}
	}
	
	//������ �ڽ��� �ν��Ͻ��� return�ϴ� static Method
	//���⼭ �ν��Ͻ� ����, �ι� ���� getInstance()�� �൵ �ѹ� �ν��Ͻ� �����Ѱɷ� ��ӻ���Ѵ�
	//�λ������ �� �Ѹ��̸� �Ǵϱ�
	public synchronized static RequestMapping getInstance(String resources){
		if(requestMapping == null){
			requestMapping = new RequestMapping(resources);
		}
		return requestMapping;
	}
	
	//path�� �´� action����
	public Action getAction(String path){
		Action action = map.get(path);
		if(action == null){
			String className = properties.getProperty(path);
			//path�� Ȯ���غ��� �츮�� ������ login.do home.do �̷��� �ߴ�����
			//������ �����Ѱ� �����س����� key(path)���� ���� value�� ã�°���
			System.out.println("prop : " + properties);
			System.out.println("path : " + path);			
			System.out.println("className : " + className);
			className = className.trim();
			try{
				Class c = Class.forName(className);
				//className(value)���� �޾� ��ü�� ������ return
				Object obj = c.newInstance();
				//�������� �Ѿ���� ���ڿ� ���� �ٸ� ��ü ���� ����
				if(obj instanceof Action){ //obj��üŸ���� Action�� �� ������(Action�� �������� �ƴ���)
					map.put(path, (Action)obj);
					action = (Action)obj;
				}else{
					throw new ClassCastException("Class����ȯ�� ���� �߻�  ");
				}
			}catch(Exception ex){
				System.out.println(ex);
				throw new RuntimeException("Action������ ���ϴ� ���� ���� �߻� : " + ex);
			}
		}
		return action;
	}
}