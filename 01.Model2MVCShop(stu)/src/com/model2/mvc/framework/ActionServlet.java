package com.model2.mvc.framework;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.common.util.HttpUtil;


public class ActionServlet extends HttpServlet {
	
	private RequestMapping mapper;
	
	//.properties�� ������ key, value�� �˸´� ���� Ȯ���ϰ�
	//�׿� �´� Action�̶�� bean�� �̾Ƴ���.
	@Override
	public void init() throws ServletException {
		super.init();
		//init() �̹Ƿ� �� �ѹ��� ����
		String resources=getServletConfig().getInitParameter("resources");
		//web.xml�� ���εǾ��ִ� resources���� �����´�.
		mapper=RequestMapping.getInstance(resources);
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) 
																									throws ServletException, IOException {
		//URL �Ľ�
		//�̴� ActionMapping�� ����� /*.do�� ����� ���̴�
		String url = request.getRequestURI();
		String contextPath = request.getContextPath();
		//http://localhost:8080/board/list.do �� ��ΰ� �ִٸ�
		//http://localhost:8080/board�� contextPath�� ���
		String path = url.substring(contextPath.length());
		//URL���� �տ� project����� ����� path�� ��´�
		//list.do�� ���� �ȴ�.
		System.out.println(path);
		
		try{
			//�˸��� Action�� ���������� �׿� �´� behavior�� ���ش�
			Action action = mapper.getAction(path);
			//������ �´� HomeController LogonController...(GetUserAction)�̷��� �����´�
			action.setServletContext(getServletContext());
			
			String resultPage=action.execute(request, response);
			//�׷��� �ؿ����� action�� Controller�� �ƴ� HomeController�̷��� �۵��ϴ°���
			String result=resultPage.substring(resultPage.indexOf(":")+1);
			
			//�����Ŀ� ���� Ŭ���̾�Ʈ���� ����
			if(resultPage.startsWith("forward:"))
				HttpUtil.forward(request, response, result);
			else
				HttpUtil.redirect(response, result);
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}