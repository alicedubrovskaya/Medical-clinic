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
    <button type="submit">Получить список врачей</button>
</form>

<%--reader.id--%>
<form id="form-3" action="/doctor/edit.html" method="post">
    <input type="hidden" name="id" value="3">
    <button type="submit">Редактирование врача</button>
</form>

<form action="/doctor/edit.html" >
    <button type="submit">Добавить врача</button>
</form>

</body>
</html>