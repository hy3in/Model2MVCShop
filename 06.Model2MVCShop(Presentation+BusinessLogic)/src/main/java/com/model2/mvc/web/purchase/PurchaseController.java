package com.model2.mvc.web.purchase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.model2.mvc.service.purchase.PurchaseService;

@Controller
public class PurchaseController {
	
	///Field
	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;
	
	///Constructor
	public PurchaseController() {
		System.out.println("==>PurchaseController default Constructor call..");
	}
	
	//@RequestMapping("")

}
