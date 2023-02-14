<%@page import="java.text.DecimalFormat"%>
<%@page import="kr.edu.mit.FruitVO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="kr.edu.mit.FruitStoreDAOImpl"%>
<%@page import="kr.edu.mit.FruitStoreDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>재고파악</title>
</head>
<body>
<%
	FruitStoreDAO dao= new FruitStoreDAOImpl();
	ArrayList<FruitVO> list= dao.listFruit();
%>


<table border="1">
	<caption><h4>[재고목록]</h4></caption>
	<tr> <th>과일코드</th> <th>이름</th> <th>가격</th> <th>재고</th>
	</tr>
<%
	DecimalFormat df = new DecimalFormat("###,###,###,###");
	for(FruitVO vo: list){%>
		<tr align="center"> 
			<td><%=vo.getFruit_code()%></td>
			<td><%=vo.getFruit_name()%></td>
			<td><%=df.format(vo.getPrice())%> 원</td>
			<td><%=vo.getQuantity()%></td>
		</tr>
<%	}
%>	
	<tr>
		<td colspan="4" align="right"><button type="button" onclick="gotitle()">메뉴로 돌아가기</button></td>
	</tr>
</table>
<br>


<script>
function gotitle() {
	location.href="index.html";
}
</script>
</body>
</html>