<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Алиса
  Date: 18.01.2021
  Time: 11:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Вход в систему</title>
</head>
<body>
<H2>Вход в систему</H2>

<c:url value="/login.html" var="loginUrl"/>
<form action="${loginUrl}" method="post">
    <label for="login">Логин:</label>
    <input type="text" id="login" name="login">

    <label for="password">Пароль:</label>
    <input type="password" id="password" name="password">

    <button type="submit">Войти</button>
</form>
</body>
</html>
