<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--
  Created by IntelliJ IDEA.
  User: Алиса
  Date: 16.01.2021
  Time: 16:59
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
<fmt:message bundle="${textResources}" key="user.list" var="user_list"/>
<fmt:message bundle="${textResources}" key="user.login" var="user_login"/>
<fmt:message bundle="${textResources}" key="user.password" var="user_password"/>
<fmt:message bundle="${textResources}" key="user.role" var="user_role"/>
<fmt:message bundle="${textResources}" key="page.next" var="next"/>
<fmt:message bundle="${textResources}" key="page.previous" var="previous"/>


<u:html title="${user_list}" message="${message}">
    <div class="container">
        <div class="main-head">
            <h2>${user_list}</h2>
        </div>
        <table class="table table-hover">
            <thead>
            <tr>
                <th>${user_login}</th>
                <th>${user_role}</th>
            </tr>
            </thead>
            <tbody>
            <c:url value="/user/edit.html" var="userEditUrl"/>
            <c:forEach items="${users}" var="user">
                <tr onclick="submitFormById('form-${user.id}')">
                    <td>
                            ${user.login}
                        <form id="form-${user.id}" action="${userEditUrl}" method="post">
                            <input type="hidden" name="id" value="${user.id}">
                        </form>
                    </td>
                    <td>${user.role.name}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <u:pagination entity="user"/>
    </div>
</u:html>
