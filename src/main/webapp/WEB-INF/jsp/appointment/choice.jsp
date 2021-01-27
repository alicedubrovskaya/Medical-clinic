<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--
  Created by IntelliJ IDEA.
  User: Алиса
  Date: 17.01.2021
  Time: 18:01
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
<fmt:message bundle="${textResources}" key="date" var="appointment_date"/>
<fmt:message bundle="${textResources}" key="doctor.specialization" var="doctor_specialization"/>
<fmt:message bundle="${textResources}" key="button.find" var="button_find"/>

<u:html title="${appointment_making}" message="${message}">
    <div class="container">
        <h2>${appointment_making}</h2>
        <form action="/appointment/list.html" method="post">
            <div class="form-group">
                <label for="date">${appointment_date}:</label>
                <input type="date" class="form-control" id="date" name="date">
            </div>
            <script>
                getDate();
            </script>
            <div class="form-group">
                <label for="specialization">${doctor_specialization}:</label>
                <select class="form-control" id="specialization" name="specialization">
                    <c:forEach items="${specializations}" var="specialization">
                        <option value="${specialization}">${specialization}</option>
                    </c:forEach>
                </select>
            </div>
            <input type="submit" class="btn btn-default" value="${button_find}">
        </form>
    </div>
</u:html>
