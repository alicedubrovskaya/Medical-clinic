<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%--
  Created by IntelliJ IDEA.
  User: Алиса
  Date: 18.01.2021
  Time: 11:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<u:html title="Вход в систему" message="${message}">
    <div class="container">
        <h2>Вход в систему</h2>

        <c:url value="/login.html" var="loginUrl"/>
        <form action="${loginUrl}" method="post">
            <div class="form-group">
                <label for="login">Логин:</label>
                <input type="text" id="login" name="login" class="form-control">
            </div>

            <div class="form-group">
                <label for="password">Пароль:</label>
                <input type="password" id="password" name="password" class="form-control">
            </div>

            <input type="submit" class="btn btn-default" value="Войти">
        </form>
    </div>
</u:html>
