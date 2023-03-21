<%-- 
    Document   : index
    Created on : Feb 4, 2023, 11:24:52 AM
    Author     : VietAnhOdyssey
--%>
<!--Library and Variable declaration goes here-->
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="DTO.Plant"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%ArrayList<Plant> result = (ArrayList)request.getAttribute("plantResultList");%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Floral Flower</title>
<link href="https://fonts.googleapis.com/css2?family=Ephesis&display=swap" rel="stylesheet"> 
<link href="css/searchresult.css" rel="stylesheet">


</head>

<body>

	
	 <!---------------------------------------------------------------------------------------------->
	 <div class="menu">

		<div id="logo">
			<img>
		</div>			

		<form id="search-bar" action="SearchServlet">
			<input type="text" name="search-bar">			
			<select name="search-option">
				<option value="name">By Name</option>
				<option value="category">By Category</option>					
			</select>
			<button type="submit"> Search</button>
		</form>
		<div id="menu-options-container">
			<ul>
                            <li class="menu-options"><a href="login.html">Login</a></li>
                            <li class="menu-options"><a href="register.html">Register</a></li>
                                <li class="menu-options"> <a href="index.jsp">Back to home</a></li>
			</ul>
		</div>

	 </div>
	 	 
	 
	  <table id="result-table">
		<tr>
		  <th>Image</th>
		  <th>Id</th>
		  <th>Name</th>
                  <th>Price</th>
                  <th>Description</th>
                  <th>Status</th>
                  <th>Category</th>
		</tr>                
	  
	<%
            if(result!=null){
                Iterator<Plant> resultIterator= result.iterator();                
                while(resultIterator.hasNext()){
                    Plant eachPlant = resultIterator.next();
        %>
        
                    <tr>
                        <td><img class="table-img"src="<%=eachPlant.getImgpath()%>"/></td>
                        <td><%=eachPlant.getId()%></td>
                        <td><%=eachPlant.getName()%></td>
                        <td><%=eachPlant.getPrice()%></td>
                        
                        <td><%=eachPlant.getDescription()%></td>
                        <td><%=eachPlant.getStatus()%></td>
                        <td><%=eachPlant.getCatename()%></td>
                    </tr>
         <%
                }                        
            }
        %>
	</table>
	</div><!--Kết thúc container-->
	<script src="mycode.js"></script>
</body>
</html>
