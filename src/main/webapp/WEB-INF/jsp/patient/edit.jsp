<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%--
  Created by IntelliJ IDEA.
  User: Алиса
  Date: 17.01.2021
  Time: 12:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:choose>
    <c:when test="${not empty patient}">
        <c:set var="surname" value="${patient.surname}"/>
        <c:set var="name" value="${patient.name}"/>
        <c:set var="email" value="${patient.email}"/>
        <c:set var="phoneNumber" value="${patient.phoneNumber}"/>
        <c:set var="address" value="${patient.address}"/>
        <c:set var="title" value="Пациент ${patient.surname} ${patient.name}"/>
    </c:when>
    <c:otherwise>
        <c:set var="title" value="Новый пациент"/>
    </c:otherwise>
</c:choose>

<u:html title="${title}" message="${message}">
    <H2>${title}</H2>
    <div class="container">
            <%--<form action="/patient/save.html" method="post" onsubmit="return validateEditDoctor(this)">--%>
        <form action="/patient/save.html" method="post">
            <c:if test="${not empty patient}">
                <input type="hidden" name="id" value="${patient.id}">
            </c:if>
            <c:if test="${not empty user}">
                <input type="hidden" name="password" value="${user.password}">
                <input type="hidden" name="login" value="${user.login}">
            </c:if>

            <div class="form-group">
                <label for="surname">Фамилия:</label>
                <input type="text" class="form-control" id="surname" name="surname" value="${surname}">
            </div>
            <div class="form-group">
                <label for="name">Имя:</label>
                <input type="text" class="form-control" id="name" name="name" value="${name}">
            </div>
            <div class="form-group">
                <label for="email">Почта:</label>
                <input type="email" class="form-control" id="email" name="email" value="${email}">
            </div>
            <div class="form-group">
                <label for="phoneNumber">Номер телефона:</label>
                <input type="text" class="form-control" id="phoneNumber" name="phoneNumber" value="${phoneNumber}">
            </div>
            <div class="form-group">
                <label for="address">Адрес проживания:</label>
                <input type="text" class="form-control" id="address" name="address" value="${address}">
            </div>
            <div class="btn-group">
                <input type="submit" class="btn btn-success" value="Сохранить">
                <input type="reset" class="btn btn-warning" value="Сбросить">
            </div>
        </form>

        <c:if test="${not empty patient}">
            <form action="/appointment/medicalCard.html" method="get">
                <input type="hidden" name="id" value="${patient.id}">
                <input type="submit" class="btn btn-default" value="История посещений врачей">
            </form>
        </c:if>
    </div>
</u:html>
