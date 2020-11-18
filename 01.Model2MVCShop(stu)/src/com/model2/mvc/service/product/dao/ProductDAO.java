package com.model2.mvc.service.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.product.vo.ProductVO;

public class ProductDAO {
	
	public ProductDAO() {}
	
	public void InsertProduct(ProductVO productVO) throws Exception{
		System.out.println("ProductDAO접근");
		Connection con = DBUtil.getConnection();
		//prod_no / prod_name / prod_detale
		//manufacture_day /price / image_file /reg_date
		//쿼리 맞는지 확인
		String sql = "INSERT into product values(seq_product_prod_no.NEXTVAL, ?, ?, ?, ?, ?, sysdate)";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, productVO.getProdName());
		stmt.setString(2, productVO.getProdDetail());
		stmt.setString(3, productVO.getManuDate());
		stmt.setInt(4, productVO.getPrice());
		stmt.setString(5, productVO.getFileName());
		stmt.executeUpdate();
		
		con.close();
	}
	
	public HashMap<String,Object> getProductList(SearchVO searchVO) throws Exception {
		
		Connection con = DBUtil.getConnection();
		
		String sql = "SELECT * FROM product";
		//String sql2 = "SELECT tran_status_code FROM transaction t, product p WHERE t.prod_no = p.prod_no";
		if(searchVO.getSearchCondition()!=null) {
			if(searchVO.getSearchCondition().equals("0")) {
				//상품번호
				sql += " WHERE prod_no LIKE '%" + searchVO.getSearchKeyword()
				+ "%'";
			}
			if(searchVO.getSearchCondition().equals("1")) {
				//상품명
				sql += " WHERE prod_name LIKE '%" + searchVO.getSearchKeyword()
				+ "%'";
			}
			if(searchVO.getSearchCondition().equals("2")) {
				//상품가격
				sql += " WHERE price =" + searchVO.getSearchKeyword();
			}
		}
		sql += " order by prod_no";
		
		PreparedStatement stmt = 
			con.prepareStatement(	sql,
														ResultSet.TYPE_SCROLL_INSENSITIVE,
														//커서 이동을 자유롭게 하고 업데이트 내용을 반영하지 않는다.
														ResultSet.CONCUR_UPDATABLE);
														//변경가능하게한다.
		ResultSet rs = stmt.executeQuery();
		rs.last();
		int total = rs.getRow();
		System.out.println("로우의 수:" + total);

		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("count", new Integer(total));
		
		rs.absolute(searchVO.getPage() * searchVO.getPageUnit() - searchVO.getPageUnit()+1);
		System.out.println("searchVO.getPage():" + searchVO.getPage());
		System.out.println("searchVO.getPageUnit():" + searchVO.getPageUnit());
		
		ArrayList<ProductVO> list = new ArrayList<ProductVO>();
		if (total > 0) {
			for (int i = 0; i < searchVO.getPageUnit(); i++) {
				ProductVO vo = new ProductVO();
				vo.setFileName(rs.getString("IMAGE_FILE"));
				vo.setManuDate(rs.getString("MANUFACTURE_DAY"));
				vo.setPrice(rs.getInt("PRICE"));
				vo.setProdDetail(rs.getString("PROD_DETAIL"));
				vo.setProdName(rs.getString("PROD_NAME"));
				vo.setProdNo(rs.getInt("PROD_NO"));
				vo.setRegDate(rs.getDate("REG_DATE"));
				
				
				list.add(vo);
				if (!rs.next())
					break;
				
			}	
		}
		System.out.println("list.size() : "+ list.size());
		map.put("list", list);
		System.out.println("map().size() : "+ map.size());

		con.close();
			
		return map;	
	}
	
	public ProductVO findProduct(int prodNo) throws Exception{
		System.out.println("findProduct 시작========");
		Connection con = DBUtil.getConnection();
		
		String sql1 = "SELECT * FROM product WHERE prod_No=?";
		
		PreparedStatement stmt1 = con.prepareStatement(sql1);
		stmt1.setInt(1, prodNo);
		
		ResultSet rs1 = stmt1.executeQuery();

		
		ProductVO productVO = new ProductVO();
		while (rs1.next()) {
			productVO.setFileName(rs1.getString("IMAGE_FILE"));
			productVO.setManuDate(rs1.getString("MANUFACTURE_DAY"));
			productVO.setPrice(rs1.getInt("PRICE"));
			productVO.setProdDetail(rs1.getString("PROD_DETAIL"));
			productVO.setProdName(rs1.getString("PROD_NAME"));
			productVO.setProdNo(rs1.getInt("PROD_NO"));
			productVO.setRegDate(rs1.getDate("REG_DATE"));
		}
		
		System.out.println(productVO.getProTranCode());
		System.out.println("findProduct 끝========");
		con.close();
		return productVO;
	}
	
	public void updateProduct(ProductVO productVO) throws Exception{
		
		Connection con = DBUtil.getConnection();
		
		String sql = "UPDATE product SET PROD_NAME = ?, PROD_DETAIL = ?, MANUFACTURE_DAY = ?, PRICE = ?, IMAGE_FILE = ? WHERE prod_no =?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, productVO.getProdName());
		stmt.setString(2, productVO.getProdDetail());
		stmt.setString(3, productVO.getManuDate());
		stmt.setInt(4, productVO.getPrice());
		stmt.setString(5, productVO.getFileName());
		stmt.setInt(6, productVO.getProdNo());
		stmt.executeUpdate();
		
		con.close();
	}

}
