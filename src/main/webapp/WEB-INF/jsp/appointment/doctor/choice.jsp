<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: Алиса
  Date: 19.01.2021
  Time: 14:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<u:html title="Прием пациентов" message="${message}">
    <h2>Прием пациентов</h2>

    <form action="/appointment/list.html" method="post">

        <label for="date">Дата:</label>
        <input type="date" id="date" name="date">

        <script>
            getDate();
        </script>

        <select id="status" name="status">
            <c:forEach items="${statuses}" var="status">
                <option value="${status}">${status}</option>
            </c:forEach>
        </select>

        <input type="submit" value=Просмотреть>
    </form>
</u:html>