package com.model2.mvc.service.product.test;

import java.util.ArrayList;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductDao;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductDaoImpl;
import com.model2.mvc.service.product.impl.ProductServiceImpl;


public class ProductServiceTest05 {
	
	//@Test
	public static void main(String args[]) throws Exception{
		
		ApplicationContext context =
				new ClassPathXmlApplicationContext(
																	new String[] {	"/config/commonservice.xml" }
									                                  );
			System.out.println("\n");

			//==> Bean/IoC Container �� ���� ȹ���� UserService �ν��Ͻ� ȹ��
			ProductService productService = (ProductService)context.getBean("productServiceImpl02");
			
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
		productService.InsertProduct(product);
		System.out.println("\n");
		
		//2. ProductMapper.getProduct Test :: testProdName insert Ȯ�� 
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
		
		//==> Test�� Search instance ����
		Search search = new Search();
		
		System.out.println("ProductMapper.getProductList All Test");
		search.setCurrentPage(1);
		search.setPageSize(3);
		
		System.out.println("::List ����"+productService.getProductList(search));
		System.out.println(productService.getTotalCount(search));
		
		//END::SqlSession  close
		System.out.println("::END::SqlSession �ݱ�..");
	}

}
