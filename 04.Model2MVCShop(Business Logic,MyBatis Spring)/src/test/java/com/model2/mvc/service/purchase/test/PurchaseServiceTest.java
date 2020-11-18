package com.model2.mvc.service.purchase.test;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.purchase.PurchaseService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:config/commonservice.xml" })
public class PurchaseServiceTest {
	
	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;
	
	//@Test
	public void testAddPurchase() throws Exception{
		
		User user = new User();
		user.setUserId("user05");
		
		Product product = new Product();
		product.setProdNo(10000);
		
		Purchase purchase = new Purchase();
		purchase.setBuyer(user);
		purchase.setDivyAddr("testAddr");
		purchase.setDivyDate("20201001");
		purchase.setDivyRequest("testRequest");
		purchase.setPaymentOption("1");
		purchase.setPurchaseProd(product);
		purchase.setReceiverName("testName");
		purchase.setReceiverPhone("123456");
		purchase.setTranCode("1");
		
		purchaseService.InsertPurchase(purchase);
		
		purchase = purchaseService.findPurchase("testName");
		
		Assert.assertEquals("testName", purchase.getReceiverName());
	}
	
	@Test
	public void testFindPurchase() throws Exception{
		
		System.out.println("find test");
		Purchase purchase = new Purchase();
		
		purchase = purchaseService.findPurchase("SCOTT");
		System.out.println(purchase);
		Assert.assertEquals("SCOTT", purchase.getReceiverName());
		Assert.assertEquals("user05", purchase.getBuyer().getUserId());
	}
	
	//@Test
	public void testFindPurchase2() throws Exception{
		
		System.out.println("find test2");
		Purchase purchase = new Purchase();
		
		purchase = purchaseService.findPurchase2(10000);
		System.out.println(purchase);
		Assert.assertEquals("testName", purchase.getReceiverName());
		Assert.assertEquals("user05", purchase.getBuyer().getUserId());
	}
	
	//@Test
	public void testGetPurchaseList() throws Exception{
		
		Search search = new Search();
		String userId = "user05";
		System.out.println("ProductMapper.getProductList All Test");
		search.setCurrentPage(1);
		search.setPageSize(3);
		Map<String, Object> map = purchaseService.getPurchaseList(search, userId);
		
		List<Object> list = (List<Object>)map.get("list");
		Assert.assertEquals(2, list.size());
		
		Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	 	System.out.println("=======================================");
	 	
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("0");
	 	search.setSearchKeyword("");
	 	map = purchaseService.getPurchaseList(search, userId);
	 	
	 	list = (List<Object>)map.get("list");
	 	Assert.assertEquals(2, list.size());
	 	
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);	
	}
	
	//@Test
	public void testUpdatePurchase() throws Exception{
		
		Purchase purchase = purchaseService.findPurchase("SCOTT");
		System.out.println(purchase);
		Assert.assertNotNull(purchase);
		
		Assert.assertEquals("SCOTT", purchase.getReceiverName());
		
		purchase.setReceiverName("updateSCOTT");
		
		purchaseService.updatePurchase(purchase);
		
		Assert.assertNotNull(purchase);
		
		Assert.assertEquals("updateSCOTT", purchase.getReceiverName());
	}

}
