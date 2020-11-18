package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.product.dao.ProductDAO;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.user.dao.UserDAO;
import com.model2.mvc.service.user.vo.UserVO;

public class PurchaseDAO {
	
	public PurchaseDAO() {}
	
	public void InsertPurchase(PurchaseVO purchaseVO)throws Exception{
		System.out.println("insertdao시작");

		String buyer = purchaseVO.getBuyer().getUserId();
		//System.out.println(buyer);
		int prodNo = purchaseVO.getPurchaseProd().getProdNo();
		Connection con = DBUtil.getConnection();
		
		String sql = "INSERT INTO transaction "+
				"values(seq_transaction_tran_no.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?,sysdate,TO_DATE(?,'YYYY-MM-DD'))";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, prodNo);
		stmt.setString(2, buyer);
		stmt.setString(3, purchaseVO.getPaymentOption());
		stmt.setString(4, purchaseVO.getReceiverName());
		stmt.setString(5, purchaseVO.getReceiverPhone());
		stmt.setString(6, purchaseVO.getDivyAddr());
		stmt.setString(7, purchaseVO.getDivyRequest());
		stmt.setString(8, purchaseVO.getTranCode());
		stmt.setString(9, purchaseVO.getDivyDate());
		stmt.executeUpdate();
		
		con.close();
	}
	
	public PurchaseVO findPurchase(int tranNo) throws Exception{
		//Connection con = DBUtil.getConnection();
		
		//String sql = "SELECT * FROM transaction WHERE tranNo=?";
		return null;
	}
	
	public HashMap<String,Object> getPurchaseList(SearchVO searchVO, String buyerId) throws Exception{
		
		Connection con = DBUtil.getConnection();
		
		String sql = "SELECT * FROM transaction WHERE BUYER_ID = ?";
		sql += "order by prod_no";
		
		PreparedStatement stmt = 
				con.prepareStatement(	sql,
										ResultSet.TYPE_SCROLL_INSENSITIVE,
										//커서 이동을 자유롭게 하고 업데이트 내용을 반영하지 않는다.
										ResultSet.CONCUR_UPDATABLE);
										//변경가능하게한다.
			stmt.setString(1, buyerId);
			ResultSet rs = stmt.executeQuery();
			rs.last();
			int total = rs.getRow();
			System.out.println("로우의 수:" + total);
			
			HashMap<String,Object> map = new HashMap<String,Object>();
			map.put("count", new Integer(total));
			
			rs.absolute(searchVO.getPage() * searchVO.getPageUnit() - searchVO.getPageUnit()+1);
			System.out.println("searchVO.getPage():" + searchVO.getPage());
			System.out.println("searchVO.getPageUnit():" + searchVO.getPageUnit());
			ArrayList<PurchaseVO> list = new ArrayList<PurchaseVO>();
			
			if(total > 0) {
				for(int i = 0; i < searchVO.getPageUnit(); i++) {
					PurchaseVO vo = new PurchaseVO();
					//String buyer = vo.getBuyer().getUserId();
					//int prodNo = vo.getPurchaseProd().getProdNo();
					vo.setBuyer(new UserDAO().findUser(rs.getString("BUYER_ID")));
					vo.setDivyAddr(rs.getString("DEMAILADDR"));
					vo.setDivyDate(rs.getString("dlvy_date"));
					vo.setDivyRequest(rs.getString("dlvy_request"));
					vo.setOrderDate(rs.getDate("order_data"));
					vo.setPaymentOption(rs.getString("payment_option"));
					vo.setPurchaseProd(new ProductDAO().findProduct(rs.getInt("prod_no")));
					vo.setReceiverName(rs.getString("receiver_name"));
					vo.setReceiverPhone(rs.getString("receiver_phone"));
					vo.setTranCode(rs.getString("tran_status_code"));
					//vo.setTranCode(rs.getInt("tran_no "));
					
					list.add(vo);
					if(!rs.next())
						break;
				}
			}
			System.out.println("list.size() : "+ list.size());
			map.put("list", list);
			System.out.println("map().size() : "+ map.size());

			con.close();
				
			return map;
	}

}
