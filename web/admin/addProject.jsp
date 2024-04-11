<%-- 
    Document   : addproject
    Created on : Mar 27, 2024, 11:59:40 AM
    Author     : MAI_PHUONG
    Edit by    : Jun
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import = "com.mongodb.*" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" http-equiv="Content-Type" content="width=device-width, initial-scale=1.0">
        <title>Add Project</title>
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
            <form action="addProject" method="post" class = "form-group" id="frm"  enctype="multipart/form-data">
                <h2 class="bg-danger text-white card-header"> Add Project Form </h2>
                <table class="table table-hover">
                    <tr>
                        <td>Title</td>
                        <td><input type="text" name="title" required></td>
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
                        <td><input type="text" name="manager" required></td>
                    </tr>
                    <tr>
                        <td>Note</td>
                        <td><input type="textarea" name="note" required></td>
                    </tr>
                    <tr>
                        <td>Date</td>
                        <td><input type="date" name="date" required></td>
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
