<%-- 
    Document   : managePlants
    Created on : Mar 14, 2023, 3:41:20 PM
    Author     : VietAnhOdyssey
--%>

<%@page import="DTO.Plant"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Manage Plants</h1>
        <a href="MainController?action=viewPlants">View All Plants</a>
        <a href="MainController?action=addPlant">Add a new plant </a>        
        <%
            //Only shows the appropriate menu when the user clicks on one of the 
            //above hyperlinks. When doing so, a parameter called action with values
            //within one of these (viewPlants, addPlant,updatePlant) will be created.
            String option = (String)request.getParameter("action");
            if(option == null){
                %> <h2>Please choose one of the above options.</h2><%
            
            }else if(option.equals("viewPlants")){
                ArrayList<Plant> plantList = (ArrayList<Plant>)request.getAttribute("plantList");
                if(plantList!=null){
                    %>
                    <table>
                        <tr>
                            <th>Name</th>
                            <th>Price</th>
                            <th>Image</th>
                            <th>Status</th>
                            <th>Category</th>
                            <th>Description</th>       
                            <th>Action</th>
                            
                        </tr>
                        <%
                        for(Plant eachPlant:plantList){                        
                            
                            %>
                            <tr>
                                <td><%=eachPlant.getName()%></td>
                                <td><%=eachPlant.getPrice()%></td>
                                <td><%=eachPlant.getImgpath()%></td>
                                <td><%=eachPlant.getStatus()%></td>
                                <td><%=eachPlant.getCatename()%></td>
                                <td><%=eachPlant.getDescription()%></td>
                                <td><a href="MainController?action=updatePlant&plantId=<%=eachPlant.getId()%>">
                                        Update Details</a></td>
                                
                            </tr>
                            <%
                        }
                        %>
                    </table>
                     <%
                }
            }else if(option.equals("addPlant")){
                %>
                <h2>Enter details for the new plant</h2>
                <form action="MainController">
                    <h1>Name</h1> <input type="text" name="newName">
                    <h1>Price</h1> <input type="text" name="newPrice">
                    <h1>Image</h1> <input type="text" name="newImg">
                    <h1>Description</h1> <input type="text" name="newDesc">
                    <h1>Status</h1> <input type="text" name="newStatus">
                    <h1>Category</h1>
                    <select name="newCategory">
                        <option value="1">Orchid</option>
                        <option value="2">Roses</option>
                        <option value="3">Others</option>
                    </select>
                    <input type="hidden" name="isFromAddForm" value="true">
                    <input type="submit" name="action" value="addPlant">
                </form>
                
                <%
            
            }else{
                int plantId = Integer.parseInt(request.getParameter("plantId"));
                %>
                <h2>Enter details for plant <%=plantId%></h2>
                <form action="MainController">
                    <h1>Name</h1> <input type="text" name="newName">
                    <h1>Price</h1> <input type="text" name="newPrice">
                    <h1>Image</h1> <input type="text" name="newImg">
                    <h1>Description</h1> <input type="text" name="newDesc">
                    <h1>Status</h1> 
                    <select name="newStatus">
                        <option value="1">Available</option>
                        <option value="0">Con cai nit</option>                        
                    </select>                   
                    <h1>Category</h1>
                    <select name="newCategory">
                        <option value="1">Orchid</option>
                        <option value="2">Roses</option>
                        <option value="3">Others</option>
                    </select>
                    <input type="hidden" name="isFromUpdateForm" value="true">
                    <input type="hidden" name="plantId" value=<%=plantId%>>
                    <input type="submit" name="action" value="updatePlant">
                </form>
                
                <%
            }
        
        %>
    </body>
</html>
