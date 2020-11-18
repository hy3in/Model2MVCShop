package com.model2.mvc.framework;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//공통 모듈
//이후 작성될 모든 Action class의 상위 클래스.
//ServletContext객체를 하위 클래스에서 이용할 수 있도록 셋팅하고 획득하는 메서드를 제공한다.
//하위 클래스에서 오버라이드 받아야 하는 execute메서드를 선언한다.
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