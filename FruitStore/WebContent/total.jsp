<%@page import="java.time.LocalDateTime"%>
<%@page import="java.sql.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.util.List"%>
<%@page import="kr.edu.mit.SalesVO"%>
<%@page import="kr.edu.mit.FruitStoreDAOImpl"%>
<%@page import="kr.edu.mit.FruitStoreDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>매출확인</title>
<style>
.totalPrice {
 	 animation: blink 0.5s ease-in-out infinite alternate;
}

@keyframes blink{
  0% {opacity:0;}
  100% {opacity:1;}
}
</style>
</head>
<body>
<%
	FruitStoreDAO dao= new FruitStoreDAOImpl();
	List<SalesVO> list= dao.listSales();
%>


<table border="1">
	<caption><h4>[판매목록]</h4></caption>
	<tr> <th> 과일코드 </th> <th> 과일이름 </th> <th> 판매수량 </th> <th> 판매금액 </th> <th> 판매일자 </th>
	</tr>
<%	
	SimpleDateFormat dateFm = new SimpleDateFormat("yyyy/MM/dd a hh:mm:ss");
	DecimalFormat df = new DecimalFormat("###,###,###,###");

	for(SalesVO vo: list){%>
	  <tr align="center">
		  <td><%=vo.getFruit_code()%></td>
		  <td><%=vo.getFruit_name()%></td>
		  <td><%=vo.getSales_quantity()%></td>
		  <td><%=df.format(vo.getTotal())%> 원</td>
		  <td><%=dateFm.format(vo.getSales_date())%></td>
		  
	</tr>
<%	}
	
%>	
	<tr> <td colspan="5" align="right"><b class="totalPrice">총 매출액: <%=df.format(dao.totalprice())%> 원</b></td>
	</tr>
	<tr> <td colspan="5" align="right"><button type="button" onclick="gotitle()" >메뉴로 돌아가기</button></td>
	</tr>
</table>

<script>
function gotitle() {
	history.back(); //뒤로가기
}	
</script>

</body>
</html>