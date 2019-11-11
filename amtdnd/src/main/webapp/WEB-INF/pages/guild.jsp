<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Guilds</title>
    <link rel="stylesheet" type="text/css" href="css/mystyle.css">
</head>
<body>
<nav>
    <a href="${pageContext.request.contextPath}/home">Home</a>
    <a href="${pageContext.request.contextPath}/guild">Guild</a>
    <a href="${pageContext.request.contextPath}/party">Party</a>
    <a href="${pageContext.request.contextPath}/quest">Quests</a>
    <a href="${pageContext.request.contextPath}/logout">Logout</a>
</nav>
<h3>Existing Guilds</h3>

<table>
    <form method="POST" action="${pageContext.request.contextPath}/guild">
        <tr>
            <input type="text" name="newGuild"/>
            <button onclick="this.form.submited=this.value;"  type="submit" name="guild" value="">Create a new guild</button>
        </tr>
    </form>
</table>

<table border="1" cellpadding="5" cellspacing="5">
    <tr>
        <th>Name</th>
        <th>Reputation</th>
    </tr>

    <c:forEach var="guild" items="${guildList}">
        <tr>
            <td>
                <form method="POST" action="${pageContext.request.contextPath}/guild">
                    <input onclick="this.form.submited=this.value;" name="guild" type="submit" value= "${guild.name}" />
                </form>
            </td>
            <td>${guild.reputation}</td>
        </tr>
    </c:forEach>
</table>

<%--For displaying Previous link except for the 1st page --%>
<c:if test="${currentPage != 1}">
    <td><a href="guild?page=${currentPage - 1}">Previous</a></td>
</c:if>

<%--For displaying Page numbers.
   The when condition does not display a link for the current page--%>
<table border="1" cellpadding="5" cellspacing="5">
    <tr>
        <c:forEach begin="1" end="${noOfPages}" var="i">
            <c:choose>
                <c:when test="${currentPage eq i}">
                    <td>${i}</td>
                </c:when>
                <c:otherwise>
                    <td><a href="guild?page=${i}">${i}</a></td>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </tr>
</table>

<%--For displaying Next link --%>
<c:if test="${currentPage lt noOfPages}">
    <td><a href="guild?page=${currentPage + 1}">Next</a></td>
</c:if>

<%--For displaying Page numbers.
The when condition does not display a link for the current page--%>
<c:if test="${errorMessage != null}">
    ${error}
</c:if>
</body>

