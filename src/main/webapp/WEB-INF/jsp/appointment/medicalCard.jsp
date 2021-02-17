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

<c:choose>
    <c:when test="${sessionScope.language != null}">
        <fmt:setLocale value="${sessionScope.language}"/>
    </c:when>
    <c:otherwise>
        <fmt:setLocale value="en"/>
    </c:otherwise>
</c:choose>

<fmt:setBundle basename="textResources" var="textResources" scope="session"/>
<fmt:message bundle="${textResources}" key="appointment.history" var="appointment_history"/>
<fmt:message bundle="${textResources}" key="appointment" var="appointment_language"/>
<fmt:message bundle="${textResources}" key="appointment.date" var="appointment_date"/>
<fmt:message bundle="${textResources}" key="doctor" var="doctor_language"/>
<fmt:message bundle="${textResources}" key="doctor.specialization" var="doctor_specialization"/>
<fmt:message bundle="${textResources}" key="appointment.complaints" var="doctor_complaints"/>
<fmt:message bundle="${textResources}" key="appointment.recommendations" var="doctor_recommendations"/>
<fmt:message bundle="${textResources}" key="appointment.medicalReport" var="doctor_medicalReport"/>
<fmt:message bundle="${textResources}" key="patient.disease" var="patient_disease"/>
<fmt:message bundle="${textResources}" key="patient.diseases" var="patient_diseases"/>

<u:html title="${appointment_history}" message="${message}">
    <div class="container">
        <div class="row">
            <div class="col-sm-8">
                <ul class="list-group">
                    <c:forEach var="appointment" items="${appointments}" varStatus="status">
                        <li class="list-group-item">
                            <h3>${appointment_language}</h3>
                            <div class="form-group row">
                                <label for="time" class="col-sm-2 col-form-label">${appointment_date}:</label>
                                <div class="col-sm-10">
                                    <fmt:formatDate value="${appointment.time}" var="timeFormat"
                                                    pattern="yyyy-MM-dd HH:mm:ss"/>
                                    <input type="text" readonly class="form-control-plaintext" id="time"
                                           value="${timeFormat}">
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="doctor" class="col-sm-2 col-form-label">${doctor_language}:</label>
                                <div class="col-sm-10">
                                    <input type="text" readonly class="form-control-plaintext" id="doctor"
                                           value="${appointment.doctor.surname} ${appointment.doctor.name}">
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="specialization"
                                       class="col-sm-2 col-form-label">${doctor_specialization}:</label>
                                <div class="col-sm-10">
                                    <input type="text" readonly class="form-control-plaintext" id="specialization"
                                           value="${appointment.doctor.specialization}">
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="complaints" class="col-sm-2 col-form-label">${doctor_complaints}:</label>
                                <div class="col-sm-10">
                                    <input type="text" readonly class="form-control-plaintext" id="complaints"
                                           value="${appointment.complaints}">
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="medicalReport"
                                       class="col-sm-2 col-form-label">${doctor_medicalReport}:</label>
                                <div class="col-sm-10">
                                    <input type="text" readonly class="form-control-plaintext" id="medicalReport"
                                           value="${appointment.medicalReport}">
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="recommendation"
                                       class="col-sm-2 col-form-label">${doctor_recommendations}:</label>
                                <div class="col-sm-10">
                                    <input type="text" readonly class="form-control-plaintext" id="recommendation"
                                           value="${appointment.recommendation}">
                                </div>
                            </div>

                            <c:if test="${not empty appointment.disease}">
                                <div class="form-group row">
                                    <label for="disease" class="col-sm-2 col-form-label">${patient_disease}:</label>
                                    <div class="col-sm-10">
                                        <input type="text" readonly class="form-control-plaintext" id="disease"
                                               value="${appointment.disease}">
                                    </div>
                                </div>
                            </c:if>
                        </li>
                    </c:forEach>
                </ul>
            </div>

            <c:if test="${not empty diseases}">
                <div class="col-4">
                    <label for="patient-disease">${patient_diseases}:</label>
                    <ul class="list-group" id="patient-disease">
                        <c:forEach var="disease" items="${diseases}" varStatus="status">
                            <li class="list-group-item">${disease}</li>
                        </c:forEach>
                    </ul>
                </div>
            </c:if>
        </div>
    </div>
</u:html>