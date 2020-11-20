package com.model2.mvc.service.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.product.vo.ProductVO;

public class ProductDAO {
	
	public ProductDAO() {}
	
	public void InsertProduct(ProductVO productVO) throws Exception{
		System.out.println("ProductDAO����");
		Connection con = DBUtil.getConnection();
		//prod_no / prod_name / prod_detale
		//manufacture_day /price / image_file /reg_date
		//���� �´��� Ȯ��
		String sql = "INSERT into product values(seq_product_prod_no.NEXTVAL, ?, ?, ?, ?, ?, sysdate)";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, productVO.getProdName());
		stmt.setString(2, productVO.getProdDetail());
		stmt.setString(3, productVO.getManuDate());
		stmt.setInt(4, productVO.getPrice());
		stmt.setString(5, productVO.getFileName());
		stmt.executeUpdate();
		
		stmt.close();
		con.close();
	}
	
	public Map<String, Object> getProductList(Search search) throws Exception {
		
		HashMap<String, Object> map = new HashMap<String,Object>();
		
		Connection con = DBUtil.getConnection();
		
		String sql = "SELECT p.*, t.tran_status_code �ڵ� FROM product p, transaction t WHERE p.prod_no = t.prod_no(+)";
		//String sql2 = "SELECT tran_status_code FROM transaction t, product p WHERE t.prod_no = p.prod_no";
		if(search.getSearchCondition()!=null) {
			if(search.getSearchCondition().equals("0")&&search.getSearchCondition()=="") {
				//��ǰ��ȣ
				sql += " AND prod_no LIKE '%" + search.getSearchKeyword()
				+ "%'";
			}
			if(search.getSearchCondition().equals("1")) {
				//��ǰ��
				sql += " AND prod_name LIKE '%" + search.getSearchKeyword()
				+ "%'";
			}
			if(search.getSearchCondition().equals("2")) {
				//��ǰ����
				sql += " AND price =" + search.getSearchKeyword();
			}
		}
		
		
		if(search.getSort()==1) {
			sql += " ORDER BY p.price DESC";
		}else if(search.getSort()==2){
			sql += " ORDER BY p.price";
		}else {
			sql += " order by p.prod_no";
		}
		
		System.out.println("UserDAO::Original SQL :: " + sql);
		
		//==> TotalCount GET
		int totalCount = this.getTotalCount(sql);
		System.out.println("UserDAO :: totalCount  :: " + totalCount);
		
		sql = makeCurrentPageSql(sql, search);
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
		
		System.out.println(search);
		
		List<ProductVO> list = new ArrayList<ProductVO>();
		
		
		while(rs.next()) {
			System.out.println("rs.next ��������..================");
			ProductVO vo = new ProductVO();
			vo.setFileName(rs.getString("IMAGE_FILE"));
			vo.setManuDate(rs.getString("MANUFACTURE_DAY"));
			vo.setPrice(rs.getInt("PRICE"));
			vo.setProdDetail(rs.getString("PROD_DETAIL"));
			vo.setProdName(rs.getString("PROD_NAME"));
			vo.setProdNo(rs.getInt("PROD_NO"));
			vo.setRegDate(rs.getDate("REG_DATE"));
			vo.setProTranCode(rs.getString("�ڵ�"));
			System.out.println(rs.getString("�ڵ�"));
			list.add(vo);
		}	
		
		//==> totalCount ���� ����
		map.put("totalCount", new Integer(totalCount));
		//==> currentPage �� �Խù� ���� ���� List ����
		map.put("list", list);
		
		rs.close();
		pStmt.close();
		con.close();

		return map;
	}
	
	public ProductVO findProduct(int prodNo) throws Exception{
		System.out.println("findProduct ����========");
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
		
		System.out.println("findProduct ��========");
		con.close();
		rs1.close();
		stmt1.close();
		
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
		stmt.close();
	}
	
	// �Խ��� Page ó���� ���� ��ü Row(totalCount)  return
	private int getTotalCount(String sql) throws Exception {
		
		sql = "SELECT COUNT(*) "+
		          "FROM ( " +sql+ ") countTable";
		
		Connection con = DBUtil.getConnection();
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
		
		int totalCount = 0;
		if( rs.next() ){
			totalCount = rs.getInt(1);
		}
		
		pStmt.close();
		con.close();
		rs.close();
		
		return totalCount;
	}
	
	// �Խ��� currentPage Row ��  return 
	private String makeCurrentPageSql(String sql , Search search){
		sql = 	"SELECT * "+ 
					"FROM (		SELECT inner_table. * ,  ROWNUM AS row_seq " +
									" 	FROM (	"+sql+" ) inner_table "+
									"	WHERE ROWNUM <="+search.getCurrentPage()*search.getPageSize()+" ) " +
					"WHERE row_seq BETWEEN "+((search.getCurrentPage()-1)*search.getPageSize()+1) +" AND "+search.getCurrentPage()*search.getPageSize();
		
		System.out.println("ProductDAO :: make SQL :: "+ sql);	
		
		return sql;
	}

}
