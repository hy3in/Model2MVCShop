package com.model2.mvc.service.cart;

import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Cart;

public interface CartService {
	
	public void addCart(String userId, int prodNo) throws Exception;

	public Map<String,Object> getCartList(Search search, String userId)throws Exception;
}
