<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Quest</title>
</head>
<body>
<nav>
    <a href="${pageContext.request.contextPath}/home">Home</a>
    <a href="${pageContext.request.contextPath}/guild">Guild</a>
    <a href="${pageContext.request.contextPath}/party">Party</a>
    <a href="${pageContext.request.contextPath}/quest">Quests</a>
    <a href="${pageContext.request.contextPath}/logout">Logout</a>
</nav>
<c:if test="">

</c:if>
<c:choose>
    <c:when test="${guild == null}">
        You must join a guild to get Quests.
        <a href="${pageContext.request.contextPath}/guild">Join a guild.</a>
    </c:when>
    <c:otherwise>
        <form method="POST" action="${pageContext.request.contextPath}/quest">
            <table>
                <tr>
                    <td>Choose your party.</td>
                    <td>
                        <label>
                            <select required name="parties">
                                <c:forEach var="party" items="${userParties}">
                                    <option value="party"> ${party.name} </option>
                                </c:forEach>
                            </select>
                        </label>
                    </td>
                </tr>
            </table>
            <p>Take on a new quest.</p>
                <table border="1" cellpadding="5" cellspacing="5">
                    <tr>
                        <th>Objective</th>
                        <th>Do this quest</th>
                    </tr>
                    <c:forEach var="quest" items="${questList}">
                        <tr>
                            <td>
                                <form method="POST" action="${pageContext.request.contextPath}/quest">
                                    <input onclick="this.form.submited=this.value;" name="quest" type="submit" value= "${quest.objective}" />
                                </form>
                            </td>
                            <td><button onclick="this.form.submited=this.value;"  type="submit" name="doit" value="${quest.objective}">Do this quest</button></td>
                        </tr>
                    </c:forEach>
                </table>
                <c:if test="${currentPage != 1}">
                    <td><a href="quest?page=${currentPage - 1}">Previous</a></td>
                </c:if>
                <table border="1" cellpadding="5" cellspacing="5">
                    <tr>
                        <c:forEach begin="1" end="${noOfPages}" var="i">
                            <c:choose>
                                <c:when test="${currentPage eq i}">
                                    <td>${i}</td>
                                </c:when>
                                <c:otherwise>
                                    <td><a href="quest?page=${i}">${i}</a></td>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </tr>
                </table>
                <c:if test="${currentPage lt noOfPages}">
                    <td><a href=quest?page=${currentPage + 1}">Next</a></td>
                </c:if>
        </form>
    </c:otherwise>
</c:choose>
</body>
</html>
