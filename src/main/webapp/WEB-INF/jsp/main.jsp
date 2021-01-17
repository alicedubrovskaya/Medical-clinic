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


<%--TODO add validation--%>
<form action="/appointment/generate.html" method="post">

    <label for="days">Количество дней:</label>
    <input type="text" id="days" name="days">

    <label for="start">Начальная дата генерации:</label>
    <input type="date" id="start" name="start" value="2021-01-01">

    <input type="submit" value="Сгенерировать расписание врачей">
</form>
</body>
</html>
