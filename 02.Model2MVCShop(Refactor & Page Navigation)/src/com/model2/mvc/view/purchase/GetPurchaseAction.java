package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;

public class GetPurchaseAction extends Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("GetPurchaseAction Ω√¿€ ===================");
		int tranNo = Integer.parseInt(request.getParameter("tranNo"));
		
		/*HttpSession session=request.getSession();
		User user = (User)session.getAttribute("user");
		System.out.println(user);
		String userId = user.getUserId();*/
		System.out.println("tranNo==="+tranNo);
		PurchaseService purchaseService=new PurchaseServiceImpl();
		PurchaseVO purchaseVO = purchaseService.getPurchase(tranNo);
		
		request.setAttribute("purchaseVO", purchaseVO);
		System.out.println("-===-=-=-=-=-=-="+purchaseVO);
		
		return "forward:/purchase/getPurchase.jsp";
	}

}
