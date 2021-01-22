<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>

<%--
  Created by IntelliJ IDEA.
  User: Алиса
  Date: 17.01.2021
  Time: 11:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<u:html title="Список пациентов" message="${message}">
    <div class="container">
        <h2>Список пациентов</h2>
        <table class="table table-hover">
            <thead>
            <tr>
                <th>Фамилия</th>
                <th>Имя</th>
                <th>Почта</th>
                <th>Номер телефона</th>
                <th>Адрес проживания</th>
            </tr>
            </thead>

            <tbody>
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
            </tbody>

        </table>

    </div>

    <%--<form action="/patient/edit.html">--%>
    <%--    <button type="submit">Добавить пациента</button>--%>
    <%--</form>--%>
</u:html>