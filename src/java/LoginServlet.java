import Database.DBConn;
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
import com.mongodb.client.MongoDatabase;
import jakarta.servlet.http.HttpSession;

//@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        resp.setContentType("text/html");

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        try {
//            MongoDatabase database = DBConn.connect();
//            MongoClient mongo = new MongoClient("localhost", 27017);
//            DB db = mongo.getDB("my_database");
            DB db = DBConn.getConn();
            
            DBCollection ad = db.getCollection("Admins"); 
            BasicDBObject query1 = new BasicDBObject();
            query1.put("Email", email);
            DBCursor cursor1 = ad.find(query1);
            
            DBCollection emp = db.getCollection("Employees");
            BasicDBObject query2 = new BasicDBObject();
            query2.put("Email", email);
            DBCursor cursor2 = emp.find(query2);

            //Check col admin
            if (cursor1.hasNext()) {
                DBObject admin = cursor1.next();
                String storedPassword = (String) admin.get("Password");
                if (storedPassword.equals(hashPassword(password))) {
//                    out.println("<h2>Login Successful!</h2>");
                    // You can redirect to a dashboard or another page upon successful login
                    HttpSession session = req.getSession(true);
                    session.setAttribute("admin", admin);
                    resp.sendRedirect("admin/adminDashboard.jsp");
                } else {
//                    resp.sendRedirect("login.html");
                    out.println("<script>");
                    out.println("   alert(\'Login Failed. Incorrect email or password.\');");
                    out.println("   window.location.href = 'login.html';");
                    out.println("</script>");
                }
            }
            //Check col employee
            else if (cursor2.hasNext()) {
                DBObject employee = cursor2.next();
                String storedPassword = (String) employee.get("Password");
                if (storedPassword.equals(hashPassword(password))) {
                    HttpSession session = req.getSession(true);
                    session.setAttribute("employee", employee);
                    resp.sendRedirect("employee/employeeDashboard.jsp");
                } else {
                    out.println("<script>");
                    out.println("   alert(\'Login Failed. Incorrect email or password.\');");
                    out.println("   window.location.href = 'login.html';");
                    out.println("</script>");
                }
            }
            else {
                out.println("<script>");
                out.println("   alert(\'Login Failed. User not found.\');");
                out.println("   window.location.href = 'login.html';");
                out.println("</script>");
            }

            DBConn.closeConn();
        } catch (Exception e) {
            resp.sendRedirect("index.html");
        }
    }

    // Method to hash the password using SHA-256
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}