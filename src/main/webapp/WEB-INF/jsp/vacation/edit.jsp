<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--
  Created by IntelliJ IDEA.
  User: Алиса
  Date: 16.01.2021
  Time: 18:38
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
<fmt:message bundle="${textResources}" key="doctor.list" var="doctor_list"/>
<fmt:message bundle="${textResources}" key="doctor.name" var="doctor_name"/>
<fmt:message bundle="${textResources}" key="doctor.surname" var="doctor_surname"/>
<fmt:message bundle="${textResources}" key="doctor.specialization" var="doctor_specialization"/>
<fmt:message bundle="${textResources}" key="doctor.working.shift" var="doctor_working_shift"/>
<fmt:message bundle="${textResources}" key="vacation.list" var="vacation_list"/>
<fmt:message bundle="${textResources}" key="date.start" var="date_start"/>
<fmt:message bundle="${textResources}" key="date.end" var="date_end"/>
<fmt:message bundle="${textResources}" key="vacation.adding" var="vacaion_adding"/>
<fmt:message bundle="${textResources}" key="vacation.edition" var="vacaion_edition"/>
<fmt:message bundle="${textResources}" key="button.save" var="button_save"/>
<fmt:message bundle="${textResources}" key="button.delete" var="button_delete"/>
<fmt:message bundle="${textResources}" key="button.reset" var="button_reset"/>


<c:choose>
    <c:when test="${not empty vacation}">
        <c:set var="start" value="${vacation.start}"/>
        <c:set var="end" value="${vacation.end}"/>
        <c:set var="doctorName" value="${vacation.doctor.name}"/>
        <c:set var="doctorSurname" value="${vacation.doctor.surname}"/>
        <c:set var="title" value="${vacaion_edition}"/>
    </c:when>
    <c:otherwise>
        <c:set var="title" value="${vacaion_adding}"/>
    </c:otherwise>
</c:choose>

<u:html title="${title}" message="${message}">
    <div class="container">
        <H2>${title}</H2>
        <form action="/vacation/save.html" method="post">
            <c:if test="${not empty id}">
                <input type="hidden" name="id" value="${id}">
            </c:if>
            <div class="form-group">
                <label for="surname">${doctor_surname}:</label>
                <input type="text" pattern="^[A-ZА-Я][a-zа-я]+$" required class="form-control" id="surname"
                       name="surname" value="${doctorSurname}">
            </div>
            <div class="form-group">
                <label for="name">${doctor_name}:</label>
                <input type="text" pattern="^[A-ZА-Я][a-zа-я]+$" required class="form-control" id="name" name="name"
                       value="${doctorName}">
            </div>
            <div class="form-group">
                <label for="start">${date_start}:</label>
                <input type="date" required class="form-control" id="start" name="vacation-start" min="2021-01-01"
                       value="${start}">
            </div>
            <div class="form-group">
                <label for="end">${date_end}:</label>
                <input type="date" required class="form-control" id="end" name="vacation-end" min="2021-01-01"
                       value="${end}">
            </div>
            <div class="btn-group">
                <input type="submit" class="btn btn-success" value="${button_save}">
                <input type="reset" class="btn btn-warning" value="${button_reset}">
            </div>
        </form>

        <c:if test="${not empty vacation}">
            <form action="/vacation/delete.html" method="post" onsubmit="deleteConfirmation(this);return false;">
                <input type="hidden" name="id" value="${id}">
                <input type="submit" class="btn btn-danger" value="${button_delete}">
            </form>
        </c:if>
    </div>
</u:html>