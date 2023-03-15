<%@page import="DAO.AccountDAO"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="DTO.Account"%>
<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <title>TODO supply a title</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <header>
        <%
            Account receivedAcc = (Account)session.getAttribute("loggedInAccount");
        %>
       
        

        
    </header>
    <body>
        
        
        <%            
            boolean isLoggedIn = false;
            Account account = null;
            Cookie[] c = request.getCookies();
            if(receivedAcc==null){                
                for(Cookie eachCookieAttribute:c){
                    if(eachCookieAttribute.getName().equals("userCookie")){
                        String token = eachCookieAttribute.getValue();  
                        account = AccountDAO.CheckAccount(token);
                        if(account !=null && account.getRole()==1){                            
                            isLoggedIn = true;
                            break;
                        }
                    }
                    
                }
            }else if (receivedAcc!=null && receivedAcc.getRole()==1) {
                isLoggedIn = true;
                account = receivedAcc;
            }
            
            
            if(isLoggedIn==false){
                %>
                <h2>Ya think ya can get in the Admin page without logging in? Think again blyat!</h2>
                <a href="index.jsp">Back to home and redeem yr mistakes</a>
                <%            
            }else if(isLoggedIn==true){     
                %>
                 <h2>Kore wa admin page blyat</h2>
                    <ul>
                        <li><a href="MainController?action=manageAccounts">Manage Accounts</a></li>
                        <li><a href="searchAccounts.jsp">Search Accounts</a></li>
                        <li><a href="MainController?action=viewOrders">Manage Orders</a></li>
                        <li><a href="managePlants.jsp">Manage Plants</a></li>                        
                        <li><a href="manageCategories.jsp">Manage Categories</a></li>                
                        <li><a href="MainController?action=Logout">Logout</a></li>         
                    </ul>
                
                <%        
                ArrayList<Account> accList = (ArrayList<Account>)request.getAttribute("accList");                
                if(accList==null){
                    %><h2>Choose an action from the above menu, and the result will be shown here.</h2><%
                }else if(request.getParameter("action").equals("manageAccounts")){                    
                    %>                                                           
                    <table>
                        <th>
                        <td>Id</td>
                        <td>Email</td>
                        <td>Full Name</td>
                        <td>Status</td>
                        <td>Phone</td>
                        <td>Role</td>
                        <td>Action</td>

                        </th><%
                    Iterator<Account> accListIterator = accList.iterator();
                    while(accListIterator.hasNext()){
                        Account eachAcc = accListIterator.next();
                        %>
                        <tr>
                            <td><%=eachAcc.getAccID()%></td>
                            <td><%=eachAcc.getEmail()%></td>
                            <td><%=eachAcc.getFullname()%></td>
                            <td><%
                                int status = eachAcc.getStatus();
                                if(status==1){%>Blocked<%}
                                else{ %>Normal<%}
                            %>
                            </td>
                            <td><%=eachAcc.getPhone()%></td>                                                
                            <td><%
                                if(eachAcc.getRole()== 1) {
                                    %>Administrator<%                            
                                }else{
                                %> Normie User

                                <%}%>
                            </td>      
                            <td>
                                <%if(eachAcc.getRole()!=1){
                                    %><a href="MainController?action=updateAccStatus&email=<%=eachAcc.getEmail()%>&currentStatus=<%=eachAcc.getStatus()%>">Block/Unblock</a></td>                        
                                    <%
                                }
                        %></tr>
                        <%
                    }%>

                        
                    </table>
                        
                    
                <%}
            }%>
    </body>
</html>
