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
		<script type="text/javascript" src="js/mouse.js"></script>
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
		</div>

		<script defer type="text/javascript" src="js/lists.js">
			
		</script>
	</body>
</html>