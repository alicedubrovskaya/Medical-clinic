<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Алиса
  Date: 16.01.2021
  Time: 18:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:choose>
    <c:when test="${not empty vacation}">
        <c:set var="start" value="${vacation.start}"/>
        <c:set var="end" value="${vacation.end}"/>
        <c:set var="doctorName" value="${vacation.doctor.name}"/>
        <c:set var="doctorSurname" value="${vacation.doctor.surname}"/>
        <c:set var="title" value="${vacation.doctor.name} ${vacation.doctor.surname}, отпуск"/>
    </c:when>
    <c:otherwise>
        <c:set var="title" value="Добавление отпуска"/>
    </c:otherwise>
</c:choose>
<html>
<head>
    <title>Отпуска</title>

    <script type="text/javascript" src="/js/test.js"></script>
    <script type="text/javascript" src="/js/main.js"></script>
    <script type="text/javascript" src="/js/validator.js"></script>
    <script type="text/javascript" src="/js/validator-of-edit-vacation-form.js"></script>
</head>
<body>

<H2>${title}</H2>

<form action="/vacation/save.html" method="post" onsubmit="return validateEditVacation(this)">
    <c:if test="${not empty id}">
        <input type="hidden" name="id" value="${id}">
    </c:if>

    <label for="surname">Фамилия:</label>
    <input type="text" id="surname" name="surname" value="${doctorSurname}">

    <label for="name">Имя:</label>
    <input type="text" id="name" name="name" value="${doctorName}">

    <label for="start">Дата начала:</label>
    <input type="date" id="start" name="vacation-start" value="2021-01-01" min="2021-01-01" value="${start}">

    <label for="end">Дата окончания:</label>
    <input type="date" id="end" name="vacation-end" value="2021-01-01" min="2021-01-01" value="${end}">

    <button type="submit">Сохранить</button>
    <button type="reset">Сбросить</button>
</form>



<c:if test="${not empty vacation}">
    <form action="/vacation/delete.html" method="post" onsubmit="deleteConfirmation(this);return false;">
        <input type="hidden" name="id" value="${id}">
        <input type="submit" value="Удалить">
    </form>
</c:if>
</body>
</html>