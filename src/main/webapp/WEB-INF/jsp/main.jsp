<%--
  Created by IntelliJ IDEA.
  User: Алиса
  Date: 15.01.2021
  Time: 10:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u"%>
<u:html title="Главная страница" message="${message}">
    <form action="/login.html" method="post">
        <button type="submit">Войти в систему</button>
    </form>
</u:html>
<%--<form action="/doctor/list.html" method="get">--%>
<%--    <button type="submit">Список врачей</button>--%>
<%--</form>--%>

<%--<form action="/user/list.html" method="get">--%>
<%--    <button type="submit">Список пользователей</button>--%>
<%--</form>--%>

<%--<form action="/vacation/list.html" method="get">--%>
<%--    <button type="submit">Список отпусков </button>--%>
<%--</form>--%>

<%--<form action="/patient/list.html" method="get">--%>
<%--    <button type="submit">Список пациентов</button>--%>
<%--</form>--%>




