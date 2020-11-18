package com.model2.mvc.service.product.test;

import java.util.ArrayList;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;


public class ProductServiceTest01 {
	
	//@Test
	public static void main(String args[]) throws Exception{
		
		SqlSession sqlSession = SqlSessionFactoryBean.getSqlSession();
		System.out.println("\n");
		
		Product product = new Product();
		product.setFileName("testFileName");
		product.setManuDate("testManu");
		product.setPrice(123);
		product.setProdDetail("testProdDetail");
		product.setProdName("testProdName");
		
		//1. ProductMapper.addProduct Test :: product table 입력값 확인.
		System.out.println(":: 1. addProduct(INSERT) ? ");
		System.out.println("::"+sqlSession.insert("ProductMapper.addProduct",product));
		System.out.println("\n");
		
		//2. ProductMapper.getProduct Test :: testProdName insert 확인 
		System.out.println(":: 2. getProduct(SELECT)  ? ");
		System.out.println(":: "+sqlSession.selectOne("ProductMapper.getProduct",product.getProdName()));
		System.out.println("\n");
		
		//3. ProductMapper.updateProduct Test :: product table 
		product.setPrice(999);
		product.setProdDetail("UpdateProdDetail");
		System.out.println(":: 3. updateProdName(UPDATE) ?");
		System.out.println("::"+sqlSession.update("ProductMapper.updateProduct",product));
		System.out.println("\n");
		
		//4. ProductMapper.getProduct Test :: 
		System.out.println(":: 4. getUser(SELECT)  ? ");
		System.out.println(":: "+sqlSession.selectOne("ProductMapper.getProduct",product.getProdName()) );
		System.out.println("\n");
		
		//==> Test용 Search instance 생성
		Search search = new Search();
		
		//1. ProductMapper.getProductList All Test
		search.setCurrentPage(1);
		search.setPageSize(3);
		SqlSessionFactoryBean.printList( sqlSession.selectList("ProductMapper.getProductList",search) );
		System.out.println(( sqlSession.selectList("ProductMapper.getTotalCount",search)));
		
		search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("0");
	 	search.setSearchKeyword("");
	 	SqlSessionFactoryBean.printList( sqlSession.selectList("ProductMapper.getProductList",search) );
		System.out.println(( sqlSession.selectList("ProductMapper.getTotalCount",search)));
		System.out.println("===========================================================");
		
		//2. ProductMapper.getProductList prod_no Test
		search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("0");
	 	search.setSearchKeyword("10001");
	 	SqlSessionFactoryBean.printList( sqlSession.selectList("ProductMapper.getProductList",search) );
		System.out.println(( sqlSession.selectList("ProductMapper.getTotalCount",search)));
		System.out.println("===========================================================");
		
		//3. ProductMapper.getProductList prod_name Test
		search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("1");
	 	search.setSearchKeyword("자전거");
	 	SqlSessionFactoryBean.printList( sqlSession.selectList("ProductMapper.getProductList",search) );
		System.out.println(( sqlSession.selectList("ProductMapper.getTotalCount",search)));
		System.out.println("===========================================================");
	
		//4. ProductMapper.getProductList price Test
		System.out.println("getProductList price Test");
		search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("2");
	 	search.setSearchKeyword("333");
	 	SqlSessionFactoryBean.printList( sqlSession.selectList("ProductMapper.getProductList",search) );
		System.out.println(( sqlSession.selectList("ProductMapper.getTotalCount",search)));
		System.out.println("===========================================================");
	
		
		//END::SqlSession  close
		System.out.println("::END::SqlSession 닫기..");
		sqlSession.close();
	}

}
