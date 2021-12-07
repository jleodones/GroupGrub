<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="org.json.*"%>
<!DOCTYPE html>
<html>
	<%
		String winners = request.getParameter("winners");
	%>
	<script language="javascript">
/* 		var winningRestaurant = "";
 */		function loadWinningRestaurant() {
<%-- 			document.getElementById("winning_restaurant").innerHTML = "<%=winner%>"; --%>		
			var winners = JSON.parse('<%=winners%>');
			console.log(winners);
			
			var keys = Object.keys(winners);
			keys.forEach(function(key){
				var array = winners[key];
				
				//General new div.
				var newDiv = document.createElement('div');
				
				//Getting the information and appending them.
				var innerName = document.createElement('div');
				innerName.innerHTML = array[0];
				newDiv.appendChild(innerName);
				
				var innerImg = document.createElement('div');
				innerImg.innerHTML = array[2];
				newDiv.appendChild(innerImg);

				var innerRating = document.createElement('div');
				innerRating.innerHTML = array[3];
				newDiv.appendChild(innerRating);

				var innerPrice = document.createElement('div');
				innerPrice.innerHTML = array[4];
				newDiv.appendChild(innerPrice);

				var innerDistance = document.createElement('div');
				innerDistance.innerHTML = array[5];
				newDiv.appendChild(innerDistance);

				//Finally, append the new div to the actual document.
				document.getElementById("winning_restaurants").appendChild(newDiv);
				
			});
		}
		
	</script>
<head>
<meta charset="UTF-8">
<title>Winning Restaurant</title>
</head>
<body onload="loadWinningRestaurant()">
	<h1>The Winner is: </h1>
	<div id="winning_restaurants">
	</div>
</body>
</html>