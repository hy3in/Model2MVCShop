package com.model2.mvc.view.purchase;

import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.user.vo.UserVO;

public class AddPurchaseAction extends Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("addpurchaseaction 시작");
		
		UserVO userVO = new UserVO();
		PurchaseVO purchaseVO = new PurchaseVO();
		ProductVO productVO = new ProductVO();
		
		HttpSession session = request.getSession();
		UserVO uVO = (UserVO) session.getAttribute("user");
		ProductVO pVO = (ProductVO)session.getAttribute("product");
		
		Date date = new Date(0);
		
		purchaseVO.setBuyer(uVO);
		purchaseVO.setPurchaseProd(pVO);
		purchaseVO.setOrderDate(date);
		purchaseVO.setPaymentOption(request.getParameter("paymentOption"));
		purchaseVO.setReceiverName(request.getParameter("receiverName"));
		purchaseVO.setReceiverPhone(request.getParameter("receiverPhone"));
		purchaseVO.setDivyAddr(request.getParameter("receiverAddr"));
		purchaseVO.setDivyRequest(request.getParameter("receiverRequest"));
		purchaseVO.setDivyDate(request.getParameter("receiverDate"));
		purchaseVO.setTranCode("1");
		
		request.setAttribute("vo", purchaseVO);
		
		System.out.println(purchaseVO);
		
		PurchaseService service = new PurchaseServiceImpl();
		System.out.println("임플 시작");
		
		service.addPurchase(purchaseVO);
		System.out.println("서비스.에드펄체이스");
		
		return "forward:/purchase/addPurchase.jsp";
	}
	
	

}
