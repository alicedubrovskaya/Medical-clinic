<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: Алиса
  Date: 18.01.2021
  Time: 19:43
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
<fmt:message bundle="${textResources}" key="appointment.management" var="appointment_management"/>
<fmt:message bundle="${textResources}" key="day.count" var="count_of_days"/>
<fmt:message bundle="${textResources}" key="date.startGeneration" var="date_start"/>
<fmt:message bundle="${textResources}" key="button.generate" var="appointment_generate"/>

<u:html title="${appointment_management}" message="${message}" validator="validator-of-edit-appointment-form.js">
    <div class="container">
        <form action="/appointment/generate.html" method="post" onsubmit="return validateDateAndDays()">
            <div class="form-group">
                <label for="days">${count_of_days}:</label>
                <input type="text" required pattern="^[0-9]+$" class="form-control" id="days" name="days"
                       onblur="validateDays()">
            </div>

            <div class="form-group">
                <label for="date">${date_start}:</label>
                <input type="date" class="form-control" id="date" name="date"
                       onblur="validateDateAndDays()">
            </div>
            <script>
                setCurrentDate();
            </script>
            <input type="submit" class="btn btn-warning" value="${appointment_generate}">
        </form>
    </div>
</u:html>
