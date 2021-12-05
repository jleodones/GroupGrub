<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html>
	<head>
		<title>peasants</title>
		
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		
		<link rel="stylesheet" type="text/css" href="css/util.css">
		<link rel="stylesheet" type="text/css" href="css/main.css">
		<link rel="stylesheet" type="text/css" href="css/glp.css">
	</head>
	<body style = "background: #800085;">
		<div class = "groupTags">
			<div class = "tagCols"> 
				<h2> Wants </h2>
				
				<div class = "navBar">
					<input type="text" placeholder="Search.." id= "goodTag" style= "float: left;"/>
					<button id="addItem" class = "button">Add</button>
				</div>

				<div class = "tagsWrapper"></div>

				<div class="scrollbar" >
					<ul id="goodList">
						
					</ul>
				</div>
				
			</div><!-- each section has a title, a sticky search bar, and a page slider to slide down the list of tags -->
		</div>

		<script defer>
			window.onload = (() => {
				const byId = id => document.querySelector(`#${id}`); //selects by ID
				const dealTag = byId("goodList"); //creates a list for deals

				const appendListItem = (theList, itemTxt) => { //append list item 
					let listItem = document.createElement("li"); //create a list item 
					listItem.textContent = itemTxt; //list item set to given text
					theList.appendChild(listItem);//adds to the list passed into the method(html)
					
				};

				const addItem = () => {
					const inputGood = byId("goodTag");
					
					const val = inputGood.value.trim();

					if (!val) { return alert("Please enter a valid Yelp tag"); }
					if(goodTags.indexOf(val) === -1){ 
						goodTags.push(val);//fix so that if the tag is good it appears in the good list, else it appears in the bad list
						appendListItem(dealTag, val);
						inputGood.value = "";
					}else{
						return alert("Oops! No duplicates!");
					}
					
				};
				
				// create good tags list
				let goodTags = [];
				goodTags.forEach(item => appendListItem(dealTag, item));
					
				// add button handling
				byId("addItem").addEventListener("click", addItem);
			})();
		</script>
		
	</body>
</html>