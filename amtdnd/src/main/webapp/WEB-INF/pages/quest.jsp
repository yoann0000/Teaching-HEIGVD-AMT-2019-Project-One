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
<c:choose>
    <c:when test="${guild != null}">
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
                            <select required name="party">
                                <c:forEach var="parties" items="${userParties}">
                                    <option value="party"> ${var.getName} </option>
                                </c:forEach>
                            </select>
                        </label>
                    </td>
                    <td>Take on a new quest.</td>
                    <td>
                        <label>
                            <select required name="quest">
                                <c:forEach var="quests" items="${userQuests}">
                                    <option value="quest"> ${var.getObjective} </option>
                                </c:forEach>
                            </select>
                        </label>
                    </td>
                </tr>
                <tr>
                    <td colspan ="2">
                        <input type="submit" value= "Submit" />
                    </td>
                </tr>
            </table>
        </form>
    </c:otherwise>
</c:choose>
</body>
</html>
