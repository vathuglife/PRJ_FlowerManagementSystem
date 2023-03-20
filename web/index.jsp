<%-- 
    Document   : index
    Created on : Feb 4, 2023, 11:24:52 AM
    Author     : VietAnhOdyssey
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@page import="java.util.Iterator"%>
<%@ page import="DAO.PlantDAO" %>
<%@ page import="DTO.Plant" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Floral Flower</title>
<link href="https://fonts.googleapis.com/css2?family=Ephesis&display=swap" rel="stylesheet"> 
<link href="css/index.css" rel="stylesheet">


</head>

<body>

	
	 <!---------------------------------------------------------------------------------------------->
	   
		 <div class="menu">

			<div id="logo">
				<img>
			</div>			

			<form id="search-bar" action="MainController">
                            <input type="text" name="search-bar">			
                            <select name="search-option">
                                    <option value="name">By Name</option>
                                    <option value="category">By Category</option>					
                            </select>
                            <input type="submit" name="action" value="search">
			</form>
			<div id="menu-options-container">			
				<ul>
					<li class="menu-options" >
						<a href="login.html">Login</a>
					</li>
					<li class="menu-options">
						<a>
							<a href="register.html">Register</a>
						</a>
					</li>
					<li class="menu-options">
						<a>
							<a href="personalPage.jsp">User Page</a>
						</a>
					</li>
					<li class="menu-options">
						<a>
							<a href="MainController?action=viewCart">View Cart</a>
						</a>
					</li>
				</ul>								
			</div>

		 </div>
	 
	 
	 		

		 <div style="clear: both;"></div>

			 <div id="best-seller-container">

                            <h1 id="best-seller-title">BEST SELLER</h1>

                            <% 
                                ArrayList<Plant> plantList = PlantDAO.getPlants("","");
                                Iterator<Plant> plantListIterator = plantList.iterator();
                                while(plantListIterator.hasNext()){
                                    Plant eachPlant = plantListIterator.next();
                                    int eachPlantId = eachPlant.getId();
                                %>
                                      <div class="best-seller-item">

                                       <img src="<%=eachPlant.getImgpath()%>"  class="best-seller-img"/>
                                       <h3 class="flower-name"><%=eachPlant.getName()%></h3>
                                       <p class="price"><%=eachPlant.getPrice()%></p>                                       
                                       <div>
                                           <a href="MainController?action=addToCart&plantID=<%=eachPlant.getId()%>">Add to cart</a>                                            
                                       </div>
                                       <div>
                                           <a href="MainController?action=showPlantDetails&plantId=<%=eachPlantId%>">View Details</a>
                                       </div>
                                </div>
                                <%
                                }
                            
                            %>                               
                                      <div style="clear: both;"></div>

			</div><!--Kết thúc listflower-->

			<div id="contact-info-container">

					<p>Email : FloralFlower.com</p>
					<p>Address: 100 To Hien Thanh, District 10, HCMC</p>
					<h5>&copy; Copyright 2021. FloralFlower.com</h5>

			</div>
	
	 
	</div><!--Kết thúc container-->
	<script src="mycode.js"></script>
</body>
</html>
