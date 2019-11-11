<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quest Description</title>
    <link rel="stylesheet" type="text/css" href="css/mystyle.css">
</head>
<body>
<nav>
    <a class="navbar" href="${pageContext.request.contextPath}/home">Home</a>
    <a class="navbar" href="${pageContext.request.contextPath}/guild">Guild</a>
    <a class="navbar" href="${pageContext.request.contextPath}/party">Party</a>
    <a class="navbar" href="${pageContext.request.contextPath}/quest">Quests</a>
    <a class="navbar" href="${pageContext.request.contextPath}/logout">Logout</a>
</nav>
<h3>${quest.getObjective}</h3>
<p>${quest.getDescription}</p>
<br/>
<h3>Rewards</h3>
<table>
    <tr>
        <td>Experience</td>
        <td>${quest.getExp}</td>
    </tr>
    <tr>
        <td>Gold</td>
        <td>${quest.getGold}</td>
    </tr>
</table>
<br/>
<a class="button" href="${pageContext.request.contextPath}/quest">Return to quests.</a>
</body>
</html>
