package com.model2.mvc.service.product.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductDao;
import com.model2.mvc.service.product.ProductService;

@Service("productServiceImpl02")
public class ProductServiceImpl02 implements ProductService{
	
	@Autowired
	@Qualifier("productDaoImpl02")
	ProductDao productDao;
	public void setProductDao(ProductDao productDao) {
		System.out.println("::"+getClass()+".setProductDao02 Call....");
	    this.productDao = productDao;
	}
	
	///Constructor
	public ProductServiceImpl02() {
		System.out.println("::"+getClass()+".default Constructor02 Call....");
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
	public Product findProduct(int prodNo) throws Exception {
		return productDao.findProduct(prodNo);
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
