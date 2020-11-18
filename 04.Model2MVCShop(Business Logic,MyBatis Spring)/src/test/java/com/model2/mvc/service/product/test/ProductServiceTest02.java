package com.model2.mvc.service.product.test;

import java.util.ArrayList;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.impl.ProductDaoImpl;


public class ProductServiceTest02 {
	
	//@Test
	public static void main(String args[]) throws Exception{
		
		SqlSession sqlSession = SqlSessionFactoryBean.getSqlSession();
		System.out.println("\n");
		
		//ProductDaoImpl���� �� sqlSession instance setter injection
		ProductDaoImpl productDao = new ProductDaoImpl();
		productDao.setSqlSession(sqlSession);
		System.out.println("\n");
		
		//Test�� product instance ����
		Product product = new Product();
		product.setFileName("testFileName");
		product.setManuDate("testManu");
		product.setPrice(123);
		product.setProdDetail("testProdDetail");
		product.setProdName("testProdName");
		
		//1. ProductMapper.addProduct Test :: product table �Է°� Ȯ��.
		System.out.println(":: 1. addProduct(INSERT) ? ");
		productDao.InsertProduct(product);
		System.out.println("\n");
		
		//2. ProductMapper.getProduct Test :: testProdName insert Ȯ�� 
		System.out.println(":: 2. getProduct(SELECT)  ? ");
		System.out.println(":: "+productDao.findProduct(product.getProdName()));
		System.out.println("\n");
		
		//3. ProductMapper.updateProduct Test :: product table 
		product.setPrice(999);
		product.setProdDetail("UpdateProdDetail");
		System.out.println(":: 3. updateProdName(UPDATE) ?");
		productDao.updateProduct(product);
		System.out.println("\n");
		
		//4. ProductMapper.getProduct Test :: 
		System.out.println(":: 4. getUser(SELECT)  ? ");
		System.out.println(":: "+productDao.findProduct(product.getProdName()) );
		System.out.println("\n");
		
		//==> Test�� Search instance ����
		Search search = new Search();
		
		System.out.println("ProductMapper.getProductList All Test");
		search.setCurrentPage(1);
		search.setPageSize(3);
		
		System.out.println("::List ����"+productDao.getProductList(search));
		System.out.println(productDao.getTotalCount(search));
		
		//END::SqlSession  close
		System.out.println("::END::SqlSession �ݱ�..");
		sqlSession.close();
	}

}
