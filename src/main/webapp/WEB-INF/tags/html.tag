<%@ attribute name="cssFiles" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/strict.dtd">

<%@tag language="java" pageEncoding="UTF-8" %>
<%@attribute name="title" required="true" rtexprvalue="true" type="java.lang.String" %>
<%@attribute name="message" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@attribute name="validator" required="false" rtexprvalue="true" type="java.lang.String" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<fmt:message bundle="${textResources}" key="clinic" var="clinic"/>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>${clinic} - ${title}</title>
    <%--	<c:url value="/main.css" var="cssUrl"/>--%>
    <%--	<LINK rel="stylesheet" type="text/css" href="${cssUrl}">--%>
    <c:url value="/js" var="javascriptUrl"/>
    <script type="text/javascript" src="${javascriptUrl}/main.js"></script>

    <%--    TODO--%>
    <c:if test="${not empty cssFiles}">
        <c:forEach var="cssFileItem" items="${cssFiles}">
            <link href="<c:url value="/css/${cssFileItem}"/>" rel="stylesheet">
        </c:forEach>
    </c:if>
    <link href="<c:url value="/css/main.css"/>" rel="stylesheet">

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>

    <c:if test="${not empty validator}">
        <script type="text/javascript" src="${javascriptUrl}/validator.js"></script>
        <script type="text/javascript" src="${javascriptUrl}/${validator}"></script>
    </c:if>
</head>
<body>
<u:menu/>
<div id="page">
    <jsp:doBody/>
</div>
<c:if test="${not empty message}">
    <div class="alert alert-info alert-dismissible fade show" role="alert">
            ${message}
        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
            <span aria-hidden="true">&times;</span>
        </button>
    </div>
</c:if>
</body>
</html>