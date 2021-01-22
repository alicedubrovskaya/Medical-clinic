<%--
  Created by IntelliJ IDEA.
  User: Алиса
  Date: 15.01.2021
  Time: 10:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>

<u:html title="Главная страница" message="${message}">
    <div class="container">
        <form action="/login.html" method="post">
            <input type="submit" class="btn btn-success" value="Войти в систему">
        </form>

        <form action="/user/edit.html" method="post">
            <input type="hidden" name="role" value="Пациент">
            <input type="submit" class="btn btn-default" value="Зарегистрироваться">
        </form>
    </div>

</u:html>




