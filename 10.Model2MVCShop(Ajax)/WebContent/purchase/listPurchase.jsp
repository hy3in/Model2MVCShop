<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>




<html>
<head>
<title>���� �����ȸ</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">
<script src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
<script type="text/javascript">

	function fncGetPurchaseList(currentPage) {
		document.getElementById("currentPage").value = currentPage;
		document.detailForm.submit();
		
		//document.getElementById("currentPage").value = currentPage;
		$("#currentPage").val(currentPage)
		//document.detailForm.submit();
		$("form").attr("method" , "POST").attr("action" , "/user/listUser").submit();
	}
	
	$(function(){
		
		$( ".ct_list_pop td:nth-child(3)" ).css("color" , "red");
		$("h7").css("color" , "red");
		
		$(".ct_list_pop td:nth-child(1)").on('click',function(){
			var tranNo = $(this).find('div').text().trim();
			$.ajax(
					{
						url : "/purchase/json/getPurchase/"+tranNo,
						method : "GET",
						dataType : "json",
						headers : {
							"Accept" : "application/json",
							"Content-Type" : "application/json"
						},
						success : function(JSONData, status){
							var displayValue = "<h3>"
													+"��ǰ��ȣ : "+JSONData.purchaseProd.prodNo+"<br/>"
													+"�����ھ��̵� : "+JSONData.buyer.userId+"<br/>"
													+"���Ź�� : "+JSONData.paymentOption+"<br/>"
													+"�������̸� : "+JSONData.receiverName+"<br/>"
													+"�����ڿ���ó : "+JSONData.receiverPhone+"<br/>"
													+"�������ּ� : "+JSONData.divyAddr+"<br/>"
													+"���ſ�û���� : "+JSONData.divyRequest+"<br/>"
													+"�������� : "+JSONData.divyDate+"<br/>"
													+"�ֹ��� : "+JSONData.orderDate+"<br/>"
													+"</h3>";
													
							console.log(displayValue);
							$('h3').remove();
							$( '#'+tranNo+'' ).html(displayValue);
						}
					}
					
			)
			
		});
		
		$( ".ct_list_pop td:nth-child(3)" ).on("click" , function() {
			var userId = $(this).text().trim();
			$.ajax( 
					{
						url : "/user/json/getUser/"+userId ,
						method : "GET" ,
						dataType : "json" ,
						headers : {
							"Accept" : "application/json",
							"Content-Type" : "application/json"
						},
						success : function(JSONData , status) {

							//Debug...
							//alert(status);
							//Debug...
							//alert("JSONData : \n"+JSONData);
							
							var displayValue = "<h3>"
														+"���̵� : "+JSONData.userId+"<br/>"
														+"��  �� : "+JSONData.userName+"<br/>"
														+"�̸��� : "+JSONData.email+"<br/>"
														+"ROLE : "+JSONData.role+"<br/>"
														+"����� : "+JSONData.regDate+"<br/>"
														+"</h3>";
							//Debug...									
							//alert(displayValue);
							console.log(displayValue);
							$("h3").remove();
							$( "#"+userId+"" ).html(displayValue);
						}
				});
		});
	});
	
	
</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width: 98%; margin-left: 10px;">

<!--<form name="detailForm" action="/user/listUser" method="post">-->
<form name="detailForm">

<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37"><img src="/images/ct_ttl_img01.gif"width="15" height="37"></td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left: 10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">���� �����ȸ</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37"><img src="/images/ct_ttl_img03.gif"	width="12" height="37"></td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0"	style="margin-top: 10px;">
	<tr>
		<td colspan="11">��ü ${resultPage.totalCount } �Ǽ�, ����  ${resultPage.currentPage } ������</td>
	</tr>
	<tr>
		<td class="ct_list_b" width="100">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">ȸ��ID</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">ȸ����</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">��ȭ��ȣ</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">�����Ȳ</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">��������</td>
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>
	
	
	
	
	
	
		<c:set var="i" value="0"/>
		<c:forEach var="purchase" items="${list}">
		
			<c:set var="i" value="${i+1}"/>
			<tr class="ct_list_pop">
				<td align="center">
					${i}
				<div  style="display:none">${purchase.tranNo}</div>
				</td>
				<td></td>
				<td align="left">
					${purchase.buyer.userId}
				</td>
				<td></td>
				
				<td align="left">${user.userName}</td>
				<td></td>
				
				<td align="left">${user.phone}</td>
				<td></td>
				
				<td align="left">
				
				<c:if test = "${purchase.tranCode eq '1  '}">
					���� ���ſϷ� �����Դϴ�
				</c:if>
				<c:if test = "${purchase.tranCode eq '2  '}">
					���� ����� �����Դϴ�<a href="/purchase/updateTranCode?prodNo=${purchase.purchaseProd.prodNo }&tranCode=3">��ۿϷ�</a>
				</c:if>
				<c:if test = "${purchase.tranCode eq '3  '}">
					��ۿϷ�
				</c:if>
				
				</td>		
		</tr>
		<tr>
		<td id="${purchase.buyer.userId}" id="${purchase.tranNo}" colspan="11" bgcolor="D6D7D6" height="1">
		<td id="${purchase.tranNo}" colspan="11" bgcolor="D6D7D6" height="1"></td>
		</tr>
	</c:forEach>
</table>





<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top: 10px;">
	<tr>
		<td align="center">
		 
			<input type="hidden" id="currentPage" name="currentPage" value=""/>
			
			<c:set var="fnc" value="fncGetPurchaseList" scope="request" /> 
			<c:import var="pageNavi" url="../common/pageNavigator.jsp" scope="request"/>
			 ${pageNavi}	
		
		</td>
	</tr>
</table>

<!--  ������ Navigator �� -->
</form>

</div>

</body>
</html>