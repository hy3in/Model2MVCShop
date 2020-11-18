package com.model2.mvc.service.product.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductDao;

public class ProductDaoImpl implements ProductDao{
	
	private SqlSession sqlSession;
	
	public void setSqlSession(SqlSession sqlSession) {
		System.out.println("::"+getClass()+".setSqlSession() Call....");
	    this.sqlSession = sqlSession;
	 }

	public ProductDaoImpl() {
		System.out.println("::"+getClass()+".default Constructor Call....");
	 }

	@Override
	public void InsertProduct(Product product) {
		sqlSession.insert("ProductMapper.addProduct",product);	
	}

	@Override
	public List<Product> getProductList(Search search) throws Exception {
		return sqlSession.selectList("ProductMapper.getProductList",search);
	}

	@Override
	public Product findProduct(String prodName) throws Exception {
		return sqlSession.selectOne("ProductMapper.getProduct",prodName);
	}

	@Override
	public void updateProduct(Product product) throws Exception {
		sqlSession.update("ProductMapper.updateProduct",product);
		
	}

	@Override
	public int getTotalCount(Search search) throws Exception {
		return sqlSession.selectOne("ProductMapper.getTotalCount", search);
	}

}
