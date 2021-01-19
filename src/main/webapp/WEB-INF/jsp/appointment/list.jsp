<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>

<%--
  Created by IntelliJ IDEA.
  User: Алиса
  Date: 17.01.2021
  Time: 14:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Список приемов</title>

    <script type="text/javascript" src="/js/test.js"></script>
    <script type="text/javascript" src="/js/main.js"></script>
</head>
<body>
<h2>Приемы врачей</h2>

<table border="1">
    <tr>
        <th>Время</th>
        <th>Врач</th>
        <th>Пациент</th>
        <th>Специализация</th>
    </tr>

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
                    <td>Свободно</td>
                </c:otherwise>
            </c:choose>

            <td>${appointment.doctor.specialization}</td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
