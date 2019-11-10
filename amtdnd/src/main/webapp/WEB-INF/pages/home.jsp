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
                    <button onclick="this.form.submited=this.value;"  type="submit" name="str" value="">+</button>
                </td>
            </tr>
            <tr>
                <td>dexterity</td>
                <td>${sessionScope.adventurer.dex}</td>
                <td>
                    <button onclick="this.form.submited=this.value;"  type="submit" name="dex" value="">+</button>
                </td>
            </tr>
            <tr>
                <td>constitution</td>
                <td>${sessionScope.adventurer.con}</td>
                <td>
                    <button onclick="this.form.submited=this.value;"  type="submit" name="con" value="">+</button>
                </td>
            </tr>
            <tr>
                <td>intelligence</td>
                <td>${sessionScope.adventurer.inte}</td>
                <td>
                    <button onclick="this.form.submited=this.value;"  type="submit" name="inte" value="">+</button>
                </td>
            </tr>
            <tr>
                <td>wisdom</td>
                <td>${sessionScope.adventurer.wis}</td>
                <td>
                    <button onclick="this.form.submited=this.value;"  type="submit" name="wis" value="">+</button>
                </td>
            </tr>
            <tr>
                <td>charisma</td>
                <td>${sessionScope.adventurer.cha}</td>
                <td>
                    <button onclick="this.form.submited=this.value;"  type="submit" name="cha" value="">+</button>
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
    <form method="POST" action="${pageContext.request.contextPath}/guild">
        <button onclick="this.form.submited=this.value;"  type="submit" name="guild" value="${guild.name}"/>
    </form>
    </p>
    <h3>Parties</h3>
    <table>
        <c:forEach var="party" items="${parties}">
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
</body>
</html>