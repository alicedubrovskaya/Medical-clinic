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

    <fmt:formatDate value="${appointment.time}" var="dateFormat" pattern="yyyy-MM-dd HH:mm:ss"/>
    <p>Время:
        <output name="doctor">${dateFormat}</output>
    </p>

    <input type="hidden" name="appointmentId" value="${appointment.id}">

    <c:choose>
        <c:when test="${not empty appointment.patient}">
            <output name="patient" onclick="submitFormById('form-${appointment.patient.id}')">
                Пациент: ${appointment.patient.surname} ${appointment.patient.name}
                <form id="form-${appointment.patient.id}" action="/patient/edit.html" method="post">
                    <input type="hidden" name="id" value="${appointment.patient.id}">
                </form>
            </output>

        </c:when>
    </c:choose>

    <form action="/appointment/save.html" method="post">

        <c:choose>
            <c:when test="${not empty appointment.patient}">
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
            </c:when>

            <c:otherwise>
                <p>Пациент на данное время не записан </p>
            </c:otherwise>

        </c:choose>
    </form>

</u:html>
