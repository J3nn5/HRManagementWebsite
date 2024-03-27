<%-- 
    Document   : admin
    Created on : Mar 26, 2024, 10:12:22 AM
    Author     : MAI_PHUONG
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import = "com.mongodb.*;" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body
        <div>
            <%
                DBObject user = (DBObject) session.getAttribute("user");
                String username = (String) user.get("Name");
            %>
            <h2><%= username%></h2>
        </div>
    </body>
</html>