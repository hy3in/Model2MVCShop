package com.model2.mvc.web.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;

@RestController
@RequestMapping("/product/*")
public class ProductRestController {
	
	///Field
	@Autowired
	@Qualifier("productServiceImpl02")
	private ProductService productService;
	
	@Value("#{commonProperties['pageUnit']}")
	int pageUnit;
	@Value("#{commonProperties['pageSize']}")
	int pageSize;
	
	public ProductRestController() {
		System.out.println(this.getClass());
	}
	
	@RequestMapping(value="json/getProduct/{prodNo}", method=RequestMethod.GET)
	public Product getProduct(@PathVariable int prodNo) throws Exception{
		
		System.out.println("json getProduct 시작@@@@@@@@@@@@@");
		System.out.println(productService.findProduct(prodNo));
		return productService.findProduct(prodNo);
	}
	
	@RequestMapping(value="json/listProduct", method = RequestMethod.POST)
	public List ListProduct(@RequestBody Search search, HttpServletRequest request)throws Exception{
		
		System.out.println("json listproduct시작 @@");
		System.out.println("@@"+search);
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		Map<String , Object> map= productService.getProductList(search);
		
		List<Product> productList = (List<Product>)map.get("list");
		System.out.println("productList===="+productList);
		System.out.println("listSize == "+productList.size());
		List productNameList = new ArrayList<String>();
		
		for(int i=0; i<productList.size(); i++) {			
			productNameList.add(productList.get(i).getProdName());
		}
		
		return productNameList;
	}

}
