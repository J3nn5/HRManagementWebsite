<%-- 
    Document   : addMeeting
    Created on : 11 Apr 2024, 15:29:47
    Author     : PHUONG
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import = "com.mongodb.*" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" http-equiv="Content-Type" content="width=device-width, initial-scale=1.0">
        <title>Add Meeting</title>
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
            DBObject admin = (DBObject) session.getAttribute("admin");
            if(admin != null) {
                String username = (String) admin.get("Name");        %>
        
        <div>
            <form action="addmeeting" method="post" class = "form-group" id="frm"  enctype>
                <h2 class="bg-danger text-white card-header"> Add Meeting Form </h2>
                <table class="table table-hover">
                    <tr>
                        <td>Topic</td>
                        <td><input type="text" name="topic" required></td>
                    </tr>
                    <tr>
                        <td>Date</td>
                        <td><input type="date" name="date" required></td>
                    </tr>
                    <tr>
                        <td>Time</td>
                        <td><input type="time" name="time" required></td>
                    </tr>
                    <tr>
                        <td>Location</td>
                        <td>
                            <select class = "custom-select" name="location">
                                <option value="Meeting Room 01">Meeting Room 01</option>
                                <option value="Meeting Room 02">Meeting Room 02</option>
                                <option value="Meeting Room 03">Meeting Room 03</option>
                                <option value="Meeting Room 04">Meeting Room 04</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                     <td><button type="submit" class = "btn btn-outline-primary d-block">Add</button></td>
                     <td><button type="reset" class = "btn btn-outline-primary d-block">Cancel</button></td>
                </tr>
                </table>
            </form>
            <a href='./adminDashboard.jsp'><button class='btn btn-outline-primary'>Home</button></a>
        </div>
        
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
