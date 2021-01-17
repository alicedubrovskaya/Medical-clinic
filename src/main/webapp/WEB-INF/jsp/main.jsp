<%--
  Created by IntelliJ IDEA.
  User: Алиса
  Date: 15.01.2021
  Time: 10:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Главная страница</title>
</head>
<body>

<form action="/doctor/list.html" method="get">
    <button type="submit">Список врачей</button>
</form>

<form action="/user/list.html" method="get">
    <button type="submit">Список пользователей</button>
</form>

<form action="/vacation/list.html" method="get">
    <button type="submit">Список отпусков </button>
</form>

<form action="/patient/list.html" method="get">
    <button type="submit">Список пациентов</button>
</form>


<%--<form action="/appointment/generate.html" method="post" onsubmit="deleteConfirmation(this);return false;">--%>
<form action="/appointment/generate.html" method="post">
    <input type="submit" value="Сгенерировать расписание врачей">
</form>
</body>
</html>
