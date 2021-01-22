<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <c:when test="${not empty doctor}">
        <c:set var="surname" value="${doctor.surname}"/>
        <c:set var="name" value="${doctor.name}"/>
        <c:set var="specialization" value="${doctor.specialization}"/>
        <c:set var="workingShift" value="${doctor.workingShift.name}"/>
        <c:set var="title" value="Врач ${doctor.surname} ${doctor.name}"/>
    </c:when>
    <c:otherwise>
        <c:set var="title" value="Новый врач"/>
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
                <label for="surname">Фамилия:</label>
                <input type="text" class="form-control" id="surname" name="surname" value="${surname}">
            </div>

            <div class="form-group">
                <label for="name">Имя:</label>
                <input type="text" class="form-control" id="name" name="name" value="${name}">
            </div>

            <div class="form-group">

                <c:if test="${not empty specialization}">
                    <c:set var="selectedSpecialization" value="${specialization}"/>
                </c:if>

                <label for="specialization">Специализация:</label>
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

                <label for="workingShift">Рабочая смена:</label>
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
                <input type="submit" class="btn btn-success" value="Сохранить">
                <input type="reset" class="btn btn-warning" value="Сбросить">
            </div>
        </form>
            <%--    TODO js--%>
        <c:if test="${not empty doctor}">
            <form action="/doctor/delete.html" method="post" onsubmit="deleteConfirmation(this);return false;">
                <input type="hidden" name="id" value="${doctor.id}">
                <input type="submit" class="btn btn-danger" value="Удалить">
            </form>
        </c:if>
    </div>

</u:html>