<%-- 
    Document   : updateProject
    Created on : Apr 6, 2024, 2:46:14 PM
    Author     : MAI_PHUONG
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import = "com.mongodb.*" %>
<%@page import = "Database.DBConn" %>
<%@page import = "org.bson.types.ObjectId;" %>
<html>
    <head>
        <title>Update Project</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="../css/bootstrap.css" type="text/css"/>
        <style>
            #frm {
                width: 500px;
                margin: auto;
                margin-top: 100px;
            }
            marquee {
                margin-top: 10px;
            }
        </style>
    </head>
    <body class="container-fluid">
        <%
            DBObject admin = (DBObject) session.getAttribute("admin");
            if(admin != null) {
                String username = (String) admin.get("Name");
        %>

    <form action= "updateStaff" method="post" class="form-group" id="frm">
        <%
            String userId = request.getParameter("projectId");
            ObjectId _id = new ObjectId(userId);
            BasicDBObject query = new BasicDBObject("_id", _id);

            DB db = DBConn.getConn();
            DBCollection col = db.getCollection("Projects");
            DBCursor cursor = col.find(query);
            BasicDBObject project = (BasicDBObject) cursor.next();
        %>
        <marquee><h2 class="text-primary">This is the Update form</h2></marquee>
        <h2 class="bg-danger text-white card-header"> Update form </h2>
        
        <input type='hidden' name='userId' value="<%= request.getParameter("projectId") %>">

        <table class="table table-hover">
            <tr>
                <td>Title</td>
                <td><input type="text" name="title" value="<%= project.getString("Title") %>" required></td>
            </tr>
            <tr>
                <td>Select files to upload</td>
                <td>
                    <input type="file" name="files" multiple>
                    <br>
                </td>
            </tr>
            <tr>
                <td>Manager</td>
                <td><input type="text" name="manager" value="<%= project.getString("Manager") %>" required></td>
            </tr>
            <tr>
                <td>Date</td>
                <td><input type="date" name="date" required></td>
            </tr>
            <tr>
                <td>Note</td>
                <td><input type="textarea" name="note" value="<%= project.getString("Note") %>" required></td>
            </tr>
            <tr>
                <td><button type="submit">Add</button></td>
                <td><button type="reset">Cancel</button></td>
            </tr>
        </table>
        <a href="showProject" class="btn btn-outline-primary d-block">Show Projects</a>
    </form>
    <a href='adminDashboard.jsp'><button class='btn btn-outline-primary'>Home</button></a>
    
        <%
            } 
            else {
                DBObject employee = (DBObject) session.getAttribute("employee");
                if(employee != null) {
                    response.sendRedirect("../employee/employeeDashboard.jsp");
                }
                else {
                    response.sendRedirect("../index.html");
                }
            }   
        %>
    </body>
</html>

