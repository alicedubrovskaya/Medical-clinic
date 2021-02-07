<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: Алиса
  Date: 19.01.2021
  Time: 14:12
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
<fmt:message bundle="${textResources}" key="patient.appointment" var="patient_appointment"/>
<fmt:message bundle="${textResources}" key="date" var="appointment_date"/>
<fmt:message bundle="${textResources}" key="button.view" var="button_view"/>
<fmt:message bundle="${textResources}" key="status.undefined" var="status_undefined"/>
<fmt:message bundle="${textResources}" key="doctor.specialization" var="doctor_specialization"/>

<u:html title="${patient_appointment}" message="${message}">
    <div class="container">
        <h2>${patient_appointment}</h2>
        <form action="/appointment/list.html" method="post">
            <div class="form-group">
                <label for="date">${appointment_date}:</label>
                <input type="date" class="form-control" id="date" name="date">
            </div>
            <script>
                getDate();
            </script>

            <c:choose>
                <c:when test="${authorizedUser.role.name=='Пациент'}">
                    <div class="form-group">
                        <label for="specialization">${doctor_specialization}:</label>
                        <select class="form-control" id="specialization" name="specialization">
                            <c:forEach items="${specializations}" var="specialization">
                                <option value="${specialization}">${specialization}</option>
                            </c:forEach>
                        </select>
                    </div>
                </c:when>
                <c:otherwise>
                    <select id="status" class="form-control" name="status">
                        <option>${status_undefined}</option>
                        <c:forEach items="${statuses}" var="status">
                            <option value="${status}">${status}</option>
                        </c:forEach>
                    </select>
                </c:otherwise>
            </c:choose>

            <input type="submit" class="btn btn-info" value="${button_view}">
        </form>
    </div>

    <c:if test="${not empty appointments}">
        <jsp:include page="/WEB-INF/jsp/appointment/list.jsp"/>
    </c:if>
</u:html>