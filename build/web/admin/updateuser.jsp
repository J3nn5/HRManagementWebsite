<%-- 
    Document   : updateUser
    Created on : Mar 27, 2024, 1:11:47 PM
    Author     : MAI_PHUONG
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.mongodb.*" %>
<%@page import="org.bson.types.ObjectId" %>
<html>
    <head>
        <title>Register Page</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="../css/bootstrap.css" type="text/css"/>
        <style>
            #frm
            {
                width: 500px;
                margin: auto;
                margin-top: 100px;
            }
            marquee
            {
                margin-top: 10px;
            }
        </style>

    </head>
     <body class="container-fluid">
         
         <%
            String userId = request.getParameter("userId");
            ObjectId _id = new ObjectId(userId);
            BasicDBObject query = new BasicDBObject("_id", _id);
            
            MongoClient mongo = new MongoClient("localhost", 27017);
            DB db = mongo.getDB("my_database");
            DBCollection col = db.getCollection("Users");
            DBCursor cursor = col.find(query);
            BasicDBObject document = (BasicDBObject) cursor.next();
         %>
         
        <form action="updateuser" method="post" class = "form-group" id="frm">
            <marquee><h2 class="text-primary">This is da Update form</h2></marquee> 
            <h2 class="bg-danger text-white card-header"> Update form </h2>
            
            <!-- Gui userid --> 
            <input type='hidden' name='userId' value="<%= userId%>">
            
            <table class="table table-hover">
                <tr>
                    <td>Name</td>
                    <td><input type="text" name="userName" required value="<%= document.getString("Name")%>"></td>
                </tr>
                <tr>
                    <td>Email</td>
                    <td><input type="email" name="email" required value="<%= document.getString("Email")%>"></td>
                </tr>
                <tr>
                    <td>Password</td>
                    <td><input type="password" name="psswd" required></td>
                </tr>
                <tr>
                    <td>Department</td>
                    <td>
                        <select class = "custom-select" name="department">
                            <option value="banquantri" <%= document.getString("Department").equals("banquantri") ? "selected" : "" %>>Ban Quan Tri</option>
                            <option value="banquanly" <%= document.getString("Department").equals("banquanly") ? "selected" : "" %>>Ban Quan Ly</option>
                            <option value="nhansu" <%= document.getString("Department").equals("nhansu") ? "selected" : "" %>>Phong Nhan Su</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>Position</td>
                    <td><input type="text" name="position" required value="<%= document.getString("Position")%>"></td>
                </tr>

                <tr class="card-footer">
                     <td><button class="btn btn-outline-success" type="submit">Update</button></td>
                     <td><button class="btn btn-outline-danger" type="reset">Cancel</button></td>
                </tr>
            </table>
            <a href="showuser" class = "btn btn-outline-primary d-block">Show Staffs</a>
        </form>
            <a href='admin.jsp'><button class='btn btn-outline-primary'>Home</button></a>
    </body>
</html>

