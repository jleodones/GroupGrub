<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<%  String winner = request.getParameter("winner");
		System.out.println(winner);
	%>
	<script language="javascript">
		var winningRestaurant = "";
		
		
		function loadWinningRestaurant() {
			document.getElementById("winning_restaurant").innerHTML = "<%=winner%>";
		}
		
	</script>
<head>
<meta charset="UTF-8">
<title>Winning Restaurant</title>
</head>
<body onload="loadWinningRestaurant()">
	<h1>The Winner is: </h1>
	<p id="winning_restaurant">
	</p>
</body>
</html>