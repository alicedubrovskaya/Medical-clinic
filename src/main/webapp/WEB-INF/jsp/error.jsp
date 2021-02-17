<%--
  Created by IntelliJ IDEA.
  User: Алиса
  Date: 17.02.2021
  Time: 13:52
  To change this template use File | Settings | File Templates.
--%>
<%@page isErrorPage="true" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:choose>
    <c:when test="${sessionScope.language != null}">
        <fmt:setLocale value="${sessionScope.language}"/>
    </c:when>
    <c:otherwise>
        <fmt:setLocale value="en"/>
    </c:otherwise>
</c:choose>

<fmt:setBundle basename="textResources" var="textResources" scope="session"/>
<fmt:message bundle="${textResources}" key="error" var="error_title"/>
<fmt:message bundle="${textResources}" key="error.page.notFound" var="page_not_found"/>
<fmt:message bundle="${textResources}" key="error.unexpected" var="unexpected_error"/>
<fmt:message bundle="${textResources}" key="page.main" var="page_main"/>

<u:html title="${error_title}">
    <c:choose>
        <c:when test="${not empty error}">
            <h2>${error}</h2>
        </c:when>
        <c:when test="${not empty pageContext.errorData.requestURI}">
            <h2>${page_not_found} ${pageContext.errorData.requestURI}</h2>
        </c:when>
        <c:otherwise>${unexpected_error}</c:otherwise>
    </c:choose>
    <c:url value="/main.html" var="mainUrl"/>
    <a href="${mainUrl}">${page_main}</a>
</u:html>
