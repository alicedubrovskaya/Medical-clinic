<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--
  Created by IntelliJ IDEA.
  User: Алиса
  Date: 17.01.2021
  Time: 19:59
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
<fmt:message bundle="${textResources}" key="appointment.making" var="appointment_making"/>
<fmt:message bundle="${textResources}" key="doctor" var="doctor_language"/>
<fmt:message bundle="${textResources}" key="patient" var="patient_language"/>
<fmt:message bundle="${textResources}" key="appointment.date" var="appointment_date"/>
<fmt:message bundle="${textResources}" key="appointment.whithoutPatient" var="withoutPatient"/>
<fmt:message bundle="${textResources}" key="appointment.make" var="appointment_make"/>
<fmt:message bundle="${textResources}" key="button.edit" var="button_edit"/>
<fmt:message bundle="${textResources}" key="button.save" var="button_save"/>
<fmt:message bundle="${textResources}" key="button.cancel" var="button_cancel"/>


<u:html title="${appointment_making}" message="${message}">
    <div class="container">
        <form name="info">
            <div class="form-group row">
                <label for="time" class="col-sm-2 col-form-label">${appointment_date}:</label>
                <div class="col-sm-10">
                    <fmt:formatDate value="${appointment.time}" var="timeFormat" pattern="yyyy-MM-dd HH:mm:ss"/>
                    <input type="text" readonly class="form-control-plaintext" id="time" value="${timeFormat}">
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
                <label for="patient" class="col-sm-2 col-form-label">${patient_language}:</label>
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
                        <c:otherwise>
                            <input type="text" readonly class="form-control-plaintext" id="patient"
                                   value="${withoutPatient}">
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </form>
        <div class="btn-group">
            <c:if test="${authorizedUser.role.name =='Пациент'}">
                <form action="/appointment/save.html" method="post">
                    <input type="hidden" name="appointmentId" value="${appointment.id}">
                    <input type="submit" class="btn btn-success" value="${button_save}">
                </form>

                <form action="/appointment/choice.html" method="get">
                    <input type="hidden" name="appointmentId" value="${appointment.id}">
                    <input type="submit" class="btn btn-warning" value="${button_cancel}">
                </form>
            </c:if>

            <c:if test="${(not empty appointment.patient)  && (authorizedUser.role.name=='Врач')}">
                <form action="/appointment/edit.html" method="get">
                    <input type="hidden" name="appointmentId" value="${appointment.id}">
                    <input type="submit" class="btn btn-warning" value="${button_edit}">
                </form>
            </c:if>
        </div>
    </div>

</u:html>