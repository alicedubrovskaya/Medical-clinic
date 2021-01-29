<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--
  Created by IntelliJ IDEA.
  User: Алиса
  Date: 17.01.2021
  Time: 12:22
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
<fmt:message bundle="${textResources}" key="patient.phoneNumber" var="patient_phoneNumber"/>
<fmt:message bundle="${textResources}" key="patient.new" var="patient_new"/>
<fmt:message bundle="${textResources}" key="patient" var="patient_language"/>
<fmt:message bundle="${textResources}" key="button.save" var="button_save"/>
<fmt:message bundle="${textResources}" key="button.delete" var="button_delete"/>
<fmt:message bundle="${textResources}" key="button.reset" var="button_reset"/>
<fmt:message bundle="${textResources}" key="appointment.history" var="appointment_history"/>


<c:choose>
    <c:when test="${not empty patient}">
        <c:set var="surname" value="${patient.surname}"/>
        <c:set var="name" value="${patient.name}"/>
        <c:set var="email" value="${patient.email}"/>
        <c:set var="phoneNumber" value="${patient.phoneNumber}"/>
        <c:set var="address" value="${patient.address}"/>
        <c:set var="title" value="${patient_language} ${patient.surname} ${patient.name}"/>
    </c:when>
    <c:otherwise>
        <c:set var="title" value="${patient_new}"/>
    </c:otherwise>
</c:choose>

<u:html title="${title}" message="${message}">
    <div class="container">
        <H2>${title}</H2>
        <form action="/patient/save.html" method="post">
            <c:if test="${not empty patient}">
                <input type="hidden" name="id" value="${patient.id}">
            </c:if>
            <c:if test="${not empty user}">
                <input type="hidden" name="password" value="${user.password}">
                <input type="hidden" name="login" value="${user.login}">
            </c:if>
            <div class="form-group">
                <label for="surname">${patient_surname}:</label>
                <input type="text" pattern="^[A-ZА-Я][a-zа-я]+$" required class="form-control" id="surname"
                       name="surname" value="${surname}">
            </div>
            <div class="form-group">
                <label for="name">${patient_name}:</label>
                <input type="text" pattern="^[A-ZА-Я][a-zа-я]+$" required class="form-control" id="name" name="name"
                       value="${name}">
            </div>
            <div class="form-group">
                <label for="email">${patient_email}:</label>
                <input type="email" required class="form-control" id="email" name="email" value="${email}">
            </div>
            <div class="form-group">
                <label for="phoneNumber">${patient_phoneNumber}:</label>
                <input type="tel" required pattern="375\s[0-9]{2}\s[0-9]{3}\s[0-9]{2}\s[0-9]{2}"
                       placeholder="375 29 894 20 43"
                       class="form-control" id="phoneNumber" name="phoneNumber" value="${phoneNumber}">
            </div>
            <div class="form-group">
                <label for="address">${patient_address}:</label>
                <input type="text" class="form-control" id="address" name="address" value="${address}">
            </div>
            <div class="btn-group">
                <input type="submit" class="btn btn-success" value="${button_save}">
                <input type="reset" class="btn btn-warning" value="${button_reset}">
            </div>
        </form>
        <c:if test="${not empty patient}">
            <c:if test="${authorizedUser.role.name =='Пациент'}">
                <form action="/patient/delete.html" method="post" onsubmit="deleteConfirmation(this);return false;">
                    <input type="hidden" name="id" value="${patient.id}">
                    <input type="submit" class="btn btn-danger" value="${button_delete}">
                </form>
            </c:if>

            <form action="/appointment/medicalCard.html" method="get">
                <input type="hidden" name="id" value="${patient.id}">
                <input type="submit" class="btn btn-default" value="${appointment_history}">
            </form>
        </c:if>


    </div>
</u:html>
