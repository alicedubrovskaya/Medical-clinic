<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%--
  Created by IntelliJ IDEA.
  User: Алиса
  Date: 14.01.2021
  Time: 14:44
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
<fmt:message bundle="${textResources}" key="doctor" var="doctor_language"/>
<fmt:message bundle="${textResources}" key="doctor.new" var="doctor_new"/>
<fmt:message bundle="${textResources}" key="button.save" var="button_save"/>
<fmt:message bundle="${textResources}" key="button.delete" var="button_delete"/>
<fmt:message bundle="${textResources}" key="button.reset" var="button_reset"/>
<fmt:message bundle="${textResources}" key="button.cancel" var="button_cancel"/>
<fmt:message bundle="${textResources}" key="account.delete.question" var="delete_question"/>
<fmt:message bundle="${textResources}" key="account.delete" var="delete_account"/>


<fmt:message bundle="${textResources}" key="doctor.working.shift" var="doctor_working_shift"/>


<c:choose>
    <c:when test="${not empty doctor}">
        <c:set var="surname" value="${doctor.surname}"/>
        <c:set var="name" value="${doctor.name}"/>
        <c:set var="specialization" value="${doctor.specialization}"/>
        <c:set var="workingShift" value="${doctor.workingShift.name}"/>
        <c:set var="title" value="${doctor_language} ${doctor.surname} ${doctor.name}"/>
    </c:when>
    <c:otherwise>
        <c:set var="title" value="${doctor_new}"/>
    </c:otherwise>
</c:choose>

<u:html title="${title}" message="${message}" validator="validator-of-edit-doctor-form.js">
    <div class="container">
        <H2>${title}</H2>
        <form action="/doctor/save.html" method="post" onsubmit="return validateEditDoctor(this)">
            <c:if test="${not empty doctor}">
                <input type="hidden" name="id" value="${doctor.id}">
            </c:if>

            <c:if test="${not empty user}">
                <input type="hidden" name="password" value="${user.password}">
                <input type="hidden" name="login" value="${user.login}">
            </c:if>

            <div class="form-group">
                <label for="surname">${doctor_surname}:</label>
                <input type="text" pattern="^[A-ZА-Я][a-zа-я]+$" required class="form-control" id="surname"
                       name="surname" value="${surname}">
            </div>

            <div class="form-group">
                <label for="name">${doctor_name}:</label>
                <input type="text" pattern="^[A-ZА-Я][a-zа-я]+$" required class="form-control" id="name" name="name"
                       value="${name}">
            </div>

            <div class="form-group">

                <c:if test="${not empty specialization}">
                    <c:set var="selectedSpecialization" value="${specialization}"/>
                </c:if>

                <label for="specialization">${doctor_specialization}:</label>
                <select class="form-control" id="specialization" name="specialization">
                    <c:forEach items="${specializations}" var="specializationSelection">
                        <c:choose>
                            <c:when test="${selectedSpecialization == specializationSelection}">
                                <option selected value="${specializationSelection}">${specializationSelection}</option>
                            </c:when>
                            <c:otherwise>
                                <option value="${specializationSelection}">${specializationSelection}</option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group">

                <c:if test="${not empty workingShift}">
                    <c:set var="selectedWorkingShift" value="${workingShift}"/>
                </c:if>

                <label for="workingShift">${doctor_working_shift}:</label>
                <select class="form-control" id="workingShift" name="workingShift">
                    <c:forEach items="${workingShifts}" var="workingShiftSelection">
                        <c:choose>
                            <c:when test="${selectedWorkingShift == workingShiftSelection}">
                                <option selected value="${workingShiftSelection}">${workingShiftSelection}</option>
                            </c:when>
                            <c:otherwise>
                                <option value="${workingShiftSelection}">${workingShiftSelection}</option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </select>
            </div>

            <div class="btn-group">
                <input type="submit" class="btn btn-success" value="${button_save}">
                <input type="reset" class="btn btn-warning" value="${button_reset}">
            </div>
        </form>

        <c:if test="${(not empty doctor)  && (authorizedUser.role.name=='Администратор')}">
            <button class="btn btn-danger"
                    onclick="document.getElementById('delete').style.display='block'">${button_delete}</button>
            <div id="delete" class="modal">
            <span onclick="document.getElementById('delete').style.display='none'" class="close"
                  title="${button_delete}">&times;</span>
                <form class="modal-content" action="/doctor/delete.html" method="post">
                    <input type="hidden" name="id" value="${doctor.id}">
                    <div class="container">
                        <h1>${delete_account}</h1>
                        <p>${delete_question}</p>
                        <div class="clearfix">
                            <button type="button" onclick="document.getElementById('delete').style.display='none'"
                                    class="btn btn-secondary">${button_cancel}
                            </button>
                            <input type="submit" onclick="document.getElementById('delete').style.display='none'"
                                   class="btn btn-danger" value="${button_delete}">
                        </div>
                    </div>
                </form>
            </div>
        </c:if>
    </div>
</u:html>