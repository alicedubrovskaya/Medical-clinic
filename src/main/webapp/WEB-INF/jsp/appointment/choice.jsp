<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%--
  Created by IntelliJ IDEA.
  User: Алиса
  Date: 17.01.2021
  Time: 18:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<u:html title="Запись на прием к врачу" message="${message}">
    <div class="container">
        <h2>Запись на прием к врачу</h2>
        <form action="/appointment/list.html" method="post">
            <div class="form-group">
                <label for="date">Дата приема:</label>
                <input type="date" class="form-control" id="date" name="date" value="2021-01-01">
            </div>

            <div class="form-group">
                <label for="specialization">Специализация врача:</label>
                <select class="form-control" id="specialization" name="specialization">
                    <c:forEach items="${specializations}" var="specialization">
                        <option value="${specialization}">${specialization}</option>
                    </c:forEach>
                </select>
            </div>
            <input type="submit" class="btn btn-default" value="Найти">
        </form>
    </div>
</u:html>
