package com.model2.mvc.web.product;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;

@Controller
@RequestMapping("/product/*")
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
	
	@RequestMapping(value = "/addProduct", method = RequestMethod.GET)
	public String addProduct() throws Exception{
		System.out.println("/addProduct GET");
		
		return "redirect:/product/addProductView.jsp";
	}
	
	@RequestMapping( value = "/addProduct", method = RequestMethod.POST)
	public String addProduct(@ModelAttribute("product") Product product, Model model )throws Exception{
		
		System.out.println("/addProduct POST");
		
		String temDir = "C:\\Users\\hy3in\\git\\Model2MVCShop\\07.Model2MVCShop(URI,pattern)\\WebContent\\images\\uploadFiles\\";
		
		MultipartFile uploadfile = product.getUploadFile();
		if(uploadfile !=null) {
			String fileName = uploadfile.getOriginalFilename();
			product.setFileName(fileName);
			System.out.println("file Name ===="+fileName);
			File file = new File(temDir+fileName);
			uploadfile.transferTo(file);
		}else {
			product.setFileName(null);
		}
		//Business Logic
		productService.InsertProduct(product);
		
		model.addAttribute("product",product);
		
		return "forward:/product/readProduct.jsp";
	}
	
	@RequestMapping( value = "/getProduct")
	public String getProduct(HttpSession session, HttpServletResponse response, HttpServletRequest request, @RequestParam("prodNo") int prodNo, Model model)throws Exception{
		System.out.println("/getProduct SERVICE");
		
		//�ֱٺ���ǰ..
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
	
	@RequestMapping(value = "/listProduct")
	public String listProduct(@RequestParam("menu") String menu, @ModelAttribute("search") Search search, Model model , HttpServletRequest request) throws Exception{
		System.out.println("/listProduct");
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
		
		// Business logic ����
		Map<String , Object> map=productService.getProductList(search);
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		System.out.println("Search==="+search);
		// Model �� View ����
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		
		return "forward:/product/listProduct.jsp";
	}
	
	@RequestMapping(value = "/updateProduct", method=RequestMethod.GET)
	public String updateProductView(@RequestParam("prodNo")int prodNo,Model model ) throws Exception{
		System.out.println("/updateProductView");
		
		Product product = productService.findProduct(prodNo);
		
		model.addAttribute("product", product);
		
		return "forward:/product/UpdateProduct.jsp";
	}
	
	@RequestMapping(value = "/updateProduct", method=RequestMethod.POST)
	public String updateUser( @ModelAttribute("product") Product product , Model model , HttpSession session) throws Exception{

		System.out.println("/updateProduct");
		int prodNo = product.getProdNo();
		System.out.println(prodNo);
		
		productService.updateProduct(product);
		
		return "forward:/product/readProduct.jsp";
	}
	

}