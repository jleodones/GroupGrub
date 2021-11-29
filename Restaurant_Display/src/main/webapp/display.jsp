<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="tagging.*"%>
<%@page import="java.util.*" %>
<%@page import="javax.servlet.*" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Restaurant Display</title>
	</head>
	<body>	
		<%
			//want tags
			ArrayList<String> want = new ArrayList<String>();
			want.add("hamburgers");
			
			//dealbreaker tags
			ArrayList<String> noWant = new ArrayList<String>();
			noWant.add("fish");
			
			//Create tagger and get restaurant list
			Tagger tag = new Tagger(want, noWant);
			ArrayList<Restaurant> choices = tag.finalRestaurants();
			
			Restaurant first = choices.get(0);
			
			out.println(first.toString());
		%>
	</body>
	
	
</html>