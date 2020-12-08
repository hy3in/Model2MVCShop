package com.model2.mvc.service.product;

import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;

public interface ProductService {
	
	//상품 등록
	public void InsertProduct(Product product);
	
	//상품 목록 출력
	public Map<String, Object> getProductList(Search search) throws Exception;
	
	//상품 검색
	public Product findProduct(int prodNo) throws Exception;

	//상품 업데이트
	public void updateProduct(Product product) throws Exception;
	
	public int getTotalCount(Search search) throws Exception ;

}
