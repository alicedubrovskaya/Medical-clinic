<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: Алиса
  Date: 18.01.2021
  Time: 20:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<u:html title="История посещений врачей" message="${message}">

    <ul>
        <c:forEach var="appointment" items="${appointments}" varStatus="status">
            <li>
                <p>Дата приема:
                    <fmt:formatDate value="${appointment.time}" var="timeFormat" pattern="yyyy-MM-dd HH:mm:ss"/>
                    <output name="time">${timeFormat}</output>
                </p>
                <p>Врач:
                    <output name="doctor">${appointment.doctor.surname} ${appointment.doctor.name}</output>
                </p>
                Профиль:
                <output name="specialization">${appointment.doctor.specialization}</output>
                <p>
                </p>
                <p>
                    Жалобы:
                    <output name="complaints">${appointment.complaints}</output>
                </p>
                <p>
                    Заключение врача:
                    <output name="medicalReport">${appointment.medicalReport}</output>
                </p>
                <p>
                    Рекомендации врача:
                    <output name="recommendation">${appointment.recommendation}</output>
                </p>
            </li>
        </c:forEach>
    </ul>
</u:html>