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
    <div class="container">
            <%--TODO add validation--%>
        <form action="/appointment/generate.html" method="post">
            <div class="form-group">
                <label for="days">Количество дней:</label>
                <input type="text" class="form-control" id="days" name="days">
            </div>

            <div class="form-group">
                <label for="date">Начальная дата генерации:</label>
                <input type="date" class="form-control" id="date" name="date" value="2021-01-01">
            </div>
            <input type="submit" class="btn btn-warning" value="Сгенерировать приемы врачей">
        </form>

        <form action="/appointment/list.html" method="get">
            <button type="submit" class="btn btn-default">Приемы врачей</button>
        </form>

    </div>
</u:html>
