<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--
  Created by IntelliJ IDEA.
  User: Алиса
  Date: 17.01.2021
  Time: 19:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<u:html title="Запись на прием к врачу" message="${message}">
    <p>Дата приема:
        <fmt:formatDate value="${appointment.time}" var="timeFormat" pattern="yyyy-MM-dd HH:mm:ss"/>
        <output name="time">${timeFormat}</output>
    </p>

    <p>Врач:
        <output name="doctor">${appointment.doctor.surname} ${appointment.doctor.name}</output>
    </p>

    <p>
    <c:choose>
        <c:when test="${not empty appointment.patient}">
            <output name="patient" onclick="submitFormById('form-${appointment.patient.id}')">
                Пациент:${appointment.patient.surname} ${appointment.patient.name}

                <form id="form-${appointment.patient.id}" action="/patient/edit.html" method="post">
                    <input type="hidden" name="id" value="${appointment.patient.id}">
                </form>
            </output>
        </c:when>
        <c:otherwise>
            <p>Пациент не записан</p>
        </c:otherwise>
    </c:choose>
    </p>

    <c:if test="${authorizedUser.role.name =='Пациент'}">
        <form action="/appointment/save.html" method="post">
            <input type="hidden" name="appointmentId" value="${appointment.id}">
            <input type="submit" value="Записаться">
        </form>
    </c:if>

    <c:if test="${authorizedUser.role.name =='Врач'}">
        <form action="/appointment/edit.html" method="post">
            <input type="hidden" name="appointmentId" value="${appointment.id}">
            <input type="submit" value="Редактировать">
        </form>
    </c:if>

</u:html>
