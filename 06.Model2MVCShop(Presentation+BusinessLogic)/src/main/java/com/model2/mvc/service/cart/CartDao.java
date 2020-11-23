package com.model2.mvc.service.cart;

import java.util.List;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Cart;

public interface CartDao {
	
	public void addCart(String userId, int prodNo) throws Exception;
	
	public List<Cart> getCartList(Search search, String userId)throws Exception;
	

	public int getTotalCount(Search search, String userId) throws Exception;
}
