<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Home Page</title>
</head>
<body>
<ul>
    <li>
        <a href="${pageContext.request.contextPath}/guild">Guild</a>
    </li>
    <li>
        <a href="${pageContext.request.contextPath}/party">Party</a>
    </li>
    <li>
        <a href="${pageContext.request.contextPath}/quest">Quests</a>
    </li>
    <li>
        <a href="${pageContext.request.contextPath}/logout">Logout</a>
    </li>
</ul>
<br/>
<h3>Home Page</h3>
<h3>${sessionScope.adventurer}</h3>
</body>
</html>