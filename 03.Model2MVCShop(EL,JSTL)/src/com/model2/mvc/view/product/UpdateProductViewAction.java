package com.model2.mvc.view.product;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.common.Search;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;


public class UpdateProductViewAction extends Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("UpdateProdductViewAction시작========");
		System.out.println(request.getParameter("prodNo"));
		int prodNo= Integer.parseInt(request.getParameter("prodNo"));
		System.out.println("prod no 가져옴"+prodNo);

		ProductService service=new ProductServiceImpl();
		ProductVO productVO=service.getProduct(prodNo);
		
		request.setAttribute("productVO", productVO);
		System.out.println("UpdateProductViewAction 끝==========");
		return "forward:/product/UpdateProduct.jsp";
	}
}
