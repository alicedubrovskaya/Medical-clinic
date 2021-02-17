<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: Алиса
  Date: 17.01.2021
  Time: 11:51
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
<fmt:message bundle="${textResources}" key="patient.list" var="patient_list"/>
<fmt:message bundle="${textResources}" key="patient.address" var="patient_address"/>
<fmt:message bundle="${textResources}" key="patient.email" var="patient_email"/>
<fmt:message bundle="${textResources}" key="patient.name" var="patient_name"/>
<fmt:message bundle="${textResources}" key="patient.surname" var="patient_surname"/>


<u:html title="${patient_list}" message="${message}">
    <div class="container">
        <div class="main-head">
            <h2>${patient_list}</h2>
        </div>
        <table class="table table-hover">
            <thead>
            <tr>
                <th>${patient_surname}</th>
                <th>${patient_surname}</th>
                <th>${patient_email}</th>
                <th>${patient_phoneNumber}</th>
                <th>${patient_address}</th>
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
    <u:pagination entity="patient"/>

    <%--<form action="/patient/edit.html">--%>
    <%--    <button type="submit">Добавить пациента</button>--%>
    <%--</form>--%>
</u:html>