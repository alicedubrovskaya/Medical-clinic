<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%--
  Created by IntelliJ IDEA.
  User: Алиса
  Date: 16.01.2021
  Time: 21:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<html>--%>
<%--<head>--%>
<%--    <title>Отпуска врачей</title>--%>
<%--    <script type="text/javascript" src="/js/test.js"></script>--%>
<%--    <script type="text/javascript" src="/js/main.js"></script>--%>
<%--</head>--%>
<%--<body>--%>
<%--<h2>Отпуска врачей</h2>--%>

<u:html title="Список отпусков" message="${message}">
    <div class="container">

        <h2>Список отпусков</h2>
        <table class="table table-hover">
            <thead>
            <tr>
                <th>Фамилия</th>
                <th>Имя</th>
                <th>Специализация</th>
                <th>Дата начала</th>
                <th>Дата конца</th>
            </tr>
            </thead>
            <tbody>

            <c:url value="/vacation/edit.html" var="vacationEditUrl"/>
            <c:forEach var="vacation" items="${vacations}" varStatus="status">
                <tr onclick="submitFormById('form-${vacation.id}')">
                    <td>
                            ${vacation.doctor.surname}
                        <form id="form-${vacation.id}" action="${vacationEditUrl}" method="post">
                            <input type="hidden" name="id" value="${vacation.id}">
                        </form>
                    </td>
                    <td>${vacation.doctor.name}</td>
                    <td>${vacation.doctor.specialization}</td>

                    <c:remove var="parsedStartDate"/>
                    <c:remove var="startFormat"/>
                    <fmt:formatDate value="${vacation.start}" var="startFormat" pattern="yyyy-MM-dd"/>
                    <td>${startFormat}</td>

                    <c:remove var="parsedEndDate"/>
                    <c:remove var="endFormat"/>
                    <fmt:formatDate value="${vacation.end}" var="endFormat" pattern="yyyy-MM-dd"/>
                    <td>${endFormat}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <%--    <form action="/vacation/edit.html" method="post">--%>
    <%--        <button type="submit">Добавить отпуск</button>--%>
    <%--    </form>--%>

</u:html>
