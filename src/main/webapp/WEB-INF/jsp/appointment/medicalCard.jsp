<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: Алиса
  Date: 18.01.2021
  Time: 20:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<u:html title="История посещений врачей" message="${message}">
    <div class="container">
        <ul class="list-group">
            <c:forEach var="appointment" items="${appointments}" varStatus="status">
                <li class="list-group-item">
                    <h3>Прием</h3>
                    <div class="form-group row">
                        <label for="time" class="col-sm-2 col-form-label">Дата приема:</label>
                        <div class="col-sm-10">
                            <fmt:formatDate value="${appointment.time}" var="timeFormat" pattern="yyyy-MM-dd HH:mm:ss"/>
                            <input type="text" readonly class="form-control-plaintext" id="time" value="${timeFormat}">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="doctor" class="col-sm-2 col-form-label">Врач:</label>
                        <div class="col-sm-10">
                            <input type="text" readonly class="form-control-plaintext" id="doctor"
                                   value="${appointment.doctor.surname} ${appointment.doctor.name}">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="specialization" class="col-sm-2 col-form-label">Специализация:</label>
                        <div class="col-sm-10">
                            <input type="text" readonly class="form-control-plaintext" id="specialization"
                                   value="${appointment.doctor.specialization}">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="complaints" class="col-sm-2 col-form-label">Жалобы:</label>
                        <div class="col-sm-10">
                            <input type="text" readonly class="form-control-plaintext" id="complaints"
                                   value="${appointment.complaints}">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="medicalReport" class="col-sm-2 col-form-label">Заключение врача:</label>
                        <div class="col-sm-10">
                            <input type="text" readonly class="form-control-plaintext" id="medicalReport"
                                   value="${appointment.medicalReport}">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="recommendation" class="col-sm-2 col-form-label">Рекомендации врача:</label>
                        <div class="col-sm-10">
                            <input type="text" readonly class="form-control-plaintext" id="recommendation"
                                   value="${appointment.recommendation}">
                        </div>
                    </div>
                </li>
            </c:forEach>
        </ul>
    </div>
</u:html>