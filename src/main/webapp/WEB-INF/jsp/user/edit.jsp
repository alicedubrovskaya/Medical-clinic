<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--
  Created by IntelliJ IDEA.
  User: Алиса
  Date: 15.01.2021
  Time: 18:17
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
<fmt:message bundle="${textResources}" key="user.password.confirm" var="user_password_confirm"/>
<fmt:message bundle="${textResources}" key="user.role" var="user_role"/>
<fmt:message bundle="${textResources}" key="user.registration" var="user_registration"/>
<fmt:message bundle="${textResources}" key="button.continue" var="button_continue"/>
<fmt:message bundle="${textResources}" key="button.delete" var="button_delete"/>
<fmt:message bundle="${textResources}" key="button.reset" var="button_reset"/>
<fmt:message bundle="${textResources}" key="button.save" var="button_save"/>
<fmt:message bundle="${textResources}" key="user.password.old" var="old_password"/>
<fmt:message bundle="${textResources}" key="validation.incorrect.login" var="incorrect_login"/>
<fmt:message bundle="${textResources}" key="validation.incorrect.password" var="incorrect_password"/>


<c:choose>
    <c:when test="${not empty user}">
        <c:set var="login" value="${user.login}"/>
        <c:set var="password" value="скрыт"/>
        <c:set var="password_confirm" value="скрыт"/>
        <c:set var="roleId" value="${user.role.id}"/>
        <c:set var="title" value="${user.role.name}"/>
    </c:when>
    <c:otherwise>
        <c:set var="title" value="${user_registration}"/>
    </c:otherwise>
</c:choose>

<u:html title="${title}" message="${message}" validator="validator-of-edit-user-form.js">
    <div class="container">
        <H2>${title}</H2>
        <form action="/user/save.html" method="post"
              onsubmit="return validatePassword()">
            <c:if test="${not empty user}">
                <input type="hidden" name="id" value="${user.id}">
            </c:if>

            <c:if test="${not empty registration}">
                <input type="hidden" id="registration" name="registration" value="${registration}">
            </c:if>

            <div class="form-group">
                <label for="login">${user_login}:</label>
                <c:choose>
                    <c:when test="${not empty user}">
                        <input readonly type="text" class="form-control" id="login" name="login" value="${login}">
                        <input type="hidden" id="role" name="role" value="${roleId}">
                    </c:when>
                    <c:otherwise>
                        <input type="text" required pattern="^[a-z0-9_-]{3,16}$"
                               title="${incorrect_login}" class="form-control" id="login"
                               name="login" value="${login}">
                    </c:otherwise>
                </c:choose>
            </div>

            <c:if test="${not empty user}">
                <div class=" form-group">
                    <label for="old_password">${old_password}:</label>
                    <input type="password" required class="form-control" id="old_password" name="old_password">
                </div>
            </c:if>
            <div class="form-group">
                <label for="password">${user_password}:</label>
                <input type="password" required pattern="^.{6,16}$" title="${incorrect_password}" class="form-control"
                       id="password" name="password">
            </div>
            <div class="form-group">
                <label for="password_confirm">${user_password_confirm}:</label>
                <input type="password" required class="form-control" id="password_confirm"
                       name="password_confirm"
                       pattern="^.{6,16}$"
                       onkeyup="validatePassword()"
                >
            </div>
            <div class="btn-group">
                <input type="submit" class="btn btn-warning" value="${button_reset}">
                <c:choose>
                    <c:when test="${not empty role}">
                        <input type="submit" class="btn btn-success" value="${button_continue}">
                    </c:when>
                    <c:otherwise>
                        <input type="submit" class="btn btn-success" value="${button_save}">
                    </c:otherwise>
                </c:choose>
            </div>
        </form>
    </div>
</u:html>
