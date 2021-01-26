<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>

<%--
  Created by IntelliJ IDEA.
  User: Алиса
  Date: 26.01.2021
  Time: 17:33
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
<fmt:message bundle="${textResources}" key="home" var="home"/>

<u:html title="${home}" message="${message}">
    <h2>${home}</h2>
</u:html>
