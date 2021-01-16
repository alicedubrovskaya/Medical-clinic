<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Алиса
  Date: 14.01.2021
  Time: 13:58
  To change this template use File | Settings | File Templates.
--%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Врачи</title>
    <script type="text/javascript" src="/js/test.js"></script>
    <script type="text/javascript" src="/js/main.js"></script>
</head>
<body>
<h2>Список врачей</h2>

<%--<form action="/doctor/find.html" method="post">--%>
<%--    <p><input type=search placeholder="Введите информацию для поиска">--%>
<%--        <input type="submit" value="Искать"></p>--%>
<%--</form>--%>

<table border="1">
    <tr>
        <th>Фамилия</th>
        <th>Имя</th>
        <th>Специализация</th>
        <th>Смена рабочая</th>
    </tr>

    <c:url value="/doctor/edit.html" var="doctorEditUrl"/>
    <c:forEach var="doctor" items="${doctors}" varStatus="status">
        <tr onclick="submitFormById('form-${doctor.id}')">
            <td>
                    ${doctor.surname}
                <form id="form-${doctor.id}" action="${doctorEditUrl}" method="post">
                    <input type="hidden" name="id" value="${doctor.id}">
                </form>
            </td>
            <td>${doctor.name}</td>
            <td>${doctor.specialization}</td>
            <td>${doctor.workingShift.name}</td>
        </tr>
    </c:forEach>
</table>

<form action="/doctor/edit.html">
    <button type="submit">Добавить врача</button>
</form>

</body>
</html>
