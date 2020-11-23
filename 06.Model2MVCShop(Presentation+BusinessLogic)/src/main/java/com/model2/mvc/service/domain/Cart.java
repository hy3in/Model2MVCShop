package com.model2.mvc.service.domain;

public class Cart {
	
	private int cartNo;
	private String userId;
	private Product product;
	
	
	public int getCartNo() {
		return cartNo;
	}
	public void setCartNo(int cartNo) {
		this.cartNo = cartNo;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	
	@Override
	public String toString() {
		return "Cart [cartNo=" + cartNo + ", userId=" + userId + ", product=" + product + "]";
	}

}
