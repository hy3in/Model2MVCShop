package com.model2.mvc.view.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;
import com.model2.mvc.service.user.vo.UserVO;


public class UpdateUserViewAction extends Action{

	@Override
	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		String userId=request.getParameter("userId");
		
		UserService service=new UserServiceImpl();
		//interface를 사용하지 않으면 userServiceImpl + Dao를 둘 다 여기에 써줘야하니까 간단하게 임플해서 구현한거임
		UserVO userVO=service.getUser(userId);
		
		request.setAttribute("userVO", userVO);
		
		System.out.println("updateuserview 잘 나옴");
		
		return "forward:/user/updateUser.jsp";
	}
}
