package Staff;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import Database.DBConn;
import com.mongodb.*;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;



/**
 *
 * @author PHUONG
 */
public class SearchProjectServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            HttpSession session = req.getSession();
            DBObject employee = (DBObject) session.getAttribute("employee");
            if(employee == null) {
                resp.sendRedirect("../index.html");
                return;
            }
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Project Data</title>");
            out.println("<link rel='stylesheet' href='../css/bootstrap.css'>");
            out.println("</head>");
            out.println("<body>");

            DB db = DBConn.getConn();
            DBCollection col = db.getCollection("Projects");

            String name = req.getParameter("name");

            BasicDBObject staff = new BasicDBObject("Manager", name);
            DBCursor project = col.find(staff);

            out.println("<div class='container'>");
            out.println("<h1>Project Data</h1>");
            out.println("<table class='table'>");
            out.println("<thead>");
            out.println("<tr><th>Title</th><th>Manager</th><th>Document</th><th>Date</th><th>Note</th><th>Status</th><th>Update</th><th>Submit</th></tr>");
            out.println("</thead>");
            out.println("<tbody>");

            while (project.hasNext()) {
                BasicDBObject document = (BasicDBObject) project.next();
                out.println("<tr>");

                out.println("<td>" + document.getString("Title") + "</td>");
                out.println("<td>" + document.getString("Manager") + "</td>");

                out.println("<td>");
                List<String> fileNames = (List<String>) document.get("FileNames");
                for (String fileName : fileNames) {
                    out.println("<a href='../download?fileName=" + fileName + "'>" + fileName + "</a><br>");
                }
                out.println("</td>");

                out.println("<td>" + document.getString("Date") + "</td>");
                out.println("<td>" + document.getString("Note") + "</td>");
                out.println("<td>" + document.getString("Status") + "</td>");

                // Update
                out.println("<td>");
                out.println("<form action='#' method='post'>");
                out.println("<input type='hidden' name='projectId' value='" + document.getObjectId("_id") + "'>");
                out.println("<input type='submit' value='Update'>");
                out.println("</form>");
                out.println("</td>");
                
                //Submit
                out.println("<td>");
                out.println("<form action='#' method='post'>");
                out.println("<input type='hidden' name='projectId' value='" + document.getObjectId("_id") + "'>");
                out.println("<input type='submit' value='Submit'>");
                out.println("</form>");
                out.println("</td>");

                out.println("</tr>");
            }

            out.println("</tbody>");
            out.println("</table>");
            out.println("</div>");

            DBConn.closeConn();

            out.println("</body>");
            out.println("</html>");
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
