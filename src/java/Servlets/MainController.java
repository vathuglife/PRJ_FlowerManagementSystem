/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author VietAnhOdyssey
 */
public class MainController extends HttpServlet {

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
            /*Gets the value of the HTML element named "action". This element can 
             be an input (login/register page), or an anchor (with href).*/ 
            String targetServlet = request.getParameter("action");
            if(targetServlet==null){
                response.sendRedirect("index.jsp");
            }else if(targetServlet.equals("Register")){
                request.getRequestDispatcher("RegisterServlet").forward(request, response);
            }else if(targetServlet.equals("search")){                    
                request.getRequestDispatcher("SearchServlet").forward(request, response);       
            }else if (targetServlet.equals("Login")){
                request.getRequestDispatcher("LoginServlet").forward(request,response);          
            }else if (targetServlet.equals("addToCart")){
                request.getRequestDispatcher("AddToCartServlet").forward(request,response);
            }else if (targetServlet.equals("viewCart")){
                request.getRequestDispatcher("ViewCart.jsp").forward(request,response);
            }else if (targetServlet.equals("update")){
                request.getRequestDispatcher("UpdateCartServlet").forward(request,response);
            }else if (targetServlet.equals("delete")){
                request.getRequestDispatcher("DeleteItemServlet").forward(request,response);
            }else if (targetServlet.equals("submitOrder")){
                request.getRequestDispatcher("SubmitOrderServlet").forward(request,response);
            }else if(targetServlet.equals("UpdateDetails")){
                request.getRequestDispatcher("UpdateDetailsServlet").forward(request,response);
            }else if(targetServlet.equals("sortOrders")){
                request.getRequestDispatcher("ShowOrdersByServlet").forward(request,response);            
            }else if(targetServlet.equals("showPlantDetails")){
                request.getRequestDispatcher("PlantDetailServlet").forward(request,response);
            }else if(targetServlet.equals("cancelOrder")){
                request.getRequestDispatcher("CancelOrderServlet").forward(request,response);
            }else if(targetServlet.equals("reorder")){
                request.getRequestDispatcher("ReorderServlet").forward(request,response);
            }
            //Admin section
            else if(targetServlet.equals("manageAccounts")){
                request.getRequestDispatcher("ManageAccountServlet").forward(request,response);
            }else if(targetServlet.equals("updateAccStatus")){
                request.getRequestDispatcher("UpdateAccStatusServlet").forward(request,response);
            }else if(targetServlet.equals("searchAccounts")){
                request.getRequestDispatcher("SearchAccountsServlet").forward(request,response);
            }else if(targetServlet.equals("viewPlants")){
                request.getRequestDispatcher("ViewPlantsServlet").forward(request,response);
            }
            else if(targetServlet.equals("addPlant")){
                request.getRequestDispatcher("AddPlantServlet").forward(request,response);
            }
            else if(targetServlet.equals("updatePlant")){
                request.getRequestDispatcher("UpdatePlantServlet").forward(request,response);
            }
             else if(targetServlet.equals("viewCategories")){
                request.getRequestDispatcher("ViewCategoriesServlet").forward(request,response);
            }
            else if(targetServlet.equals("addCategory")){
                request.getRequestDispatcher("AddCategoryServlet").forward(request,response);
            }else if(targetServlet.equals("viewOrders")){
                request.getRequestDispatcher("ViewOrdersServlet").forward(request,response);
            }else if(targetServlet.equals("getOrdersWithinDates")){
                request.getRequestDispatcher("GetOrdersWithinDates").forward(request,response);
            }else if(targetServlet.equals("deletePlant")){
                request.getRequestDispatcher("DeletePlantServlet").forward(request,response);
            }else if(targetServlet.equals("updateCategory")){
                request.getRequestDispatcher("UpdateCategoryServlet").forward(request,response);
            }                       
            else{
                request.getRequestDispatcher("LogoutServlet").forward(request,response);
            }                        
        }
    }           // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
