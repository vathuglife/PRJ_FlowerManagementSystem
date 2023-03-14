/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets.Common;

import DAO.AccountDAO;
import DAO.OrderDAO;
import DTO.Account;
import DTO.Order;
import java.io.IOException;
import java.util.HashMap;
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
public class SubmitOrderServlet extends HttpServlet {

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
        HttpSession requestingSession = request.getSession();
        Cookie[] cookiesList = request.getCookies();
        int currentOrder = Integer.parseInt(request.getParameter("orderId"));
        HashMap<String,Integer> currentCart = (HashMap<String,Integer>)requestingSession.getAttribute("cart");
        //case 1: there is nothing in the cart. The !=null condition is for the event 
        //when the user ONLY PERFORMS THE RE-ORDER FUNCTION (second if-case, meaning that no Order/Cart is created.)
        if(currentCart!=null){             
            if(currentCart.size()==0){
                request.setAttribute("warningMsg","CART EMPTY YA BLYAT!");            
                //When we put this line here, this Servlet will no longer be used.
                request.getRequestDispatcher("ViewCart.jsp").forward(request,response);
            }else{
        //        case 3: there are some items in the cart, but the user has not logged in yet.
        //        to do so, we need to check if the cookie of that user has already existed
        //        before or not. We will get the cookie list from the request, and check if any 
        //        of them contains the name called: "userCookie" - which we have already set before.
                String token = "";
                for (Cookie eachCookie:cookiesList){
                    if(eachCookie.getName().equals("userCookie")){
                        token = eachCookie.getValue();
                        break;
                    }
                }
                if(token==""){
                    request.setAttribute("warningMsg","User iz not logged in ya dumbazz");            
                    //When we put this line here, this Servlet will no longer be used.
                    request.getRequestDispatcher("ViewCart.jsp").forward(request,response);
                }else{         
                    //case 3: The cart already has some items, and the cookie of a user is found.
                    //But who knows if that cookie is invalid?
                    //So we need to check if that cookie is invalid.
                    Account acc = AccountDAO.CheckAccount(token);
                    if (acc==null){
                        request.setAttribute("warningMsg","User iz not logged in ya dumbazz");            
                        //When we put this line here, this Servlet will no longer be used.
                        request.getRequestDispatcher("ViewCart.jsp").forward(request,response);
                    }else{
                        boolean isAdded = OrderDAO.AddOrder(acc.getEmail(),currentCart);
                        if(isAdded==false){
                            request.setAttribute("warningMsg","Da server iz fecked up, so orderz culdn't be addid");            
                        //When we put this line here, this Servlet will no longer be used.

                        }else{
                            request.setAttribute("warningMsg","Succezzfully addid sum orderz for ya");            
                        }
                        request.getRequestDispatcher("ViewCart.jsp").forward(request,response);
                    }
                }
            } 
        }                      
        //Case 2: User performs the re-order function on a canceled order. (By clicking 
        //on the create order Link.)
        else {                           
            boolean isReordered = OrderDAO.Reorder(currentOrder);
            if(isReordered == false){
                request.setAttribute("warningMsg","Something went wrong and we couldn't reorder this order.");            
            }            
            else{            
                request.setAttribute("warningMsg","Reordered complete!");            
                request.getRequestDispatcher("personalPage.jsp").forward(request,response);
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

