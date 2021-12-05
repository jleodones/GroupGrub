<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<script language="javascript">
		var winningRestaurant = "";
		
		function loadPage() {
			winningRestaurant = localStorage.getItem("winningRestaurant");
			localStorage.removeItem("winningRestaurant");
		}
		
		function loadWinningRestaurant() {
			document.getElementById("winning_restaurant").innerHTML = winningRestaurant;
		}
		
	</script>
<head>
<meta charset="UTF-8">
<title>Winning Restaurant</title>
</head>
<body onload="loadPage()">
	<h1>The Winner of the CUM award is: </h1>
	<p id="winning_restaurant" onload="loadWinningRestaurant()"></p>
</body>
</html>