package com.model2.mvc.service.product;

import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;

public interface ProductService {
	
	//��ǰ ���
	public void InsertProduct(Product product);
	
	//��ǰ ��� ���
	public Map<String, Object> getProductList(Search search) throws Exception;
	
	//��ǰ �˻�
	public Product findProduct(int prodNo) throws Exception;

	//��ǰ ������Ʈ
	public void updateProduct(Product product) throws Exception;
	
	public int getTotalCount(Search search) throws Exception ;

}
