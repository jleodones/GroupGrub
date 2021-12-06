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
		String master = request.getParameter("master");
		%>
		
	</head>
	<body style = "background: #AB8CB5;">
		
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
			
			<div id="waiting"></div>
			
			<button type="button" id="finishedButton">
				Finished?
			</button>
			
		</div>
		
		<script defer>
			window.onload = (() => {
				var data;
				var socket;
				var address = "ws://" + window.location.host + "/baby/glp/" + "<%=code%>/<%=username%>/<%=master%>";
				console.log(address);
				socket = new WebSocket(address);
				
				socket.onmessage = function(event) {
					msg = event.data;
                    msg = msg.replace(/(\r\n|\n|\r)/gm,"");
                    
					console.log(msg);
					if(msg == "finished"){
						if(<%=master%> === true){
							socket.send("datapls");
						}
						else{
							moveOn();
						}
 					}
					else if(msg == "wait"){
						document.getElementById("waiting").innerHTML += "Waiting" + "<br/>";
					}
					else{ //Requested data has been received.
						var x = JSON.parse(event.data);
						var array = JSON.stringify(x);
/* 						console.log(x);
						console.log("x URI: " + encodeURIComponent(x));
						console.log("array URI: " + encodeURIComponent(array)); */
						data = encodeURIComponent(array);
/*  						console.log("first data: " + data);
 */						moveOn();
					}
				}
				
				const addGoodTag = (tag) => {
					var message = "1,";
					message += tag;
					socket.send(message);
				}
				
				const addDealbreaker = (tag) => {
					var message = "3,";
					message += tag;
					socket.send(message);
				}
				
				const sendFinish = () => {
					console.log("hang tight!");
					socket.send("done");
				}
				
				const moveOn = () => {
					var url = "../Restaurant_Swiping/RestaurantDisplay.jsp?code=<%=code%>&username=<%=username%>&master=<%=master%>&data=";
					url += data;
/* 					console.log("data again: " + data);
					console.log(url); */
					window.location.href = url;
				}
				
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
						addGoodTag(val);
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
						addDealbreaker(val);
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
				document.querySelector("#finishedButton").addEventListener("click", sendFinish);
			})();
		</script>
		
	</body>
</html>