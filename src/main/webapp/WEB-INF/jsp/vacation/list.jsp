<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%--
  Created by IntelliJ IDEA.
  User: Алиса
  Date: 16.01.2021
  Time: 21:44
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


<u:html title="${vacation_list}" message="${message}">
    <div class="container">
        <div class="main-head">
            <h2>${vacation_list}</h2>
        </div>
        <table class="table table-hover">
            <thead>
            <tr>
                <th>${doctor_surname}</th>
                <th>${doctor_name}</th>
                <th>${doctor_specialization}</th>
                <th>${date_start}</th>
                <th>${date_end}</th>
            </tr>
            </thead>
            <tbody>

            <c:url value="/vacation/edit.html" var="vacationEditUrl"/>
            <c:forEach var="vacation" items="${vacations}" varStatus="status">
                <tr onclick="submitFormById('form-${vacation.id}')">
                    <td>
                            ${vacation.doctor.surname}
                        <form id="form-${vacation.id}" action="${vacationEditUrl}" method="post">
                            <input type="hidden" name="id" value="${vacation.id}">
                        </form>
                    </td>
                    <td>${vacation.doctor.name}</td>
                    <td>${vacation.doctor.specialization}</td>

                    <c:remove var="parsedStartDate"/>
                    <c:remove var="startFormat"/>
                    <fmt:formatDate value="${vacation.start}" var="startFormat" pattern="yyyy-MM-dd"/>
                    <td>${startFormat}</td>

                    <c:remove var="parsedEndDate"/>
                    <c:remove var="endFormat"/>
                    <fmt:formatDate value="${vacation.end}" var="endFormat" pattern="yyyy-MM-dd"/>
                    <td>${endFormat}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <u:pagination entity="vacation"/>
    </div>
</u:html>
