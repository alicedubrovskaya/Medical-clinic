<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%--
  Created by IntelliJ IDEA.
  User: Алиса
  Date: 17.01.2021
  Time: 19:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<html>--%>
<%--<head>--%>
<%--    <title>Прием к врачу</title>--%>

<%--    <script type="text/javascript" src="/js/test.js"></script>--%>
<%--    <script type="text/javascript" src="/js/main.js"></script>--%>
<%--</head>--%>
<%--<body>--%>


<u:html title="Запись на прием к врачу" message="${message}">
    <p>Дата приема:
        <fmt:formatDate value="${appointment.time}" var="timeFormat" pattern="yyyy-MM-dd HH:mm:ss"/>
        <output name="time">${timeFormat}</output>
    </p>

    <p>Врач:
        <output name="doctor">${appointment.doctor.surname} ${appointment.doctor.name}</output>
    </p>

    <form action="/appointment/save.html" method="post">
        <input type="hidden" name="appointmentId" value="${appointment.id}">
        <input type="submit" value="Записаться">
    </form>
</u:html>
