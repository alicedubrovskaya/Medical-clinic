<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--
  Created by IntelliJ IDEA.
  User: Алиса
  Date: 15.01.2021
  Time: 10:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>

<c:choose>
    <c:when test="${sessionScope.language != null}">
        <fmt:setLocale value="${sessionScope.language}"/>
    </c:when>
    <c:otherwise>
        <fmt:setLocale value="en"/>
    </c:otherwise>
</c:choose>

<fmt:setBundle basename="textResources" var="textResources" scope="session"/>
<fmt:message bundle="${textResources}" key="page.main" var="page_main"/>
<fmt:message bundle="${textResources}" key="entrance" var="entrance"/>
<fmt:message bundle="${textResources}" key="button.register" var="register"/>

<u:html title="${page_main}" message="${message}">
    <div class="container">
        <form action="/login.html" method="post">
            <input type="submit" class="btn btn-success" value="${entrance}">
        </form>

        <form action="/user/edit.html" method="post">
            <input type="hidden" name="role" value="Пациент">
            <input type="submit" class="btn btn-default" value="${register}">
        </form>
    </div>

</u:html>




