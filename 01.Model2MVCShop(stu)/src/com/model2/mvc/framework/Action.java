package com.model2.mvc.framework;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//���� ���
//���� �ۼ��� ��� Action class�� ���� Ŭ����.
//ServletContext��ü�� ���� Ŭ�������� �̿��� �� �ֵ��� �����ϰ� ȹ���ϴ� �޼��带 �����Ѵ�.
//���� Ŭ�������� �������̵� �޾ƾ� �ϴ� execute�޼��带 �����Ѵ�.
public abstract class Action {
	
	private ServletContext servletContext;
	
	public Action(){
	}
	
	public ServletContext getServletContext() {
		return servletContext;
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	public abstract String execute(HttpServletRequest request, HttpServletResponse response) throws Exception ;
}