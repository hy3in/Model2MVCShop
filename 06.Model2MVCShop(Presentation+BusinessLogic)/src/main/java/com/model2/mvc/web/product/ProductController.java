package com.model2.mvc.web.product;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;

@Controller
public class ProductController {
	
	///Field
	@Autowired
	@Qualifier("productServiceImpl02")
	private ProductService productService;
	
	public ProductController() {
		System.out.println(this.getClass());
	}
	
	@Value("#{commonProperties['pageUnit']}")
	//@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	//@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;
	
	@RequestMapping("/addProductView.do")
	public String addProductView() throws Exception{
		System.out.println("/addProductView.do");
		
		return "redirect:/product/addProductView.jsp";
	}
	
	@RequestMapping("/addProduct.do")
	public String addProduct(@ModelAttribute("product") Product product, Model model )throws Exception{
		
		System.out.println("/addProduct.do");
		//Business Logic
		productService.InsertProduct(product);
		
		model.addAttribute("product",product);
		
		return "forward:/product/readProduct.jsp";
	}
	
	@RequestMapping("/getProduct.do")
	public String getProduct(HttpSession session, HttpServletResponse response, HttpServletRequest request, @RequestParam("prodNo") int prodNo, Model model)throws Exception{
		System.out.println("/getProduct.do");
		
		//최근본상품..
		Cookie[] cookies = request.getCookies();
		String sumValue = request.getParameter("prodNo")+",";
		
		for(int i =0; i<cookies.length;i++) {
			if(cookies[i].getName().equals("history")) {
				sumValue = cookies[i].getValue()+request.getParameter("prodNo")+",";
			}
		}	
		
		Cookie cookie = new Cookie("history", sumValue);
		cookie.setMaxAge(60*1);
		response.addCookie(cookie);
		
		Product product = productService.findProduct(prodNo);
		model.addAttribute("product",product);
		session.setAttribute("product", product);
		
		String authorization = (String)session.getAttribute("menuType");
		if(authorization.equals("manage")) {
			return "forward:/product/UpdateProduct.jsp";
		}else {
			return "forward:/product/readProduct.jsp";
		}
	}
	
	@RequestMapping("/listProduct.do")
	public String listProduct(@RequestParam("menu") String menu, @ModelAttribute("search") Search search, Model model , HttpServletRequest request) throws Exception{
		System.out.println("/listProduct.do");
		HttpSession session = request.getSession();
		String authorization = "";
		
		if(search.getCurrentPage() != 0 ){
			if(menu==null) {
				session.getAttribute("menuType");
			}
		}else {
			search.setCurrentPage(1);
			if(menu!=null){
				if(menu.equals("manage")){
					authorization = "manage";
				}else {
					authorization = "search";
				}
				session.setAttribute("menuType", authorization);
			}
		}

		if(request.getParameter("sort")!=null) {
			search.setSort(Integer.parseInt(request.getParameter("sort")));
			System.out.println(search.getSort());
		}
		
		search.setPageSize(pageSize);
		
		// Business logic 수행
		Map<String , Object> map=productService.getProductList(search);
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		// Model 과 View 연결
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		
		return "forward:/product/listProduct.jsp";
	}
	
	@RequestMapping("/updateProductView.do")
	public String updateProductView(@RequestParam("prodNo")int prodNo,Model model ) throws Exception{
		System.out.println("/updateProductView.do");
		
		Product product = productService.findProduct(prodNo);
		
		model.addAttribute("product", product);
		
		return "forward:/product/UpdateProduct.jsp";
	}
	
	@RequestMapping("/updateProduct.do")
	public String updateUser( @ModelAttribute("product") Product product , Model model , HttpSession session) throws Exception{

		System.out.println("/updateProduct.do");
		int prodNo = product.getProdNo();
		System.out.println(prodNo);
		
		productService.updateProduct(product);
		
		return "redirect:/getProduct.do?prodNo="+prodNo;
	}
	

}
