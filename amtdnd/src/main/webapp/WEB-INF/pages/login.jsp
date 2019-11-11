<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <link rel="stylesheet" type="text/css" href="css/mystyle.css">
</head>
<body>
<nav>
    <a class="navbar" href="${pageContext.request.contextPath}/registration">Register</a>
</nav>
<h3>Login Page</h3>

<form method="POST" action="${pageContext.request.contextPath}/login">
    <table>
        <tr>
            <td>User Name</td>
            <td>
                <label>
                    <input type="text" name="username" placeholder="username"/>
                </label>
            </td>
        </tr>
        <tr>
            <td>Password</td>
            <td>
                <label>
                    <input type="password" name="password" placeholder="password"/>
                </label>
            </td>
        </tr>

        <tr>
            <td colspan ="2">
                <input type="submit" value= "Submit" />
                <a href="${pageContext.request.contextPath}/login">Cancel</a>
            </td>
        </tr>
    </table>
</form>
<c:if test="${errorMessage != null}">
    ${error}
</c:if>
</body>
</html>