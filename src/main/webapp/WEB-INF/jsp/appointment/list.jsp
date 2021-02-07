<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%--
  Created by IntelliJ IDEA.
  User: Алиса
  Date: 17.01.2021
  Time: 14:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:choose>
    <c:when test="${sessionScope.language != null}">
        <fmt:setLocale value="${sessionScope.language}"/>
    </c:when>
    <c:otherwise>
        <fmt:setLocale value="en"/>
    </c:otherwise>
</c:choose>

<fmt:setBundle basename="textResources" var="textResources" scope="session"/>
<fmt:message bundle="${textResources}" key="appointment.list" var="appointment_list"/>
<fmt:message bundle="${textResources}" key="appointment" var="appointment_language"/>
<fmt:message bundle="${textResources}" key="date" var="date"/>
<fmt:message bundle="${textResources}" key="button.find" var="find"/>
<fmt:message bundle="${textResources}" key="time" var="time"/>
<fmt:message bundle="${textResources}" key="doctor" var="doctor"/>
<fmt:message bundle="${textResources}" key="patient" var="patient"/>
<fmt:message bundle="${textResources}" key="doctor.specialization" var="doctor_specialization"/>
<fmt:message bundle="${textResources}" key="appointment.free" var="appointment_isFree"/>

<html>
<head>
    <title>${appointment_list}"> </title>
</head>
<body>
<div class="container">
    <div class="main-head">
        <h4>${appointment_list}</h4>
    </div>
<%--    <c:if test="${authorizedUser.role.name == 'Администратор'}">--%>
<%--        <form action="/appointment/list.html" method="get">--%>
<%--            <label for="date">${date}:</label>--%>
<%--            <input type="date" id="date" name="date">--%>
<%--            <script>--%>
<%--                getDate();--%>
<%--            </script>--%>
<%--            <input type="submit" class="btn btn-success" value="${find}">--%>
<%--        </form>--%>
<%--    </c:if>--%>

    <table class="table table-hover">
        <thead>
        <tr>
            <th>${time}</th>
            <th>${doctor}</th>
            <th>${patient}</th>
            <th>${doctor_specialization}</th>
        </tr>
        </thead>
        <tbody>
        <c:url value="/appointment/info.html" var="appointmentInfoUrl"/>
        <c:forEach var="appointment" items="${appointments}" varStatus="status">
            <tr onclick="submitFormById('form-${appointment.id}')">
                <td>
                    <c:remove var="dateFormat"/>
                    <fmt:formatDate value="${appointment.time}" var="dateFormat" pattern="yyyy-MM-dd HH:mm:ss"/>
                        ${dateFormat}
                    <form id="form-${appointment.id}" action="${appointmentInfoUrl}" method="post">
                        <input type="hidden" name="id" value="${appointment.id}">
                    </form>
                </td>
                <td>${appointment.doctor.surname} ${appointment.doctor.name}</td>

                <c:choose>
                    <c:when test="${not empty appointment.patient}">
                        <td>${appointment.patient.surname} ${appointment.patient.name}</td>
                    </c:when>
                    <c:otherwise>
                        <td>${appointment_isFree}</td>
                    </c:otherwise>
                </c:choose>

                <td>${appointment.doctor.specialization}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>