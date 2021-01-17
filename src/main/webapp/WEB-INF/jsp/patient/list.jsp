<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Алиса
  Date: 17.01.2021
  Time: 11:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Список пациентов</title>
</head>
<body>

<table border="1">
    <tr>
        <th>Фамилия</th>
        <th>Имя</th>
        <th>Почта</th>
        <th>Номер телефона</th>
        <th>Адрес проживания</th>
    </tr>

    <c:forEach var="patient" items="${patients}" varStatus="status">
    <tr>
        <td>${patient.surname}</td>
        <td>${patient.name}</td>
        <td>${patient.email}</td>
        <td>${patient.phoneNumber}</td>
        <td>${patient.address}</td>
    </tr>
    </c:forEach>

<%--    <c:url value="/doctor/edit.html" var="doctorEditUrl"/>--%>
<%--    <c:forEach var="doctor" items="${doctors}" varStatus="status">--%>
<%--        <tr onclick="submitFormById('form-${doctor.id}')">--%>
<%--            <td>--%>
<%--                    ${doctor.surname}--%>
<%--                <form id="form-${doctor.id}" action="${doctorEditUrl}" method="post">--%>
<%--                    <input type="hidden" name="id" value="${doctor.id}">--%>
<%--                </form>--%>
<%--            </td>--%>
<%--            <td>${doctor.name}</td>--%>
<%--            <td>${doctor.specialization}</td>--%>
<%--            <td>${doctor.workingShift.name}</td>--%>
<%--        </tr>--%>
<%--    </c:forEach>--%>
</table>

</body>
</html>
