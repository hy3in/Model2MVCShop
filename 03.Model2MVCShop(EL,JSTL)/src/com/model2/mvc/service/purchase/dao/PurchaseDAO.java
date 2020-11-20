package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.dao.ProductDAO;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.user.dao.UserDao;


public class PurchaseDAO {
	
	public PurchaseDAO() {}
	
	public void InsertPurchase(PurchaseVO purchaseVO)throws Exception{
		System.out.println("insertdao����");

		String buyer = purchaseVO.getBuyer().getUserId();
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
		
		Connection con = DBUtil.getConnection();
		PurchaseVO vo = new PurchaseVO();
		String sql =
				"SELECT "
				+"t.tran_no �ѹ�, p.prod_no ��ǰ��ȣ, u.user_id ����id, t.payment_option ���Ź��, t.receiver_name �����ڸ�, t.receiver_phone �����ڿ���ó, t.demailaddr �������ּ�, t.dlvy_request ��û����, t.dlvy_date �����, t.order_data �ֹ��� " 
				+"FROM users u, product p, transaction t "
				+"WHERE p.prod_no = t.prod_no "
				+"AND t.buyer_id = u.user_id "
				+"AND t.tran_no = ? ";
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setInt(1, tranNo);
		ResultSet rs = pStmt.executeQuery();
		while(rs.next()) {
			vo.setPurchaseProd(new ProductDAO().findProduct(rs.getInt("��ǰ��ȣ")));
			vo.setBuyer(new UserDao().findUser(rs.getString("����id")));
			vo.setPaymentOption(rs.getString("���Ź��"));
			vo.setReceiverName(rs.getString("�����ڸ�"));
			vo.setReceiverPhone(rs.getString("�����ڿ���ó"));
			vo.setDivyAddr(rs.getString("�������ּ�"));
			vo.setDivyRequest(rs.getString("��û����"));
			vo.setDivyDate(rs.getString("�����"));
			vo.setOrderDate(rs.getDate("�ֹ���"));
			vo.setTranNo(rs.getInt("�ѹ�"));
		}
		System.out.println("find Dao vo======"+vo);
		
		rs.close();
		pStmt.close();
		con.close();

		return vo;
	}
	
	public PurchaseVO findPurchase2(int ProdNo) throws Exception{
		
		Connection con = DBUtil.getConnection();
		PurchaseVO vo = new PurchaseVO();
		String sql =
				"SELECT "
				+"t.tran_no �ѹ�, p.prod_no ��ǰ��ȣ, u.user_id ����id, t.payment_option ���Ź��, t.receiver_name �����ڸ�, t.receiver_phone �����ڿ���ó, t.demailaddr �������ּ�, t.dlvy_request ��û����, t.dlvy_date �����, t.order_data �ֹ��� " 
				+"FROM users u, product p, transaction t "
				+"WHERE p.prod_no = t.prod_no "
				+"AND t.buyer_id = u.user_id "
				+"AND t.prod_no = ? ";
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setInt(1, ProdNo);
		ResultSet rs = pStmt.executeQuery();
		while(rs.next()) {
			vo.setPurchaseProd(new ProductDAO().findProduct(rs.getInt("��ǰ��ȣ")));
			vo.setBuyer(new UserDao().findUser(rs.getString("����id")));
			vo.setPaymentOption(rs.getString("���Ź��"));
			vo.setReceiverName(rs.getString("�����ڸ�"));
			vo.setReceiverPhone(rs.getString("�����ڿ���ó"));
			vo.setDivyAddr(rs.getString("�������ּ�"));
			vo.setDivyRequest(rs.getString("��û����"));
			vo.setDivyDate(rs.getString("�����"));
			vo.setOrderDate(rs.getDate("�ֹ���"));
			vo.setTranNo(rs.getInt("�ѹ�"));
		}
		System.out.println("find Dao vo======"+vo);
		
		rs.close();
		pStmt.close();
		con.close();

		return vo;
	}
	
	public Map<String,Object> getPurchaseList(Search search, String buyerId) throws Exception{
		
		
		Map<String , Object>  map = new HashMap<String, Object>();
		Connection con = DBUtil.getConnection();
		
		String sql = "SELECT * FROM transaction WHERE BUYER_ID = ? ORDER BY tran_no ";
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setString(1, buyerId);
		
		System.out.println("UserDAO::Original SQL :: " + sql);
		
		int totalCount = this.getTotalCount(sql, buyerId);
		System.out.println("UserDAO :: totalCount  :: " + totalCount);	
		
		//==> CurrentPage �Խù��� �޵��� Query �ٽñ���
		sql = makeCurrentPageSql(sql, search);
		ResultSet rs = pStmt.executeQuery();
		
		System.out.println(search);
		
		List<PurchaseVO> list = new ArrayList<PurchaseVO>();
		
		while(rs.next()) {
			PurchaseVO vo = new PurchaseVO();
			vo.setBuyer(new UserDao().findUser(rs.getString("BUYER_ID")));
			vo.setDivyAddr(rs.getString("DEMAILADDR"));
			vo.setDivyDate(rs.getString("dlvy_date"));
			vo.setDivyRequest(rs.getString("dlvy_request"));
			vo.setOrderDate(rs.getDate("order_data"));
			vo.setPaymentOption(rs.getString("payment_option"));
			vo.setPurchaseProd(new ProductDAO().findProduct(rs.getInt("prod_no")));
			vo.setReceiverName(rs.getString("receiver_name"));
			vo.setReceiverPhone(rs.getString("receiver_phone"));
			vo.setTranCode(rs.getString("tran_status_code"));
			vo.setTranNo(rs.getInt("tran_no"));
			System.out.println(vo);
			list.add(vo);
		}

		map.put("totalCount", new Integer(totalCount));
		//==> currentPage �� �Խù� ���� ���� List ����
		map.put("list", list);

		rs.close();
		pStmt.close();
		con.close();

		return map;
	}
	public void updatePurchase(PurchaseVO purchaseVO) throws Exception{
		
		Connection con = DBUtil.getConnection();
		
		String sql = "UPDATE transaction "+
		"SET payment_option = ?, receiver_name =?, receiver_phone = ?, demailaddr = ?, dlvy_request = ?, dlvy_date = ?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, purchaseVO.getPaymentOption());
		stmt.setString(2, purchaseVO.getReceiverName());
		stmt.setString(3, purchaseVO.getReceiverPhone());
		stmt.setString(4, purchaseVO.getDivyAddr());
		stmt.setString(5, purchaseVO.getDivyRequest());
		stmt.setString(6, purchaseVO.getDivyDate());
		stmt.executeUpdate();
		
		con.close();
		stmt.close();
	}
	public void updateTranCode(PurchaseVO purchaseVO)throws Exception{
		
		System.out.println("updateTranCode ���� =========");
		Connection con = DBUtil.getConnection();
		System.out.println(purchaseVO);
		String sql = "UPDATE transaction SET tran_status_code = ? WHERE prod_no = ?";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, purchaseVO.getTranCode());
		stmt.setInt(2, purchaseVO.getPurchaseProd().getProdNo());
		System.out.println(purchaseVO.getTranCode());
		System.out.println(purchaseVO.getPurchaseProd().getProdNo());
		int a = stmt.executeUpdate();
		System.out.println(a);
		con.close();
		stmt.close();	
	}
	// �Խ��� Page ó���� ���� ��ü Row(totalCount)  return
	private int getTotalCount(String sql, String buyerId) throws Exception {
		System.out.println("============"+sql);
		sql = "SELECT COUNT(*) "+
		          "FROM ( " +sql+ ") countTable";
		
		Connection con = DBUtil.getConnection();
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setString(1, buyerId);
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
		
		System.out.println("UserDAO :: make SQL :: "+ sql);	
		
		return sql;
	}
	

}
