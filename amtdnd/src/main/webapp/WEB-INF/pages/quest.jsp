<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Quest</title>
</head>
<body>
<c:if test="">

</c:if>
<c:choose>
    <c:when test="${guild != null}">
        You must join a guild to get Quests.
        <a href="${pageContext.request.contextPath}/guild">Join a guild.</a>
    </c:when>
    <c:otherwise>
        <form method="POST" action="${pageContext.request.contextPath}/quest">
            <input type="hidden" name="ac" value="get" />
            <table>
                <tr>
                    <td>Get a new quest</td>
                    <td>
                        <label>
                            <select name="quest">
                                <c:forEach var="quests" items="${guildQuests}">
                                    <option value="quest"> ${var.objective} </option>
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
        <br/>
        <form method="POST" action="${pageContext.request.contextPath}/quest">
            <input type="hidden" name="ac" value="do" />
            <table>
                <tr>
                    <td>Get a new quest</td>
                    <td>
                        <label>
                            <select name="quest">
                                <c:forEach var="quests" items="${userQuests}">
                                    <option value="quest"> ${var.objective} </option>
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
