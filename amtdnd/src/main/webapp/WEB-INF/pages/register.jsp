<!--%@ page contentType="text/html;charset=UTF-8" language="java" %-->
<!--%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %-->
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Register</title>
    <link rel="stylesheet" type="text/css" href="css/mystyle.css">
</head>
<body>
<nav>
    <a class="navbar" href="${pageContext.request.contextPath}/login">Login</a>
</nav>
<h3>Register Page</h3>

<form method="POST" action="${pageContext.request.contextPath}/registration">
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
            <td>Race</td>
            <td>
                <label>
                    <select name="race">
                        <c:forEach var="race" items="${races}">
                            <option value="${race}"> ${race} </option>
                        </c:forEach>
                    </select>
                </label>
            </td>
        </tr>
        <tr>
            <td>Class</td>
            <td>
                <label>
                    <select name="class">
                        <c:forEach var="class" items="${classes}">
                            <option value="${class}"> ${class} </option>
                        </c:forEach>
                    </select>
                </label>
            </td>
        </tr>
        <tr>
            <td colspan ="2">
                <input type="submit" value= "Submit"/>
                <a class="button cancel" href="${pageContext.request.contextPath}/registration">Cancel</a>
            </td>
        </tr>
    </table>
</form>
<c:if test="${errorMessage != null}">
    <h2 class="error">${error}</h2>
</c:if>
</body>
</html>
