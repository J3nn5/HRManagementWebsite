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

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        resp.setContentType("text/html");

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        try {
            MongoClient mongo = new MongoClient("localhost", 27017);
            DB db = mongo.getDB("my_database");
            DBCollection col = db.getCollection("Users");

            BasicDBObject query = new BasicDBObject();
            query.put("Email", email);

            DBCursor cursor = col.find(query);
            if (cursor.hasNext()) {
                DBObject user = cursor.next();
                String storedPassword = (String) user.get("Password");
                if (storedPassword.equals(hashPassword(password))) {
                    out.println("<h2>Login Successful!</h2>");
                    // You can redirect to a dashboard or another page upon successful login
                } else {
                    out.println("<h2>Login Failed. Incorrect email or password.</h2>");
                }
            } else {
                out.println("<h2>Login Failed. User not found.</h2>");
            }

            mongo.close();
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<h2>Error occurred during login.</h2>");
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
