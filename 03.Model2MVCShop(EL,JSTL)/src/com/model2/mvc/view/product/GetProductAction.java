package com.model2.mvc.view.product;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

import sun.print.resources.serviceui;

public class GetProductAction extends Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("getproductAction 시작2==============");
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		
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
		
		
		
		
		ProductService service = new ProductServiceImpl();
		ProductVO vo = service.getProduct(prodNo);
		
		request.setAttribute("vo", vo);
		
		
		HttpSession session=request.getSession();
		session.setAttribute("product", vo);				
		
		String authorization = (String)session.getAttribute("menuType");
		System.out.println(authorization);
		if(authorization.equals("manage")) {
			System.out.println("매니지");
			return "forward:/product/UpdateProduct.jsp";
		}else {
			System.out.println("검색");
			return "forward:/product/readProduct.jsp";
		}
	}

}
