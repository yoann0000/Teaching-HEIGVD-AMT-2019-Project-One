<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<h3>Welcome ${sessionScope.adventurer.name}</h3>
    <table>
        <form method="POST" action="${pageContext.request.contextPath}/home">
            <tr>
                <td>Level</td>
                <td>${sessionScope.adventurer.getLevel()}</td>
                <td></td>
            </tr>
            <tr>
                <td>Gold</td>
                <td>${sessionScope.adventurer.gold}</td>
                <td></td>
            </tr>
            <tr>
                <td>strength</td>
                <td>${sessionScope.adventurer.str}</td>
                <td>
                    <c:if test="${sessionScope.adventurer.getremainingpoint() > 0}">
                        <button onclick="this.form.submited=this.value;"  type="submit" name="stat" value="str">+</button>
                    </c:if>
                </td>
            </tr>
            <tr>
                <td>dexterity</td>
                <td>${sessionScope.adventurer.dex}</td>
                <td>
                    <c:if test="${sessionScope.adventurer.getremainingpoint() > 0}">
                        <button onclick="this.form.submited=this.value;"  type="submit" name="stat" value="dex">+</button>
                    </c:if>
                </td>
            </tr>
            <tr>
                <td>constitution</td>
                <td>${sessionScope.adventurer.con}</td>
                <td>
                    <c:if test="${sessionScope.adventurer.getremainingpoint() > 0}">
                        <button onclick="this.form.submited=this.value;"  type="submit" name=stat"" value="con">+</button>
                    </c:if>
                </td>
            </tr>
            <tr>
                <td>intelligence</td>
                <td>${sessionScope.adventurer.inte}</td>
                <td>
                    <c:if test="${sessionScope.adventurer.getremainingpoint() > 0}">
                        <button onclick="this.form.submited=this.value;"  type="submit" name="stat" value="inte">+</button>
                    </c:if>
                </td>
            </tr>
            <tr>
                <td>wisdom</td>
                <td>${sessionScope.adventurer.wis}</td>
                <td>
                    <c:if test="${sessionScope.adventurer.getremainingpoint() > 0}">
                        <button onclick="this.form.submited=this.value;"  type="submit" name="stat" value="wis">+</button>
                    </c:if>
                </td>
            </tr>
            <tr>
                <td>charisma</td>
                <td>${sessionScope.adventurer.cha}</td>
                <td>
                    <c:if test="${sessionScope.adventurer.getremainingpoint() > 0}">
                        <button onclick="this.form.submited=this.value;"  type="submit" name="stat" value="cha">+</button>
                    </c:if>
                </td>
            </tr>
            <tr>
                <td>experience</td>
                <td>${sessionScope.adventurer.experience}</td>
                <td></td>
            </tr>
            <tr>
                <td>race</td>
                <td>${sessionScope.adventurer.race}</td>
                <td></td>
            </tr>
            <tr>
                <td>classe</td>
                <td>${sessionScope.adventurer.klass}</td>
                <td></td>
            </tr>
            <tr>
                <td>remaining points</td>
                <td>${sessionScope.adventurer.getremainingpoint()}</td>
                <td></td>
            </tr>
        </form>
    </table>
    <h3>Guild</h3>
    <p>
        <c:choose>
            <c:when test="${guild != null}">
                <form method="POST" action="${pageContext.request.contextPath}/guild">
                    <input onclick="this.form.submited=this.value;"  type="submit" name="guild" value="${guild.name}"/>
                </form>
            </c:when>
            <c:otherwise>
                <p>You don't belong to any guild right now</p>
            </c:otherwise>
        </c:choose>
    </p>
    <h3>Parties</h3>
    <table>
        <c:choose>
            <c:when test="${parties.size() > 0}">
                <c:forEach var="party" items="${parties}">
                    <tr>
                        <td>
                            <form method="POST" action="${pageContext.request.contextPath}/party">
                                <input onclick="this.form.submited=this.value;" name="party" type="submit" value= "${party.name}" />
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </c:when>
            <c:otherwise>
               <p>You don't belong to any party right now</p>
            </c:otherwise>
        </c:choose>
    </table>
    <p>
        <form method="POST" action="${pageContext.request.contextPath}/home">
            <button onclick="this.form.submited=this.value;"  type="submit" name="delete" value="delete">
                Delete my account
            </button>
        </form>
    </p>
</body>
</html>