package com.model2.mvc.service.product.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductDao;
import com.model2.mvc.service.product.impl.ProductDaoImpl;
import com.model2.mvc.service.product.impl.ProductServiceImpl;


public class ProductServiceTest03 {
	
	//@Test
	public static void main(String args[]) throws Exception{
		
		SqlSession sqlSession = SqlSessionFactoryBean.getSqlSession();
		System.out.println("\n");
		
		//ProductDaoImpl생성 및 sqlSession instance setter injection
		ProductDaoImpl productDao = new ProductDaoImpl();
		productDao.setSqlSession(sqlSession);
		//ProductServiceImpl생성 및 userDao instance setter injection
		ProductServiceImpl productService = new ProductServiceImpl();
		productService.setProductDao(productDao);
		System.out.println("\n");
		
		//Test용 product instance 생성
		Product product = new Product();
		product.setFileName("testFileName");
		product.setManuDate("testManu");
		product.setPrice(123);
		product.setProdDetail("testProdDetail");
		product.setProdName("testProdName");
		
		//1. ProductMapper.addProduct Test :: product table 입력값 확인.
		System.out.println(":: 1. addProduct(INSERT) ? ");
		productService.InsertProduct(product);
		System.out.println("\n");
		
		//2. ProductMapper.getProduct Test :: testProdName insert 확인 
		System.out.println(":: 2. getProduct(SELECT)  ? ");
		System.out.println(":: "+productService.findProduct(product.getProdName()));
		System.out.println("\n");
		
		//3. ProductMapper.updateProduct Test :: product table 
		product.setPrice(999);
		product.setProdDetail("UpdateProdDetail");
		System.out.println(":: 3. updateProdName(UPDATE) ?");
		productService.updateProduct(product);
		System.out.println("\n");
		
		//4. ProductMapper.getProduct Test :: 
		System.out.println(":: 4. getUser(SELECT)  ? ");
		System.out.println(":: "+productService.findProduct(product.getProdName()) );
		System.out.println("\n");
		
		//==> Test용 Search instance 생성
		Search search = new Search();
		
		System.out.println("ProductMapper.getProductList All Test");
		search.setCurrentPage(1);
		search.setPageSize(3);
		Map<String,Object> map = productService.getProductList(search);
		
		List<Object> list = (List<Object>)map.get("list");
		Integer totalCount = (Integer)map.get("totalCount");
		System.out.println(list);
		System.out.println(totalCount);
		
		//END::SqlSession  close
		System.out.println("::END::SqlSession 닫기..");
		sqlSession.close();
	}

}
