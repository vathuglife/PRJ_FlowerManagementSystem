/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets.Users;

import DAO.OrderDAO;
import DTO.Account;
import DTO.Order;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author VietAnhOdyssey
 */
public class ShowOrdersByServlet extends HttpServlet {

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
            String sortBy = request.getParameter("sortOrdersBy");
            //Gets the current user's environment 
            //(which contains data, for example: cart, account...,list of orders).
            HttpSession session = request.getSession();
            Account receivedAcc = (Account)session.getAttribute("loggedInAccount");
            int option = 0;
            switch(sortBy){                
                case "all":
                    option = 4;
                    break;
                case "progress":
                    option = 1;
                    break;
                case "completed":
                    option = 2;
                    break;
                case "canceled":
                    option = 3;
                    break;
            }
            ArrayList<Order> orderList = OrderDAO.getOrders(receivedAcc.getEmail(),option);
            //Binds the list of orders (received from the OrderDAO.getOrders function,
            //into the request. This is to help the personalPage receive the found results later on,
            //using the command: request.getAttribute("orderList");
            request.setAttribute("orderList",orderList);
            request.getRequestDispatcher("personalPage.jsp").forward(request,response);
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
