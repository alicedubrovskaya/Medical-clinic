<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%--
  Created by IntelliJ IDEA.
  User: Алиса
  Date: 16.01.2021
  Time: 18:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:choose>
    <c:when test="${not empty vacation}">
        <c:set var="start" value="${vacation.start}"/>
        <c:set var="end" value="${vacation.end}"/>
        <c:set var="doctorName" value="${vacation.doctor.name}"/>
        <c:set var="doctorSurname" value="${vacation.doctor.surname}"/>
        <c:set var="title" value="${vacation.doctor.name} ${vacation.doctor.surname}, отпуск"/>
    </c:when>
    <c:otherwise>
        <c:set var="title" value="Добавление отпуска"/>
    </c:otherwise>
</c:choose>

<u:html title="${title}" message="${message}">
    <div class="container">
        <H2>${title}</H2>
        <form action="/vacation/save.html" method="post" onsubmit="return validateEditVacation(this)">
            <c:if test="${not empty id}">
                <input type="hidden" name="id" value="${id}">
            </c:if>
            <div class="form-group">
                <label for="surname">Фамилия:</label>
                <input type="text" class="form-control" id="surname" name="surname" value="${doctorSurname}">
            </div>
            <div class="form-group">
                <label for="name">Имя:</label>
                <input type="text" class="form-control" id="name" name="name" value="${doctorName}">
            </div>
            <div class="form-group">
                <label for="start">Дата начала:</label>
                <input type="date" class="form-control" id="start" name="vacation-start" min="2021-01-01"
                       value="${start}">
            </div>
            <div class="form-group">
                <label for="end">Дата окончания:</label>
                <input type="date" class="form-control" id="end" name="vacation-end" min="2021-01-01" value="${end}">
            </div>
            <div class="btn-group">
                <input type="submit" class="btn btn-success" value="Сохранить">
                <input type="reset" class="btn btn-warning" value="Сбросить">
            </div>
        </form>

        <c:if test="${not empty vacation}">
            <form action="/vacation/delete.html" method="post" onsubmit="deleteConfirmation(this);return false;">
                <input type="hidden" name="id" value="${id}">
                <input type="submit" class="btn btn-danger" value="Удалить">
            </form>
        </c:if>
    </div>
</u:html>