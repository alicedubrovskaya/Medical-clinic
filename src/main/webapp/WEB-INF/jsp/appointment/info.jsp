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

<u:html title="Запись на прием к врачу" message="${message}">
    <div class="container">
        <form>
            <div class="form-group row">
                <label for="time" class="col-sm-2 col-form-label">Дата приема:</label>
                <div class="col-sm-10">
                    <fmt:formatDate value="${appointment.time}" var="timeFormat" pattern="yyyy-MM-dd HH:mm:ss"/>
                    <input type="text" readonly class="form-control-plaintext" id="time" value="${timeFormat}">
                </div>
            </div>
            <div class="form-group row">
                <label for="doctor" class="col-sm-2 col-form-label">Врач:</label>
                <div class="col-sm-10">
                    <input type="text" readonly class="form-control-plaintext" id="doctor"
                           value="${appointment.doctor.surname} ${appointment.doctor.name}">
                </div>
            </div>
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
                        <c:otherwise>
                            <input type="text" readonly class="form-control-plaintext" id="patient" value="Не записан">
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </form>
        <div class="btn-group">
            <c:if test="${authorizedUser.role.name =='Пациент'}">
                <form action="/appointment/save.html" method="post">
                    <input type="hidden" name="appointmentId" value="${appointment.id}">
                    <input type="submit" class="btn btn-success" value="Записаться">
                </form>
            </c:if>

            <c:if test="${authorizedUser.role.name =='Врач'}">
                <form action="/appointment/edit.html" method="post">
                    <input type="hidden" name="appointmentId" value="${appointment.id}">
                    <input type="submit" class="btn btn-default" value="Редактировать">
                </form>
            </c:if>
        </div>
    </div>

</u:html>