
package com.model2.mvc.service.product;

import java.util.HashMap;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.product.vo.ProductVO;


public interface ProductService {
	
	public void addProduct(ProductVO productVO) throws Exception;

	public ProductVO getProduct(int prodNo) throws Exception;

	public Map<String, Object> getProductList(Search search) throws Exception;

	public void updateProduct(ProductVO productVO) throws Exception;
	
}