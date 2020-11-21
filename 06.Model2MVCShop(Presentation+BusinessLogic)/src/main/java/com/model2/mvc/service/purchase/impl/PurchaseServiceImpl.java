package com.model2.mvc.service.purchase.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.product.ProductDao;
import com.model2.mvc.service.purchase.PurchaseDao;
import com.model2.mvc.service.purchase.PurchaseService;

@Service("purchaseServiceImpl")
public class PurchaseServiceImpl implements PurchaseService {
	
	@Autowired
	@Qualifier("purchaseDaoImpl")
	PurchaseDao purchaseDao;
	public void setProductDao(ProductDao productDao) {
		System.out.println("::"+getClass()+".setProductDao02 Call....");
	    this.purchaseDao = purchaseDao;
	}
	
	///Constructor
	public PurchaseServiceImpl() {
		System.out.println("::"+getClass()+".default Constructor02 Call....");
	}
	
	@Override
	public void InsertPurchase(Purchase purchase) {
		purchaseDao.InsertPurchase(purchase);
	}

	@Override
	public Purchase findPurchase(int tranNo) {
		return purchaseDao.findPurchase(tranNo);
	}

	@Override
	public Purchase findPurchase2(int ProdNo) {
		return purchaseDao.findPurchase2(ProdNo);
	}

	@Override
	public Map<String, Object> getPurchaseList(Search search, String userId) throws Exception {
		List<Purchase> list = purchaseDao.getPurchaseList(search, userId);
		int totalCount = purchaseDao.getTotalCount(search, userId);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("list", list);
		map.put("totalCount", new Integer(totalCount));
		return map;
	}

	@Override
	public void updatePurchase(Purchase purchase) {
		purchaseDao.updatePurchase(purchase);
	}

	@Override
	public void updateTranCode(Purchase purchase) {
		purchaseDao.updateTranCode(purchase);
	}

}
