<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/strict.dtd">

<%@tag language="java" pageEncoding="UTF-8" %>
<%@attribute name="title" required="true" rtexprvalue="true" type="java.lang.String" %>
<%@attribute name="message" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@attribute name="validator" required="false" rtexprvalue="true" type="java.lang.String" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <TITLE>Медицинская клиника - ${title}</TITLE>
    <%--	<c:url value="/main.css" var="cssUrl"/>--%>
    <%--	<LINK rel="stylesheet" type="text/css" href="${cssUrl}">--%>
    <c:url value="/js" var="javascriptUrl"/>
    <script type="text/javascript" src="${javascriptUrl}/main.js"></script>
    <c:if test="${not empty message}">
        <script type="text/javascript">
            startMessage = "${message}";
        </script>
    </c:if>
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
</body>
</html>