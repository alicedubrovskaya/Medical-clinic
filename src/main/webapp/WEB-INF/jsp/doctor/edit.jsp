<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Алиса
  Date: 14.01.2021
  Time: 14:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:choose>
    <c:when test="${not empty doctor}">
        <c:set var="surname" value="${doctor.surname}"/>
        <c:set var="name" value="${doctor.name}"/>
        <c:set var="specialization" value="${doctor.specialization}"/>
        <c:set var="workingShift" value="${doctor.workingShift.name}"/>
        <c:set var="title" value="Врач ${doctor.surname} ${doctor.name}"/>
    </c:when>
    <c:otherwise>
        <c:set var="title" value="Новый врач"/>
    </c:otherwise>
</c:choose>
<html>
<head>
    <title>"${title}"</title>
</head>
<body>
<form action="clinic" method="post">
    <label for="surname">Фамилия:</label>
    <input type="text" id="surname" name="surname" value="${surname}">

    <label for="name">Имя:</label>
    <input type="text" id="name" name="name" value="${name}">

    <label for="specialization">Специализация:</label>
    <input type="text" id="specialization" name="specialization" value="${specialization}">

    <label for="workingShift">Специализация:</label>
    <input type="text" id="workingShift" name="working_shift" value="${workingShift}">

    <button type="submit">Сохранить</button>
</form>

</body>
</html>
