<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html>
	<head>
		<title>Elite</title>
		
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		
		<link rel="stylesheet" type="text/css" href="css/util.css">
		<link rel="stylesheet" type="text/css" href="css/main.css">
		<link rel="stylesheet" type="text/css" href="css/glp.css">
		
		<%
		String code = request.getParameter("code");
		String username = request.getParameter("username");
		String m = request.getParameter("m");
		%>
			
	</head>
	<body style = "background: #AB8CB5;" onload="onLoad();">
		
		<div class = "groupTags">
			<div class = "tagCols" style= "float: left;"> 
				<h2> Want </h2>
				
				<div class = "navBar">
					<input id= "goodTag" type="text" placeholder="Search.." style= "float: left;"/>
					<button id="addGItem" class = "button">AddGood</button>
				</div>

				<div class = "tagsWrapper"></div>

				<div class="scrollbar" >
					<ol id="goodList">
						
					</ol>
				</div>
				
			</div><!-- each section has a title, a sticky search bar, and a page slider to slide down the list of tags -->
			
			<div class = "tagCols" style = "float: right;">
				<h2> No Want </h2>
				<div class = "navBar">
					<input id= "badTag" type="text" placeholder="Search.." style= "float: left;"/>
					<button id="addBItem" class = "button">AddBad</button>
				</div>
				
				<div class = "tagsWrapper">
				
				</div>
				
				<div class="scrollbar">
					<ol id="badList">
						
					</ol>
				</div>
			</div>
		</div>
		
		<script defer>
			window.onload = (() => {
				var socket;
				var address = "ws://" + window.location.host + "/baby/glp/" + "<%=code%>" + "/<%=username%>";
				console.log(address);
				socket = new WebSocket(address);
				
				const addGoodTag = (tag) => {
					var message = "1,";
					message += tag;
					socket.send(message);
				}
				
				const addDealbreaker = (tag) =>{
					var message = "3,";
					message += tag;
					socket.send(message);
				}
				
				//const document.querySelector = id => document.querySelector(`#${id}`); //selects by ID
				const dealTag = document.querySelector("#goodList"); //creates a list for deals
				const nodealTag = document.querySelector("#badList"); //creates a list const for noDeals

				const appendListItem = (theList, itemTxt) => { //append list item 
					let listItem = document.createElement("li"); //create a list item 
					listItem.textContent = itemTxt; //list item set to given text
					theList.appendChild(listItem);//adds to the list passed into the method(html)
				};

				const addGoodItem = () => { //adds items to list object
					const inputGood = document.querySelector("#goodTag");
					
					const val = inputGood.value.trim();

					if (!val) { return alert("Please enter a good Yelp tag"); }

					if(goodTags.indexOf(val) === -1){ 
						goodTags.push(val);//fix so that if the tag is good it appears in the good list, else it appears in the bad list
						appendListItem(dealTag, val);
						inputGood.value = "";
					}else{
						return alert("Oops! No duplicates!");
					}
				};

				const addBadItem = () => { //adds items to list object
					const inputBad = document.querySelector("#badTag");
					
					const val = inputBad.value.trim();

					if (!val) { return alert("Please enter a bad Yelp tag"); }

					if(badTags.indexOf(val) === -1){ 
						badTags.push(val);//fix so that if the tag is good it appears in the good list, else it appears in the bad list
						appendListItem(nodealTag, val);
						inputBad.value = "";
					}else{
						return alert("Oops! No duplicates!");//if there is a duplicate tag it will not be added
					}
				};
				
				// create good tags list
				let goodTags = [];
				goodTags.forEach(item => appendListItem(dealTag, item));

				let badTags = [];
				badTags.forEach(item => appendListItem(nodealTag, item));
					
				// add button handling
				document.querySelector("#addGItem").addEventListener("click", addGoodItem);
				document.querySelector("#addBItem").addEventListener("click", addBadItem);
			})();
		</script>
		
	</body>
</html>