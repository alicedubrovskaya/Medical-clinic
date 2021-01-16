<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Алиса
  Date: 15.01.2021
  Time: 18:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:choose>
    <c:when test="${not empty user}">
        <c:set var="login" value="${user.login}"/>
        <c:set var="password" value="скрыт"/>
        <c:set var="roleId" value="${user.role.id}"/>
        <c:set var="title" value="${user.role.name}"/>
    </c:when>
    <c:otherwise>
        <c:set var="title" value="Регистрация пользователя"/>
    </c:otherwise>
</c:choose>
<html>
<head>
    <title>"${title}"</title>
    <script type="text/javascript" src="/js/test.js"></script>
    <script type="text/javascript" src="/js/main.js"></script>
    <script type="text/javascript" src="/js/validator.js"></script>
        <script type="text/javascript" src="/js/validator-of-edit-user-form.js"></script>

</head>
<body>

<H2>${title}</H2>

<form action="/doctor/save.html" method="post" onsubmit="return validateEditUser(this)">
    <c:if test="${not empty user}">
        <input type="hidden" name="id" value="${user.id}">
    </c:if>
    <label for="login">Логин:</label>
    <input type="text" id="login" name="login" value="${login}">

    <label for="password">Пароль:</label>
    <input type="text" id="password" name="password" value="${password}">

    <label for="role">Роль:</label>
    <select id="role" name="role">
        <c:forEach items="${roles}" var="role">
            <c:remove var="selected"/>
            <c:if test="${not empty roleId and roleId eq role.id}">
                <c:set var="selected" value="selected"/>
            </c:if>
            <option value="${role.id}" ${selected}>${role.name}</option>
        </c:forEach>
    </select>

    <button type="submit">Сохранить</button>
    <button type="reset">Сбросить</button>
</form>

<c:if test="${not empty user}">
    <form action="/user/delete.html" method="post" onsubmit="deleteConfirmation(this);return false;">
        <input type="hidden" name="id" value="${user.id}">
        <input type="submit" value="Удалить">
    </form>
</c:if>
</body>
</html>
