<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--
  Created by IntelliJ IDEA.
  User: Алиса
  Date: 15.01.2021
  Time: 10:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>

<c:choose>
    <c:when test="${sessionScope.language != null}">
        <fmt:setLocale value="${sessionScope.language}"/>
    </c:when>
    <c:otherwise>
        <fmt:setLocale value="en"/>
    </c:otherwise>
</c:choose>

<fmt:setBundle basename="textResources" var="textResources" scope="session"/>
<fmt:message bundle="${textResources}" key="page.main" var="page_main"/>
<fmt:message bundle="${textResources}" key="entrance" var="entrance"/>
<fmt:message bundle="${textResources}" key="button.register" var="register"/>
<fmt:message bundle="${textResources}" key="service.allergist" var="allergist"/>
<fmt:message bundle="${textResources}" key="service.cardiologist" var="cardiologist"/>
<fmt:message bundle="${textResources}" key="service.nutritionist" var="nutritionist"/>
<fmt:message bundle="${textResources}" key="service.rheumatologist" var="rheumatologist"/>
<fmt:message bundle="${textResources}" key="service.surgeon" var="surgeon"/>
<fmt:message bundle="${textResources}" key="service.therapist" var="therapist"/>
<fmt:message bundle="${textResources}" key="service" var="service"/>


<c:set var="cssFiles" value="${['main.css']}" scope="page"/>
<u:html title="${page_main}" cssFiles="${cssFiles}" message="${message}">
    <h2 class="text-center">${service}</h2>

    <div class="row">
        <div class="col-sm-4">
            <div class="card text-white bg-primary">
                <img class="img-responsive" src="<c:url value="/img/therapist.jpg"/>" alt="therapist">
                <div class="card-body">
                    <h4 class="card-title">${therapist}</h4>
                </div>
            </div>
        </div>
        <div class="col-sm-4">
            <div class="card text-white bg-primary">
                <img class="img-responsive" src="<c:url value="/img/surgeon.jpg"/>" alt="surgeon">
                <div class="card-body">
                    <h4 class="card-title">${surgeon}</h4>
                </div>
            </div>
        </div>
        <div class="col-sm-4">
            <div class="card text-white bg-primary">
                <img class="img-responsive" src="<c:url value="/img/allergist.jpg"/>" alt="allergist">
                <div class="card-body">
                    <h4 class="card-title">${allergist}</h4>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-4">
            <div class="card text-white bg-primary">
                <img class="img-responsive" src="<c:url value="/img/nutritionist.jpg"/>" alt="nutritionist">
                <div class="card-body">
                    <h4 class="card-title">${nutritionist}</h4>
                </div>
            </div>
        </div>
        <div class="col-sm-4">
            <div class="card text-white bg-primary">
                <img class="img-responsive" src="<c:url value="/img/rheumatologist.png"/>" alt="rheumatologist">
                <div class="card-body">
                    <h4 class="card-title">${rheumatologist}</h4>
                </div>
            </div>
        </div>
        <div class="col-sm-4">
            <div class="card text-white bg-primary">
                <img class="img-responsive" src="<c:url value="/img/cardiologist.jpg"/>" alt="cardiologist">
                <div class="card-body">
                    <h4 class="card-title">${cardiologist}</h4>
                </div>
            </div>
        </div>
    </div>

</u:html>


<%--    <div class="container">--%>
<%--        <form action="/login.html" method="post">--%>
<%--            <input type="submit" class="btn btn-success" value="${entrance}">--%>
<%--        </form>--%>

<%--        <form action="/user/edit.html" method="post">--%>
<%--            <input type="hidden" name="role" value="Пациент">--%>
<%--            <input type="submit" class="btn btn-default" value="${register}">--%>
<%--        </form>--%>
<%--    </div>--%>