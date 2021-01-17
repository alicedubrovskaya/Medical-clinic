<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Алиса
  Date: 17.01.2021
  Time: 12:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:choose>
    <c:when test="${not empty patient}">
        <c:set var="surname" value="${patient.surname}"/>
        <c:set var="name" value="${patient.name}"/>
        <c:set var="email" value="${patient.email}"/>
        <c:set var="phoneNumber" value="${patient.phoneNumber}"/>
        <c:set var="address" value="${patient.address}"/>
        <c:set var="title" value="Пациент ${patient.surname} ${patient.name}"/>
    </c:when>
    <c:otherwise>
        <c:set var="title" value="Новый пациент"/>
    </c:otherwise>
</c:choose>
<html>
<head>
    <title>"${title}"</title>
</head>
<body>

<H2>${title}</H2>
<%--<form action="/patient/save.html" method="post" onsubmit="return validateEditDoctor(this)">--%>
<form action="/patient/save.html" method="post">

    <c:if test="${not empty patient}">
        <input type="hidden" name="id" value="${patient.id}">
    </c:if>
    <label for="surname">Фамилия:</label>
    <input type="text" id="surname" name="surname" value="${surname}">

    <label for="name">Имя:</label>
    <input type="text" id="name" name="name" value="${name}">

    <label for="email">Почта:</label>
    <input type="text" id="email" name="email" value="${email}">

    <label for="phoneNumber">Номер телефона:</label>
    <input type="text" id="phoneNumber" name="phoneNumber" value="${phoneNumber}">

    <label for="address">Адрес проживания:</label>
    <input type="text" id="address" name="address" value="${address}">

    <button type="submit">Сохранить</button>
    <button type="reset">Сбросить</button>
</form>

</body>
</html>
