package com.model2.mvc.service.product.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductDao;
import com.model2.mvc.service.product.ProductService;

public class ProductServiceImpl implements ProductService{
	
	ProductDao productDao;
	
	public void setProductDao(ProductDao productDao) {
		System.out.println("::"+getClass()+".setProductDao Call....");
	    this.productDao = productDao;
	}
	
	public ProductServiceImpl() {
		System.out.println("::"+getClass()+".default Constructor Call....");
	}
	
	@Override
	public void InsertProduct(Product product) {
		productDao.InsertProduct(product);
	}

	@Override
	public Map<String , Object> getProductList(Search search) throws Exception {
		List<Product> list = productDao.getProductList(search);
		int totalCount = productDao.getTotalCount(search);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("list", list);
		map.put("totalCount", new Integer(totalCount));
		return map;
	}

	@Override
	public Product findProduct(String prodName) throws Exception {
		return productDao.findProduct(prodName);
	}

	@Override
	public void updateProduct(Product product) throws Exception {
		productDao.updateProduct(product);
	}

	@Override
	public int getTotalCount(Search search) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

}
