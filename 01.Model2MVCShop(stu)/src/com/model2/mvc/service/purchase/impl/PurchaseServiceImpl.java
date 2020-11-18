package com.model2.mvc.service.purchase.impl;

import java.util.HashMap;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.dao.PurchaseDAO;
import com.model2.mvc.service.purchase.vo.PurchaseVO;

public class PurchaseServiceImpl implements PurchaseService {
	
	private PurchaseDAO purchaseDAO;
	
	public PurchaseServiceImpl() {
		purchaseDAO = new PurchaseDAO();
	}
	
	@Override
	public void addPurchase(PurchaseVO purchaseVO) throws Exception {
		System.out.println("인서트1");
		purchaseDAO.InsertPurchase(purchaseVO);
		System.out.println("인서트2");
	}

	@Override
	public PurchaseVO getPurchase(int tranNo) throws Exception {
		return purchaseDAO.findPurchase(tranNo);
	}

	@Override
	public PurchaseVO getPurchase2(int ProdNo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashMap<String, Object> getPurchaseList(SearchVO searchVO, String buyerId) throws Exception {
		return purchaseDAO.getPurchaseList(searchVO,buyerId);
	}

	@Override
	public HashMap<String, Object> getSaleList(SearchVO searchVO) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updatePurcahse(PurchaseVO purchaseVO) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateTranCode(PurchaseVO purchaseVO) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
