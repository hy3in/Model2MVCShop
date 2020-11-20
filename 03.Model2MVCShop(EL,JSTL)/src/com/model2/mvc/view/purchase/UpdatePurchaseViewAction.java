package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.vo.PurchaseVO;

public class UpdatePurchaseViewAction extends Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("UpdatePurchaseViewAction Ω√¿€ ==========");
		int tranNo = Integer.parseInt(request.getParameter("tranNo"));
		System.out.println(tranNo);
		
		PurchaseService purchaseService = new PurchaseServiceImpl();
		PurchaseVO vo = purchaseService.getPurchase(tranNo);
		System.out.println("Update vo===="+vo);
		request.setAttribute("purchaseVO", vo);
		
		return "forward:/purchase/updatePurchase.jsp";
	}

}
