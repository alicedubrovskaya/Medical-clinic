<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Алиса
  Date: 14.01.2021
  Time: 13:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Врачи</title>
</head>
<body>
<h2>Список врачей</h2>
<table border="1">
    <tr>
        <th>Фамилия</th>
        <th>Имя</th>
        <th>Специализация</th>
        <th>Смена рабочая</th>
    </tr>

    <c:forEach var="doctor" items="${doctors}" varStatus="status">
        <tr>
            <td>${doctor.surname}</td>
            <td>${doctor.name}</td>
            <td>${doctor.specialization}</td>
            <td>${doctor.workingShift.name}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
