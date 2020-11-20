package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.vo.PurchaseVO;

public class UpdatePurchaseAction extends Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("updatePurchaseAction Ω√¿€ ===========");
		int tranNo = Integer.parseInt(request.getParameter("tranNo"));
		PurchaseVO vo = new PurchaseVO();
		HttpSession session=request.getSession();
		User buyer = (User)session.getAttribute("user");
		
		vo.setTranNo(tranNo);
		vo.setBuyer(buyer);
		vo.setPaymentOption(request.getParameter("paymentOption"));
		vo.setReceiverName(request.getParameter("receiverName"));
		vo.setReceiverPhone(request.getParameter("receiverPhone"));
		vo.setDivyAddr(request.getParameter("receiverAddr"));
		vo.setDivyRequest(request.getParameter("receiverRequest"));
		vo.setDivyDate(request.getParameter("divyDate"));
		
		PurchaseService purchaseService = new PurchaseServiceImpl();
		purchaseService.updatePurcahse(vo);
		
		System.out.println("updatePurchaseAction ≥° ===========");
		return "redirect:/getPurchase.do?tranNo="+tranNo;
	}

}
