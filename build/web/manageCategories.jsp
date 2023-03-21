<%-- 
    Document   : managePlants
    Created on : Mar 14, 2023, 3:41:20 PM
    Author     : VietAnhOdyssey
--%>

<%@page import="DTO.Category"%>
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
        <h1>Manage Categories</h1>
        <a href="MainController?action=viewCategories">View All Categories</a>
        <a href="MainController?action=addCategory">Add a new category </a>           
        <a href="admin.jsp">Back to admin page</a>
        <%
            //Only shows the appropriate menu when the user clicks on one of the 
            //above hyperlinks. When doing so, a parameter called action with values
            //within one of these (viewPlants, addPlant,updatePlant) will be created.
            String option = (String)request.getParameter("action");
            if(option == null){
                %> <h2>Please choose one of the above options.</h2><%
            
            }else if(option.equals("viewCategories")){
                ArrayList<Category> cateList = (ArrayList<Category>)request.getAttribute("cateList");
                if(cateList!=null){
                    %>
                    <table>
                        <tr>
                            <th>Name</th>
                            <th>New name</th>
                            <th>Action</th>                                                        
                        </tr>
                        <%
                        for(Category eachCate:cateList){                        
                            
                            %>
                            <tr>
                                <td><%=eachCate.getCatename()%></td>                                                                                                                                
                                <td>
                                    <form action="MainController">
                                        <input type="hidden" name="cateId" value="<%=eachCate.getCateId()%>">
                                        <input type="text" name="newCatename">
                                        <input type="submit" name="action" value="updateCategory">
                                    </form>
                                </td>
                            </tr>
                            <%
                        }
                        %>
                    </table>
                     <%
                }
            }else if(option.equals("addCategory")){
                %>
                <h2>Enter details for the new plant</h2>
                <form action="MainController">
                    <h1>Name</h1> <input type="text" name="newName">                                        
                    <input type="hidden" name="isFromAddForm" value="true">
                    <input type="submit" name="action" value="addCategory">
                </form>
                
                <%
            
           }
        
        %>
    </body>
</html>
