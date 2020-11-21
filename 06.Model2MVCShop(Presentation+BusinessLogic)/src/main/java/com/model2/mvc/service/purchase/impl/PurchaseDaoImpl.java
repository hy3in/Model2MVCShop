package com.model2.mvc.service.purchase.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseDao;

@Repository("purchaseDaoImpl")
public class PurchaseDaoImpl implements PurchaseDao{
	
	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSession;
	public void setSqlSession(SqlSession sqlSession) {
		System.out.println("::"+getClass()+".setSqlSession() Call....");
	    this.sqlSession = sqlSession;
	 }

	public PurchaseDaoImpl() {
		System.out.println("::"+getClass()+".default Constructor Call....");
	 }

	@Override
	public void InsertPurchase(Purchase purchase) {
		sqlSession.insert("PurchaseMapper.addPurchase",purchase);
	}

	@Override
	public Purchase findPurchase(String name) {
		return sqlSession.selectOne("PurchaseMapper.getPurchase",name);
	}
	
	

	@Override
	public Purchase findPurchase2(int ProdNo) {
		return sqlSession.selectOne("PurchaseMapper.getPurchase2",ProdNo);
	}

	@Override
	public List<Purchase> getPurchaseList(Search search, String userId) {
		List list = new ArrayList();
		list.add(search);
		list.add(userId);
		return sqlSession.selectList("PurchaseMapper.getPurchaseList",list);
	}

	@Override
	public void updatePurchase(Purchase purchase) {
		sqlSession.update("PurchaseMapper.updatePurchase",purchase);
	}

	@Override
	public void updateTranCode(Purchase purchase) {
		sqlSession.update("PurchaseMapper.updateTranCode",purchase);
		
	}

	@Override
	public int getTotalCount(Search search, String userId) throws Exception {
		List list = new ArrayList();
		list.add(search);
		list.add(userId);
		return sqlSession.selectOne("PurchaseMapper.getTotalCount", list);
	}

}
