package com.model2.mvc.web.purchase;

import java.sql.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl02;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.user.UserService;


@Controller
public class PurchaseController {
	
	///Field
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;
	
	@Autowired
	@Qualifier("productServiceImpl02")
	private ProductService productService;
	
	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;
	
	@Value("#{commonProperties['pageUnit']}")
	//@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	//@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;
	
	///Constructor
	public PurchaseController() {
		System.out.println("==>PurchaseController default Constructor call..");
	}
	
	@RequestMapping("/addPurchaseView.do")
	public ModelAndView addPurchaseView(HttpSession session,
										HttpServletRequest request,
										@RequestParam("prod_no") int prodNo,
										Model model) throws Exception{
		
		ModelAndView modelAndView = new ModelAndView();
		
		User user = (User) session.getAttribute("user");
		Product product = productService.findProduct(prodNo);
		
		modelAndView.setViewName("forward:/purchase/addPurchaseView.jsp");
		modelAndView.addObject("pvo", product);
		
		return modelAndView;
	}
	
	@RequestMapping("/addPurchase.do")
	public ModelAndView addPurchase(@ModelAttribute("purchase") Purchase purchase,
									HttpSession session,
									HttpServletRequest request,
									Model model) throws Exception{
		System.out.println("/addPurchase start....");
		
		ModelAndView modelAndView = new ModelAndView();
		User user = (User)session.getAttribute("user");
		Product product = (Product)session.getAttribute("product");
		Date date = new Date(0);
		
		purchase.setBuyer(user);
		purchase.setPurchaseProd(product);
		purchase.setOrderDate(date);
		purchase.setTranCode("1");
		//jsp를 divyDate로 고치면 캘린더가 안열리는데..?
		purchase.setDivyDate(request.getParameter("receiverDate"));
		purchaseService.InsertPurchase(purchase);
		
		modelAndView.setViewName("forward:/purchase/addPurchase.jsp");
		modelAndView.addObject("purchase", purchase);
			
		return modelAndView;
	}
	
	@RequestMapping("/getPurchase.do")
	public ModelAndView getPurchase(@RequestParam("tranNo") int tranNo,
									Model model)throws Exception{
		System.out.println("/getPurchase.do");
		
		ModelAndView modelAndView = new ModelAndView();
		
		Purchase purchase = purchaseService.findPurchase(tranNo);
		
		modelAndView.addObject("purchase",purchase);
		modelAndView.setViewName("forward:/purchase/getPurchase.jsp");
		
		return modelAndView;
	}
	
	@RequestMapping("/listPurchase.do")
	public ModelAndView getListPurchase(HttpSession session,
									@ModelAttribute("search") Search search,
									@ModelAttribute("page") Page page,
									Model model) throws Exception{
		
		System.out.println("/listPurchase.do");
		ModelAndView modelAndView = new ModelAndView();
		String buyerId = ((User)session.getAttribute("user")).getUserId();
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		Map<String , Object> map=purchaseService.getPurchaseList(search, buyerId);
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);

		modelAndView.addObject("list", map.get("list"));
		modelAndView.addObject("resultPage", resultPage);
		modelAndView.addObject("search", search);
		
		modelAndView.setViewName("forward:/purchase/listPurchase.jsp");
		
		return modelAndView;
	}
	
	@RequestMapping("/updatePurchaseView.do")
	public ModelAndView updatePurchaseView(@RequestParam("tranNo") int tranNo,
										Model model) throws Exception{
		ModelAndView modelAndView = new ModelAndView();
		
		Purchase purchase=purchaseService.findPurchase(tranNo);
		
		modelAndView.addObject("purchase",purchase);
		modelAndView.setViewName("forward:/purchase/updatePurchase.jsp");
		return modelAndView;
	}
	
	@RequestMapping("/updatePurchase.do")
	public ModelAndView updatePurchase(HttpSession session,
									@RequestParam("tranNo") int tranNo,
									@ModelAttribute("purchase") Purchase purchase,
									Model model) throws Exception{
		System.out.println("/updatePurchase start...");
		ModelAndView modelAndView = new ModelAndView();
		User user= (User)session.getAttribute("user");
		
		purchase.setBuyer(user);
		purchase.setTranNo(tranNo);
		System.out.println(purchase);
		purchaseService.updatePurchase(purchase);
		
		modelAndView.addObject("purchase", purchase);
		modelAndView.setViewName("redirect:/getPurchase.do?tranNo="+tranNo);
		return modelAndView;
	}
	
	@RequestMapping("/updateTranCode.do")
	public ModelAndView updateTranCode(@RequestParam("prodNo") int prodNo,
									@RequestParam("tranCode") String tranCode) throws Exception{
		
		ModelAndView modelAndView = new ModelAndView();
		Purchase purchase=new Purchase();
		
		purchase = purchaseService.findPurchase2(prodNo);
		purchase.setTranCode(tranCode);
		purchaseService.updateTranCode(purchase);
		
		modelAndView.setViewName("redirect:/listPurchase.do");
		return modelAndView;
	}
	
	@RequestMapping("/updateTranCodeByProd.do")
	public ModelAndView updateTranCodeByProdAction(@RequestParam("prodNo") int prodNo,
									@RequestParam("tranCode") String tranCode) throws Exception{
		
		ModelAndView modelAndView = new ModelAndView();
		Purchase purchase=new Purchase();
		
		purchase = purchaseService.findPurchase2(prodNo);
		purchase.setTranCode(tranCode);
		purchaseService.updateTranCode(purchase);
		
		modelAndView.setViewName("redirect:/listProduct.do?menu=manage");
		return modelAndView;
	}

}
