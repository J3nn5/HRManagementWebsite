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
        <title>Admin Page</title>
    </head>
    <body
        <div>
            <%
                DBObject user = (DBObject) session.getAttribute("user");
                String username = (String) user.get("Name");
            %>
            <h2><%= username%></h2>
        </div>
        <div>
            <a href="showdata" class = "btn btn-outline-primary d-block">Show Staffs</a>
        </div>
        <div>
            <form action="addproject" method="post" class = "form-group" id="frm">
                <h2 class="bg-danger text-white card-header"> AddTask Form </h2>
                <table>
                    <tr>
                        <td>Title</td>
                        <td><input type="text" name="title" required></td>
                    </tr>
                    <tr>
                        <td>Document</td>
                        <td><input type="text" name="document" required></td>
                    </tr>
                    <tr>
                        <td>Note</td>
                        <td><input type="textarea" name="note" required></td>
                    </tr>
                    <tr>
                        <td>Time</td>
                        <td><input type="date" name="time" required></td>
                    </tr>
                    <tr>
                     <td><button type="submit">Add</button></td>
                     <td><button type="reset">Cancel</button></td>
                </tr>
                </table>
            </form>
        </div>
    </body>
</html>