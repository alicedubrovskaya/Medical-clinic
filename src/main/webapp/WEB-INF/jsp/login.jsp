<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--
  Created by IntelliJ IDEA.
  User: Алиса
  Date: 18.01.2021
  Time: 11:50
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
<fmt:message bundle="${textResources}" key="user.login" var="user_login"/>
<fmt:message bundle="${textResources}" key="user.password" var="user_password"/>
<fmt:message bundle="${textResources}" key="user.role" var="user_role"/>
<fmt:message bundle="${textResources}" key="user.registration" var="user_registration"/>
<fmt:message bundle="${textResources}" key="button.enter" var="button_enter"/>
<fmt:message bundle="${textResources}" key="entrance" var="entrance"/>


<u:html title="${entrance}" message="${message}">
    <div class="container">
        <h2>${entrance}</h2>

        <c:url value="/login.html" var="loginUrl"/>
        <form action="${loginUrl}" method="post">
            <div class="form-group">
                <label for="login">${user_login}:</label>
                <input type="text" id="login" name="login" class="form-control">
            </div>
            <div class="form-group">
                <label for="password">${user_password}:</label>
                <input type="password" id="password" name="password" class="form-control">
            </div>
            <input type="submit" class="btn btn-success" value="${button_enter}">
        </form>
    </div>
</u:html>
