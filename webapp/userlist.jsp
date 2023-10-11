<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User List</title>
</head>
<body>
<h1>User List</h1>

<table>
    <thead>
    <tr>
        <th>Username</th>
        <th>Email</th>
        <th>Password</th>
        <!-- Thêm các cột khác tùy theo thông tin bạn muốn hiển thị -->
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${userList}" var="user">
        <tr>
            <td>${user.password}</td>
            <td>${user.username}</td>
            <td>${user.email}</td>
            <!-- Thêm các cột khác tùy theo thông tin bạn muốn hiển thị -->
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
