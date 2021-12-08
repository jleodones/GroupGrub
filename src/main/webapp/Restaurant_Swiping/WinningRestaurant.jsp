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

		    //Change logout button visibility.
			if(sessionStorage.username == "Guest"){
				document.getElementById("logout").style.visibility = "hidden";
			}
		    
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
				
				var innerName = document.createElement('p');
				innerName.innerHTML = "Name: " + array[0];
				newDiv.appendChild(innerName);

				var innerRating = document.createElement('p');
				var rating = array[3];
				if(rating === null || rating === "undefined"){
					rating = "Rating could not be found!";
				}
				innerRating.innerHTML = "Rating: " + rating;
				newDiv.appendChild(innerRating);

				var innerPrice = document.createElement('p');
				var price = array[4];
				if(price === null || price === "undefined"){
					price = "Price could not be found!";
				}
				innerPrice.innerHTML = "Price: " + price;
				newDiv.appendChild(innerPrice);

				var innerDistance = document.createElement('p');
				var miles = array[5];
				if(miles === null || miles === "undefined"){
					miles = "Distance could not be found!";
				}
				else{
					miles = array[5] * 0.000621371192;
					miles = Math.trunc(miles);
					miles += " miles";
				}
	           	innerDistance.innerHTML = "Distance: " + miles;
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