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
<fmt:message bundle="${textResources}" key="doctor.list" var="doctorList"/>


<u:html title="Список врачей" message="${message}">
    <div class="container">
        <h2>${doctorList}</h2>

        <table class="table table-hover">
            <thead>
            <tr>
                <th>Фамилия</th>
                <th>Имя</th>
                <th>Специализация</th>
                <th>Смена рабочая</th>
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

        <form action="/user/edit.html">
            <input type="hidden" name="role" value='Врач'>
            <button type="submit">Зарегистрировать врача</button>
        </form>
    </div>
</u:html>

