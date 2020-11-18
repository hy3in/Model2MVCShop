package com.model2.mvc.service.product.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductDao;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductDaoImpl;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:config/context-common.xml",
									"classpath:config/context-aspect.xml",
									"classpath:config/context-mybatis.xml",
									"classpath:config/context-transaction.xml"})
public class ProductServiceTest06 {
	
	@Autowired
	@Qualifier("productServiceImpl02")
	private ProductService productService;
	
	//@Test
	public void testAddProduct() throws Exception{
		
		Product product = new Product();
		product.setFileName("testFileName");
		product.setManuDate("testManu");
		product.setPrice(123);
		product.setProdDetail("testProdDetail");
		product.setProdName("testProdName");
		
		productService.InsertProduct(product);
		
		product = productService.findProduct("testProdName");
		
		Assert.assertEquals("testFileName", product.getFileName());
		Assert.assertEquals("testManu", product.getManuDate());
		Assert.assertEquals(123, product.getPrice());
		Assert.assertEquals("testProdDetail", product.getProdDetail());
		Assert.assertEquals("testProdName", product.getProdName());
	}
	
	@Test
	public void testFindProduct() throws Exception{
		
		System.out.println("find test");
		Product product = new Product();
		
		product = productService.findProduct("testProdName");

		Assert.assertEquals("testFileName", product.getFileName());
		Assert.assertEquals("testManu", product.getManuDate());
		Assert.assertEquals(123, product.getPrice());
		Assert.assertEquals("testProdDetail", product.getProdDetail());
		Assert.assertEquals("testProdName", product.getProdName());
		
		Assert.assertNotNull(productService.findProduct("ÀÚÀü°Å"));
	}
	
	//@Test
	public void testUpdateProduct() throws Exception{
		
		Product product = productService.findProduct("testProdName");
		Assert.assertNotNull(product);
		
		Assert.assertEquals(123, product.getPrice());
		Assert.assertEquals("testProdDetail", product.getProdDetail());
		
		product.setPrice(999);
		product.setProdDetail("UpdateProdDetail");
		
		productService.updateProduct(product);
		
		product = productService.findProduct("testProdName");
		Assert.assertNotNull(product);
		
		Assert.assertEquals(999, product.getPrice());
		Assert.assertEquals("UpdateProdDetail", product.getProdDetail());
	}
	
	@Test
	public void testGetProductListAll() throws Exception{
		
		Search search = new Search();
		
		System.out.println("ProductMapper.getProductList All Test");
		search.setCurrentPage(1);
		search.setPageSize(3);
		search.setSort(1);
		Map<String, Object> map = productService.getProductList(search);
		System.out.println(map);
		List<Object> list = (List<Object>)map.get("list");
		Assert.assertEquals(3, list.size());
		
		Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	 	System.out.println("=======================================");
	 	
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("0");
	 	search.setSearchKeyword("");
	 	map = productService.getProductList(search);
	 	
	 	list = (List<Object>)map.get("list");
	 	Assert.assertEquals(3, list.size());
	 	
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	}
	
	//@Test
	public void testGetProductListByProdNo() throws Exception{
		
		Search search = new Search();
		
		System.out.println("ProductMapper.getProductList All Test");
		search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("0");
	 	search.setSearchKeyword("10001");
		Map<String, Object> map = productService.getProductList(search);
		
		List<Object> list = (List<Object>)map.get("list");
		Assert.assertEquals(1, list.size());
		
		Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	}

}
