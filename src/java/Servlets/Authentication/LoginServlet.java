/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets.Authentication;

import DAO.AccountDAO;
import DTO.Account;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 *
 * @author VietAnhOdyssey
 */
public class LoginServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String email = request.getParameter("email");
            String password = request.getParameter("password");            
            //Checks if the user hits on the save login button in the form.
            String saveLogin = request.getParameter("savelogin");            
            
            /*------CASE 1: If the user logs in, BUT NOT FROM THE LOGIN PAGE------*/
            
            /*If user enters the personal Page from another page BUT the Login.html,
            (for example, from the Home Page to the Personal Page).
            the chances are: they are logged in previously. If they are
            logged in previously, their cookies (ticket) will be stored 
            in the BROWSER. That's why we need to check if there is any of them
            in the BROWSER.            
            */
            
            
            if(email==null || password==null){
                //Contains a list of cookies. These cookies are received from the request
                //that the browser makes (e.g. GET/POST requests from the address bar/form.                
                //Note: In a request that the browser makes, THERE ARE LOTS OF COOKIES
                //that are stored beforehand. That's why, we need to select the Correct cookie
                //that this Java Web app generates for a user, based on the name.
                Cookie[] cookieList = request.getCookies();
                String token = "";
                
                if(cookieList!=null){
                    //The for-loop is to check if inside the existing cookie list, there
                    //is a cookie called "userCookie".
                    for(Cookie eachCookie:cookieList){
                        if(eachCookie.getName().equals("userCookie")){
                            token = eachCookie.getValue();
                            break;
                        }
                    }                    
                }
                //If the user access the personal page from another page (e.g. Home Page)
                //without loggin in, he/she won't have the cookie, and thus, won't be allowed
                //to view the user page.
                if(token.equals("")) response.sendRedirect("error.html");
                else response.sendRedirect("personalPage.jsp");
                
            }
                                    
            /*------CASE 2: If the user logs in, WITHIN THE LOGIN PAGE.*/
            else{            
                Account acc = DAO.AccountDAO.CheckAccount(email, password);
                if(acc!=null){
                    //Redirect to admin's page
                    if(acc.getRole()==1) {                        
                        //Creates a session memory space in the Java Web Server,
                        // the admin user.
                        HttpSession session = request.getSession(true);
                        if(session!=null){
                            session.setAttribute("loggedInAccount",acc);                        
                            
                            //If user chooses to save the login, then generates a cookie then
                            //saves it into the database.
                            if(saveLogin!=null){                                 
                                String token = AccountDAO.GenerateToken();
                                //sends back to the BROWSER (e.g. Chrome, OperaGX) the Cookie generated 
                                //if the user successfully logs in, and GIVES IT A NAME called: "userCookie"                                
                                AccountDAO.UpdateToken(token, email);
                                Cookie cookie = new Cookie("userCookie",token);
                                cookie.setMaxAge(500); //sets after how long the cookie will expire, in SECONDS.
                                response.addCookie(cookie);
                            }
                            response.sendRedirect("admin.jsp");                
                        }
                    }else{ //Redirect to customer's page.
                        /*If a user is found, generates a session cookie to the front-end.
                        In short, a cookie identifies a user by (name, id,...)
                        In the future, for each request sent to the back-end
                        That cookie is also sent, so that the back-end can check
                        Which user is, for example, adding an order, editing an order, or 
                        deleting an order.

                        A cookie is pretty much like a ticket in an amusement park.
                        Without spending the money (logging in), you won't have the ticket. (cookie).
                        Without having the ticket (cookie), you WON'T  be able to enter ANY GAME (website) 
                        in the Amusement park.*/

                        //Generates a session inside the Java Tomcat's Session Memory.
                        /*Session vs Cookie
                        Session: able to save a lot of data, securely. (e.g. user's shopping cart,...)
                        //In ReactJS, the Session is basically the State/Context.
                        Cookie: able to save a tiny bit of data, NOT SECURELY (e.g. UserID) */
                        HttpSession session = request.getSession(true);
                        if(session!=null){
                            session.setAttribute("loggedInAccount",acc);                        
                            
                            //If user chooses to save the login, then generates a cookie then
                            //saves it into the database.
                            if(saveLogin!=null){                                 
                                String token = AccountDAO.GenerateToken();
                                //sends back to the BROWSER (e.g. Chrome, OperaGX) the Cookie generated 
                                //if the user successfully logs in, and GIVES IT A NAME called: "userCookie"                                
                                AccountDAO.UpdateToken(token, email);
                                Cookie cookie = new Cookie("userCookie",token);
                                cookie.setMaxAge(500); //sets after how long the cookie will expire, in SECONDS.
                                response.addCookie(cookie);
                            }
                            //As we will be using the cookie on the BROWSER to identify which user
                            //for which page, we will need to bind THAT COOKIE into the RESPONSE,
                            //so that the BROWSER will receive THAT RESPONSE (with the Cookie inside it).
                            //That's why we don't use request.getRequestDispatcher here.
                            response.sendRedirect("personalPage.jsp"); 
                        }
                        else{
                            response.sendRedirect("error.html");
                        }                    
                }
            }                        
        }
    }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>


}
