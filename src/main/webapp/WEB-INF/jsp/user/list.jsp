<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%--
  Created by IntelliJ IDEA.
  User: Алиса
  Date: 16.01.2021
  Time: 16:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<u:html title="Список пользователей" message="${message}">
    <div class="container">
        <h2>Список пользователей</h2>
        <table class="table table-hover">
            <thead>
            <tr>
                <th>Логин</th>
                <th>Роль</th>
            </tr>
            </thead>

            <tbody>
            <c:url value="/user/edit.html" var="userEditUrl"/>
            <c:forEach items="${users}" var="user">
                <tr onclick="submitFormById('form-${user.id}')">
                    <td>
                            ${user.login}
                        <form id="form-${user.id}" action="${userEditUrl}" method="post">
                            <input type="hidden" name="id" value="${user.id}">
                        </form>
                    </td>
                    <td>${user.role.name}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</u:html>
