package com.model2.mvc.service.cart.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.cart.CartDao;
import com.model2.mvc.service.domain.Cart;

@Repository("cartDaoImpl")
public class CartDaoImpl implements CartDao{
	
	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSession;
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	public void addCart(String userId, int prodNo)throws Exception{
		List list = new ArrayList();
		list.add(userId);
		list.add(prodNo);
		sqlSession.insert("CartMapper.addCart",list);
	}

	@Override
	public List<Cart> getCartList(Search search, String userId) throws Exception {
		List list = new ArrayList();
		list.add(search);
		list.add(userId);
		return sqlSession.selectList("CartMapper.getCartlist",list);
	}
	
	@Override
	public int getTotalCount(Search search, String userId) throws Exception {
		List list = new ArrayList();
		list.add(search);
		list.add(userId);
		return sqlSession.selectOne("CartMapper.getTotalCount", list);
	}

}
