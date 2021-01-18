<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%--
  Created by IntelliJ IDEA.
  User: Алиса
  Date: 18.01.2021
  Time: 19:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<u:html title="Управление расписанием приемов врачей" message="${message}">
    <%--TODO add validation--%>
    <form action="/appointment/generate.html" method="post">
        <label for="days">Количество дней:</label>
        <input type="text" id="days" name="days">

        <label for="date">Начальная дата генерации:</label>
        <input type="date" id="date" name="date" value="2021-01-01">

        <input type="submit" value="Сгенерировать приемы врачей">
    </form>
</u:html>
