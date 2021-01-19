<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%--
  Created by IntelliJ IDEA.
  User: Алиса
  Date: 19.01.2021
  Time: 16:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--TODO validatior--%>
<u:html title="Редактирование приема" message="${message}">
    <h2>Редактирование приема</h2>

    <form action="/appointment/save.html" method="post">

        <input type="hidden" name="appointmentId" value="${appointment.id}">

        <fmt:formatDate value="${appointment.time}" var="dateFormat" pattern="yyyy-MM-dd HH:mm:ss"/>
        <p>Время:
            <output name="doctor">${dateFormat}</output>
        </p>

        <p>Пациент:
            <output name="patient">${appointment.patient.surname} ${appointment.patient.name}</output>
        </p>

        <select id="status" name="status">
            <c:forEach items="${statuses}" var="status">
                <option value="${status}">${status}</option>
            </c:forEach>
        </select>

        <label for="complaints">Жалобы:</label>
        <input type="text" id="complaints" name="complaints" value="${appointment.complaints}">

        <label for="medicalReport">Заключение:</label>
        <input type="text" id="medicalReport" name="medicalReport" value="${appointment.medicalReport}">

        <label for="recommendation">Рекоммендации:</label>
        <input type="text" id="recommendation" name="recommendation" value="${appointment.recommendation}">

        <button type="submit">Сохранить</button>
        <button type="reset">Сбросить</button>
    </form>

</u:html>
