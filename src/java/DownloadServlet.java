/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import Database.DBConn;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 *
 * @author PHUONG
 */
public class DownloadServlet extends HttpServlet {

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
        try {
            DB db = DBConn.getConn();
            DBCollection col = db.getCollection("Projects");

            String fileName = request.getParameter("fileName");

            // Query for the file based on its name
            BasicDBObject query = new BasicDBObject("FileNames", fileName);
            DBCursor cursor = col.find(query);
            
            if (cursor.hasNext()) {
                BasicDBObject document = (BasicDBObject) cursor.next();
                List<String> fileNames = (List<String>) document.get("FileNames");
                List<byte[]> fileContents = (List<byte[]>) document.get("FileContents");
                
                for (int i = 0; i < fileNames.size(); i++) {
                    if (fileNames.get(i).equals(fileName)) {
                        // Set content type dynamically based on file extension
                        String contentType = "application/octet-stream";
                        response.setContentType(contentType);
                        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

                        // Write file content to response output stream
                        OutputStream outputStream = response.getOutputStream();
                        outputStream.write(fileContents.get(i));
                        outputStream.flush();
                        outputStream.close();
                        break;
                    }
                } 
            } else {
                response.getWriter().println("File not found");
            }
            DBConn.closeConn();
        } 
        catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error downloading file: " + e.getMessage());
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
