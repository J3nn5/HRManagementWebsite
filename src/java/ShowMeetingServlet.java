/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import Database.DBConn;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author PHUONG
 */
public class ShowMeetingServlet extends HttpServlet {

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
            try {
                DB db = DBConn.getConn();
                DBCollection col = db.getCollection("Meetings");

                // Find all documents in the collection
                DBCursor cursor = col.find();

                out.println("<div class='container'>");
                out.println("<h1>Meeting Data</h1>");
                out.println("<link rel='stylesheet' href='css/bootstrap.css'>");
                out.println("<table class='table'>");
                out.println("<thead>");
                out.println("<tr><th>Topic</th><th>Date</th><th>Time</th><th>Location</th><th>Update</th><th>Delete</th></tr>");
                out.println("</thead>");
                out.println("<tbody>");

                // Iterate over the cursor and print each document as a row in the table
                while (cursor.hasNext()) {
                    BasicDBObject document = (BasicDBObject) cursor.next();
                    out.println("<tr>");

                    out.println("<td>" + document.getString("Topic") + "</td>");
                    out.println("<td>" + document.getString("Date") + "</td>");
                    out.println("<td>" + document.getString("Time") + "</td>");
                    out.println("<td>" + document.getString("Location") + "</td>");

                    // Update
                    out.println("<td>");
                    out.println("<form action='#' method='post'>");
                    out.println("<input type='hidden' name='userId' value='" + document.getObjectId("_id") + "'>");
                    out.println("<input type='submit' value='Update'>");
                    out.println("</form>");
                    out.println("</td>");

                    //Delete
//                    out.println("<td>");
//                    out.println("<form action='../admin/deleteStaff' method='post'>");
//                    out.println("<input type='hidden' name='userId' value='" + document.getObjectId("_id") + "'>");
//                    out.println("<button onclick=\"return confirmDelete()\">Delete</button>");
//                    out.println("</form>");
//                    out.println("</td>");
//
//                    out.println("<script>");
//                    out.println("function confirmDelete() {");
//                    out.println("    var result = confirm('Delete account " + document.getString("Name") + " ?');");
//                    out.println("    if (result) {");
//                    out.println("        return true;"); // Chuyển hướng nếu người dùng đồng ý
//                    out.println("    } else {");
//                    out.println("        return false;"); // Chuyển hướng nếu người dùng hủy
//                    out.println("    }");
//                    out.println("    return false;");
//                    out.println("}");
//                    out.println("</script>");

                    out.println("</tr>");
                }

                out.println("</tbody>");
                out.println("</table>");
                out.println("</div>");

                DBConn.closeConn();
            } catch (Exception e) {
                e.printStackTrace();
                out.println("<h2>Error retrieving data from MongoDB!</h2>");
            }

            out.println("<a href='admin/adminDashboard.jsp'><button class='btn btn-outline-primary'>Home</button></a>");
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
