<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="org.json.*"%>

<!DOCTYPE html>
<html>
	<%
		String winners = request.getParameter("winners");
	%>
	
	<link rel="stylesheet" type="text/css" href="css/restaurant.css">
	<link rel="stylesheet" type="text/css" href="css/util.css">
	
	<script>
		function loadWinningRestaurant() {
			var winners = JSON.parse('<%=winners%>');
			console.log(winners);
			
			var keys = Object.keys(winners);
			keys.forEach(function(key){
				var array = winners[key];
				
				//General new div.
				var newDiv = document.createElement('div');
				newDiv.className = "card";
				
				//Getting the information and appending them.
				var innerImg = document.createElement('img');
				innerImg.src = array[2];
				innerImg.style = "width:100%";
				newDiv.appendChild(innerImg);
				
				var innerName = document.createElement('div');
				innerName.innerHTML = array[0];
				newDiv.appendChild(innerName);

				var innerRating = document.createElement('p');
				innerRating.innerHTML = array[3];
				newDiv.appendChild(innerRating);

				var innerPrice = document.createElement('p');
				innerPrice.innerHTML = array[4];
				newDiv.appendChild(innerPrice);

				var innerDistance = document.createElement('p');
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
	<h1 id="winner">🤩 Your Group Grub options 🤩  
			<div class="logout"> 
				<button type="button" class = "btn-2" Id="logout" style="Display: initial;">
						<a href="/baby" ><span >Logout</span></a>
				</button>
			</div>
	</h1>
	<div id="winning_restaurants">
	</div>
</body>
</html>