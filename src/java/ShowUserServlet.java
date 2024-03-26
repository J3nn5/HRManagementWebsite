/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author MSI PC
 */
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

//@WebServlet("/showuser")
public class ShowUserServlet extends HttpServlet {
     @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter pw = resp.getWriter();
        resp.setContentType("text/html");

        pw.println("<!DOCTYPE html>");
        pw.println("<html>");
        pw.println("<head>");
        pw.println("<title>User Data</title>");
        pw.println("<link rel='stylesheet' href='css/bootstrap.css'>");
        pw.println("</head>");
        pw.println("<body>");

        try {
            MongoClient mongo = new MongoClient("localhost", 27017);
            DB db = mongo.getDB("my_database");
            DBCollection col = db.getCollection("Users");

            // Find all documents in the collection
            DBCursor cursor = col.find();

            pw.println("<div class='container'>");
            pw.println("<h1>User Data</h1>");
            pw.println("<table class='table'>");
            pw.println("<thead><tr><th>Name</th><th>Email</th><th>DOB</th><th>Phone Number</th><th>Gender</th><th>City</th></tr></thead>");
            pw.println("<tbody>");

            // Iterate over the cursor and print each document as a row in the table
            while (cursor.hasNext()) {
                BasicDBObject document = (BasicDBObject) cursor.next();
                pw.println("<tr>");
                pw.println("<td>" + document.getString("Name") + "</td>");
                pw.println("<td>" + document.getString("Email") + "</td>");
                pw.println("<td>" + document.getString("DOB") + "</td>");
                pw.println("<td>" + document.getString("Phone Number") + "</td>");
                pw.println("<td>" + document.getString("Gender") + "</td>");
                pw.println("<td>" + document.getString("City") + "</td>");
                pw.println("</tr>");
            }

            pw.println("</tbody>");
            pw.println("</table>");
            pw.println("</div>");

            mongo.close();
        } catch (Exception e) {
            e.printStackTrace();
            pw.println("<h2>Error retrieving data from MongoDB!</h2>");
        }

        pw.println("<a href='home.html'><button class='btn btn-outline-primary'>Home</button></a>");
        pw.println("</body>");
        pw.println("</html>");
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}