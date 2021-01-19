<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
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
<%--<html>--%>
<%--<head>--%>
<%--    <title>"${title}"</title>--%>
<%--    <script type="text/javascript" src="/js/test.js"></script>--%>
<%--    <script type="text/javascript" src="/js/main.js"></script>--%>
<%--    <script type="text/javascript" src="/js/validator.js"></script>--%>
<%--    <script type="text/javascript" src="/js/validator-of-edit-doctor-form.js"></script>--%>

<%--</head>--%>
<%--<body>--%>

<u:html title="${title}" message="${message}" validator="validator-of-edit-doctor-form.js">
    <H2>${title}</H2>
    <form action="/doctor/save.html" method="post" onsubmit="return validateEditDoctor(this)">
        <c:if test="${not empty doctor}">
            <input type="hidden" name="id" value="${doctor.id}">
        </c:if>
        <label for="surname">Фамилия:</label>
        <input type="text" id="surname" name="surname" value="${surname}">

        <label for="name">Имя:</label>
        <input type="text" id="name" name="name" value="${name}">

        <label for="specialization">Специализация:</label>
        <input type="text" id="specialization" name="specialization" value="${specialization}">

        <label for="workingShift">Рабочая смена:</label>
        <input type="text" id="workingShift" name="workingShift" value="${workingShift}">

        <button type="submit">Сохранить</button>
        <button type="reset">Сбросить</button>
    </form>

    <%--    TODO js--%>
    <c:if test="${not empty doctor}">
        <form action="/doctor/delete.html" method="post" onsubmit="deleteConfirmation(this);return false;">
            <input type="hidden" name="id" value="${doctor.id}">
            <input type="submit" value="Удалить">
        </form>
    </c:if>

<%--    <c:if test="${not empty doctor}">--%>
<%--        <form action="/vacation/edit.html" method="post">--%>
<%--            <input type="hidden" name="id" value="${doctor.id}">--%>
<%--            <button type="submit">Добавить отпуск</button>--%>
<%--        </form>--%>
<%--    </c:if>--%>

</u:html>