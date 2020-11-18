package com.model2.mvc.service.purchase;

import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;

public interface PurchaseDao {
	
	public void InsertPurchase(Purchase purchase);
	
	public Purchase findPurchase(String name);
	
	public Purchase findPurchase2(int ProdNo);
	
	public List<Purchase> getPurchaseList(Search search, String userId);
	
	public void updatePurchase(Purchase purchase);
	
	public void updateTranCode(Purchase purchase);
	
	public int getTotalCount(Search search, String userId) throws Exception ;
}
