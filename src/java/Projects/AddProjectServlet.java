package Projects;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import com.mongodb.*;

/**
 *
 * @author MAI_PHUONG
 */
//@WebServlet("/addtask")
public class addProjectServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
           
            
            String title = req.getParameter("title");
            String document = req.getParameter("document");
            String note = req.getParameter("note");
            String time = req.getParameter("time");
            
            try {
                MongoClient mongo = new MongoClient("localhost", 27017);
                DB dbl = mongo.getDB("my_database");
                DBCollection col = dbl.getCollection("Projects");
                
                BasicDBObject data = new BasicDBObject();
                data.put("Title", title);
                data.put("Document", document);
                data.put("Note", note);
                data.put("Time", time);
                data.put("User", "");
                data.put("Status", "");
                
                col.insert(data);
                
                out.println("Successful");
                mongo.close();
            }
            catch(Exception e) {
                out.println("Failed" + e);
            }
            
            out.println("<a href='admin.jsp'><button class='btn btn-outline-primary'>Home</button></a>");
            out.println("</div>");
            out.close();
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
