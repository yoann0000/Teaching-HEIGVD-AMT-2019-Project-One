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
    <title>Parties</title>
</head>
<body>
<h3>Existing Partiess</h3>

<table>
    <form method="POST" action="${pageContext.request.contextPath}/party">
        <tr>
            <input type="text" name="newParty"/>
            <button onclick="this.form.submited=this.value;"  type="submit" name="party" value="">Create a new party</button>
        </tr>
    </form>
</table>

<table border="1" cellpadding="5" cellspacing="5">
    <tr>
        <th>Name</th>
        <th>Reputation</th>
    </tr>

    <c:forEach var="party" items="${partyList}">
        <tr>
            <td>
                <form method="POST" action="${pageContext.request.contextPath}/party">
                    <input onclick="this.form.submited=this.value;" name="party" type="submit" value= "${party.name}" />
                </form>
            </td>
            <td>${party.reputation}</td>
        </tr>
    </c:forEach>
</table>

<%--For displaying Previous link except for the 1st page --%>
<c:if test="${currentPage != 1}">
    <td><a href="party?page=${currentPage - 1}">Previous</a></td>
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
                    <td><a href="party?page=${i}">${i}</a></td>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </tr>
</table>

<%--For displaying Next link --%>
<c:if test="${currentPage lt noOfPages}">
    <td><a href="party?page=${currentPage + 1}">Next</a></td>
</c:if>

<%--For displaying Page numbers.
The when condition does not display a link for the current page--%>
<c:if test="${errorMessage != null}">
    ${error}
</c:if>
</body>

