/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author VietAnhOdyssey
 */
public class AddToCartServlet extends HttpServlet {

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
            String plantId = request.getParameter("plantID");
            //Gets the current session that is related/binded to the current request
            //sent from the jsp Page.            
            HttpSession session = request.getSession(true);
            HashMap<String,Integer> HashMapCart = (HashMap<String,Integer>)session.getAttribute("cart");
            if(HashMapCart==null){ //If the cart is not already created, then create one.
                HashMapCart = new HashMap<>();
                HashMapCart.put(plantId, 1); //default quantity is 1.                
            }else{ //If the cart is already created, but there is nothing inside the cart.
                //Checks if the plant with a plantID is already available in the
                //cart                
                Integer tmp = HashMapCart.get(plantId);
                if(tmp==null){
                    HashMapCart.put(plantId,1);
                }else{
                    //if another plant with same PlantId is found, replace it with a new one.
                    tmp++;
                    HashMapCart.put(plantId,tmp); 
                }
            }
            //Sets a new Cart into Java's Session Memory for 1 specific user.
            session.setAttribute("cart",HashMapCart);
            response.sendRedirect("index.jsp");
            
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
