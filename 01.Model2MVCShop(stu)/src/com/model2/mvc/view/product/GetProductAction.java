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
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		
		Cookie[] cookies = request.getCookies();
		String sumValue = request.getParameter("prodNo")+",";
		
		for(int i =0; i<cookies.length;i++) {
			if(cookies[i].getName().equals("history")) {
				sumValue = cookies[i].getValue()+request.getParameter("prodNo")+",";
				System.out.println("쿠키가 널이 아님"+sumValue);	
			}
		}	
		
		System.out.println("sumValue가 빈칸인지아닌지");
		Cookie cookie = new Cookie("history", sumValue);
		cookie.setMaxAge(60*1);
		response.addCookie(cookie);
		
		
		
		
		ProductService service = new ProductServiceImpl();
		ProductVO vo = service.getProduct(prodNo);
		
		request.setAttribute("vo", vo);
		
		
		HttpSession session=request.getSession();
		session.setAttribute("product", vo);
		
		System.out.println(session.getAttribute("product"));
		
		
		System.out.println(vo.getProTranCode());
		
		return "forward:/product/GetProduct.jsp";
		//주소 맞는지 확인
	}

}
