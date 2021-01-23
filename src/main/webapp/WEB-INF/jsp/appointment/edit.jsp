<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%--
  Created by IntelliJ IDEA.
  User: Алиса
  Date: 19.01.2021
  Time: 16:42
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
<fmt:message bundle="${textResources}" key="appointment.edition" var="appointment_edition"/>
<fmt:message bundle="${textResources}" key="patient" var="patient_language"/>
<fmt:message bundle="${textResources}" key="appointment.complaints" var="appointment_complaints"/>
<fmt:message bundle="${textResources}" key="appointment.recommendations" var="appointment_recommendations"/>
<fmt:message bundle="${textResources}" key="appointment.medicalReport" var="appointment_medicalReport"/>
<fmt:message bundle="${textResources}" key="button.save" var="button_save"/>
<fmt:message bundle="${textResources}" key="button.reset" var="button_reset"/>
<fmt:message bundle="${textResources}" key="appointment.whithoutPatient" var="withoutPatient"/>



<%--TODO validatior--%>
<u:html title="${appointment_edition}" message="${message}">
    <div class="container">
        <h2>${appointment_edition}</h2>

        <div class="form-group row">
            <label for="dateFormat" class="col-sm-2 col-form-label">Время:</label>
            <div class="col-sm-10">
                <fmt:formatDate value="${appointment.time}" var="dateFormat" pattern="yyyy-MM-dd HH:mm:ss"/>
                <input type="text" readonly class="form-control-plaintext" id="dateFormat" value="${dateFormat}">
            </div>
        </div>
        <input type="hidden" name="appointmentId" value="${appointment.id}">

        <div class="form-group row">
            <label for="patient" class="col-sm-2 col-form-label">Пациент:</label>
            <div class="col-sm-10">
                <c:choose>
                    <c:when test="${not empty appointment.patient}">
                        <input type="text" readonly class="form-control-plaintext" id="patient"
                               onclick="submitFormById('form-${appointment.patient.id}')"
                               value="${appointment.patient.surname} ${appointment.patient.name}">
                        <form id="form-${appointment.patient.id}" action="/patient/edit.html" method="post">
                            <input type="hidden" name="id" value="${appointment.patient.id}">
                        </form>
                        </input>
                    </c:when>
                </c:choose>
            </div>
        </div>

        <form action="/appointment/save.html" method="post">
            <c:choose>
                <c:when test="${not empty appointment.patient}">
                    <input type="hidden" name="patientId" value="${appointment.patient.id}">
                    <input type="hidden" name="appointmentId" value="${appointment.id}">


                    <select class="form-control" id="status" name="status">
                        <c:forEach items="${statuses}" var="status">
                            <option value="${status}">${status}</option>
                        </c:forEach>
                    </select>

                    <div class="form-group">
                        <label for="complaints">${appointment_complaints}:</label>
                        <input type="text" class="form-control" id="complaints" name="complaints"
                               value="${appointment.complaints}">
                    </div>
                    <div class="form-group>">
                        <label for="medicalReport">${appointment_medicalReport}:</label>
                        <input type="text" class="form-control" id="medicalReport" name="medicalReport"
                               value="${appointment.medicalReport}">

                    </div>
                    <div class="form-group">
                        <label for="recommendation">${appointment_recommendations}:</label>
                        <input type="text" class="form-control" id="recommendation" name="recommendation"
                               value="${appointment.recommendation}">
                    </div>
                    <div class="btn-group">
                        <div class="btn-group">
                            <input type="submit" class="btn btn-success" value="${button_save}">
                            <input type="reset" class="btn btn-warning" value="${button_reset}">
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <p class="text-info">${withoutPatient}</p>
                </c:otherwise>
            </c:choose>
        </form>
    </div>
</u:html>
