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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Project</title>
        <link rel="stylesheet" href=""
    </head>
    <body>
        <%
            DBObject user = (DBObject) session.getAttribute("user");
            if(user != null) {
                String username = (String) user.get("Name");
        %>
        
        <div>
            <form action="addProject" method="post" class = "form-group" id="frm"  enctype="multipart/form-data">
                <h2 class="bg-danger text-white card-header"> Add Project Form </h2>
                <table>
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
                     <td><button type="submit">Add</button></td>
                     <td><button type="reset">Cancel</button></td>
                </tr>
                </table>
            </form>
        </div>
        
        <%
            } else {
                response.sendRedirect("../index.html");
            }
        %>
    </body>
</html>
