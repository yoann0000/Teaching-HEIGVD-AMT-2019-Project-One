<%--
  Created by IntelliJ IDEA.
  User: Rod Julien
  Date: 10.11.2019
  Time: 15:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${guild.name}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/mystyle.css">
</head>
<body>
<nav>
    <a class="navbar" href="${pageContext.request.contextPath}/home">Home</a>
    <a class="navbar" href="${pageContext.request.contextPath}/guild">Guild</a>
    <a class="navbar" href="${pageContext.request.contextPath}/party">Party</a>
    <a class="navbar" href="${pageContext.request.contextPath}/quest">Quests</a>
    <a class="navbar" href="${pageContext.request.contextPath}/logout">Logout</a>
</nav>
<h1>${guild.name}</h1>
<p>Reputation : ${guild.reputation}</p>
<h3>Members</h3>

<table border="1" cellpadding="5" cellspacing="5">
    <tr>
        <th>Name</th>
        <th>Level</th>
        <th>Race</th>
        <th>Class</th>
    </tr>

    <c:forEach var="member" items="${memberList}">
        <tr>
            <td>${member.name}</td>
            <td>${member.getLevel()}</td>
            <td>${member.race}</td>
            <td>${member.klass}</td>
        </tr>
    </c:forEach>
</table>
<form method="POST" action="${pageContext.request.contextPath}/guild">
    <c:choose>
        <c:when test="${isMember}">
            <button onclick="this.form.submited=this.value;"  type="submit" name="quit" value="${guild.name}">Quit this Guild</button>
        </c:when>
        <c:otherwise>
            <button onclick="this.form.submited=this.value;"  type="submit" name="join" value="${guild.name}">Join this Guild</button>
        </c:otherwise>
    </c:choose>
</form>

<%--For displaying Page numbers.
The when condition does not display a link for the current page--%>
<c:if test="${errorMessage != null}">
    ${error}
</c:if>
</body>

