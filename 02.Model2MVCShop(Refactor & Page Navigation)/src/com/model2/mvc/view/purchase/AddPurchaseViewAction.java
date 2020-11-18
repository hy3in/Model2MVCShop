package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;

public class AddPurchaseViewAction extends Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		int prodNo = Integer.parseInt(request.getParameter("prod_no"));
		HttpSession session = request.getSession();
		User userVO = (User) session.getAttribute("user");
		
		PurchaseService service1 = new PurchaseServiceImpl();
		ProductService service3 = new ProductServiceImpl();
		
		ProductVO productVO = service3.getProduct(prodNo);	
		
		request.setAttribute("pvo", productVO);
		
		//request.setAttribute("purchaseVO", purchaseVO);
		
		return "forward:/purchase/addPurchaseView.jsp";
	}

}
