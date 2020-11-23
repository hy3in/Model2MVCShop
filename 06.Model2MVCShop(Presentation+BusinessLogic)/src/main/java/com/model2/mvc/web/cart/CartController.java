package com.model2.mvc.web.cart;

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

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.cart.CartService;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;

@Controller
public class CartController {
	
	@Autowired
	@Qualifier("cartServiceImpl")
	private CartService cartService;
	
	@Autowired
	@Qualifier("productServiceImpl02")
	private ProductService productService;
	
	public CartController() {
		System.out.println(this.getClass());
	}
	
	@Value("#{commonProperties['pageUnit']}")
	//@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	//@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;
	
	@RequestMapping("/addCartView.do")
	public String addCartView() throws Exception{
		System.out.println("/addCartView.do");
		
		return "redirect:/cart/addCartView.jsp";
	}
	
	@RequestMapping("/addCart.do")
	public String addCart(@RequestParam("prodNo") int prodNo, HttpSession session)throws Exception{
		System.out.println("/addCart.do");
		User user = (User)session.getAttribute("user");
		String userId = user.getUserId();
		
		cartService.addCart(userId, prodNo);
		
		return "redirect:/listProduct.do?menu=search";
	}
	
	@RequestMapping("/listCart.do")
	public String listCart(@ModelAttribute("search") Search search , 
							Model model , 
							HttpServletRequest request
							,HttpSession session) throws Exception{
		
		System.out.println("listCart.do start...");
		User user = (User)session.getAttribute("user");
		String userId = user.getUserId();
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		Map<String , Object> map=cartService.getCartList(search, userId);
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		
		return "forward:/cart/cartView.jsp";
	}

}
