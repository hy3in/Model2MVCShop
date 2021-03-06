package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.dao.PurchaseDAO;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.vo.PurchaseVO;

public class UpdateTranCodeByProdAction extends Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("updateTranCodeAction ���� =========");
		
		String tranCode =request.getParameter("tranCode");
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		PurchaseVO vo = new PurchaseVO();

		PurchaseService purchaseService = new PurchaseServiceImpl();
		vo = purchaseService.getPurchase2(prodNo);
		vo.setTranCode(tranCode);		
		purchaseService.updateTranCode(vo);
		
		return "redirect:/listProduct.do?menu=manage" ;
	}

}
