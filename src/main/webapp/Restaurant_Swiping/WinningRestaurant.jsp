<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="org.json.*"%>
<!DOCTYPE html>
<html>
	<%
		String winners = request.getParameter("winner");
	%>
	<script language="javascript">
<%-- 		var winningRestaurant = "";
		function loadWinningRestaurant() {
			document.getElementById("winning_restaurant").innerHTML = "<%=winner%>"; --%>
			
			var winners = JSON.parse(<%=winners%>);
			winners.forEach
		}
		
	</script>
<head>
<meta charset="UTF-8">
<title>Winning Restaurant</title>
</head>
<body onload="loadWinningRestaurant()">
	<h1>The Winner is: </h1>
	<div id="winning_restaurant">
	</div>
</body>
</html>