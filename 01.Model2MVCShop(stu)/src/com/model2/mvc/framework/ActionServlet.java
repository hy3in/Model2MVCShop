package com.model2.mvc.framework;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.common.util.HttpUtil;


public class ActionServlet extends HttpServlet {
	
	private RequestMapping mapper;
	
	//.properties에 지정된 key, value에 알맞는 것을 확인하고
	//그에 맞는 Action이라는 bean을 뽑아낸다.
	@Override
	public void init() throws ServletException {
		super.init();
		//init() 이므로 단 한번만 실행
		String resources=getServletConfig().getInitParameter("resources");
		//web.xml에 맵핑되어있는 resources값을 가져온다.
		mapper=RequestMapping.getInstance(resources);
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) 
																									throws ServletException, IOException {
		//URL 파싱
		//이는 ActionMapping에 기재된 /*.do로 변경될 것이다
		String url = request.getRequestURI();
		String contextPath = request.getContextPath();
		//http://localhost:8080/board/list.do 의 경로가 있다면
		//http://localhost:8080/board가 contextPath에 담김
		String path = url.substring(contextPath.length());
		//URL에서 앞에 project명까지 지우고 path에 담는다
		//list.do만 남게 된다.
		System.out.println(path);
		
		try{
			//알맞은 Action을 가져왔으면 그에 맞는 behavior을 해준다
			Action action = mapper.getAction(path);
			//각각에 맞는 HomeController LogonController...(GetUserAction)이런걸 가져온다
			action.setServletContext(getServletContext());
			
			String resultPage=action.execute(request, response);
			//그래서 밑에서도 action이 Controller가 아닌 HomeController이런게 작동하는거임
			String result=resultPage.substring(resultPage.indexOf(":")+1);
			
			//응답방식에 따라 클라이언트에게 전달
			if(resultPage.startsWith("forward:"))
				HttpUtil.forward(request, response, result);
			else
				HttpUtil.redirect(response, result);
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}