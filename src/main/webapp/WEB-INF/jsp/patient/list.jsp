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

    <script type="text/javascript" src="/js/test.js"></script>
    <script type="text/javascript" src="/js/main.js"></script>
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

    <c:url value="/patient/edit.html" var="patientEditUrl"/>
    <c:forEach var="patient" items="${patients}" varStatus="status">
        <tr onclick="submitFormById('form-${patient.id}')">
            <td>
                    ${patient.surname}
                <form id="form-${patient.id}" action="${patientEditUrl}" method="post">
                    <input type="hidden" name="id" value="${patient.id}">
                </form>
            </td>
            <td>${patient.name}</td>
            <td>${patient.email}</td>
            <td>${patient.phoneNumber}</td>
            <td>${patient.address}</td>
        </tr>
    </c:forEach>

</table>


<%--<form action="/patient/edit.html">--%>
<%--    <button type="submit">Добавить пациента</button>--%>
<%--</form>--%>

</body>
</html>
