<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%--
  Created by IntelliJ IDEA.
  User: Алиса
  Date: 14.01.2021
  Time: 13:58
  To change this template use File | Settings | File Templates.
--%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<c:choose>
    <c:when test="${sessionScope.language != null}">
        <fmt:setLocale value="${sessionScope.language}"/>
    </c:when>
    <c:otherwise>
        <fmt:setLocale value="en"/>
    </c:otherwise>
</c:choose>

<fmt:setBundle basename="textResources" var="textResources" scope="session"/>
<fmt:message bundle="${textResources}" key="doctor.list" var="doctor_list"/>
<fmt:message bundle="${textResources}" key="doctor.name" var="doctor_name"/>
<fmt:message bundle="${textResources}" key="doctor.surname" var="doctor_surname"/>
<fmt:message bundle="${textResources}" key="doctor.specialization" var="doctor_specialization"/>
<fmt:message bundle="${textResources}" key="doctor.working.shift" var="doctor_working_shift"/>


<u:html title="${doctor_list}" message="${message}">
    <div class="container">
        <h2>${doctor_list}</h2>

        <table class="table table-hover">
            <thead>
            <tr>
                <th>${doctor_surname}</th>
                <th>${doctor_name}</th>
                <th>${doctor_specialization}</th>
                <th>${doctor_working_shift}</th>
            </tr>
            </thead>
            <tbody>
            <c:url value="/doctor/edit.html" var="doctorEditUrl"/>
            <c:forEach var="doctor" items="${doctors}" varStatus="status">
                <tr onclick="submitFormById('form-${doctor.id}')">
                    <td>
                            ${doctor.surname}
                        <form id="form-${doctor.id}" action="${doctorEditUrl}" method="post">
                            <input type="hidden" name="id" value="${doctor.id}">
                        </form>
                    </td>
                    <td>${doctor.name}</td>
                    <td>${doctor.specialization}</td>
                    <td>${doctor.workingShift.name}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</u:html>

