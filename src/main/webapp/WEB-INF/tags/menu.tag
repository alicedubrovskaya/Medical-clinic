<%@tag language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:choose>
    <c:when test="${sessionScope.language != null}">
        <fmt:setLocale value="${sessionScope.language}"/>
    </c:when>
    <c:otherwise>
        <fmt:setLocale value="en"/>
    </c:otherwise>
</c:choose>

<fmt:setBundle basename="textResources" var="textResources" scope="session"/>
<fmt:message bundle="${textResources}" key="medicalClinic" var="medicalClinic"/>
<fmt:message bundle="${textResources}" key="entrance" var="entrance"/>
<fmt:message bundle="${textResources}" key="button.register" var="register"/>

<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">${medicalClinic}</a>
        </div>
        <ul class="nav navbar-nav">
            <%--			<li class="active"><a href="#">Home</a></li>--%>
            <c:choose>
                <c:when test="${not empty authorizedUser}">

                </c:when>
                <c:otherwise>
                    <li><a href="/login.html">${entrance}</a></li>
                </c:otherwise>
            </c:choose>

            <c:forEach items="${menu}" var="item">
                <c:url value="${item.url}" var="itemUrl"/>
                <c:choose>
                    <c:when test="${not empty item.menuItemsDropDown}">
                        <li class="dropdown">
                            <a class="dropdown-toggle" data-toggle="dropdown" href="#">${item.name}
                                <span class="caret"></span></a>
                            <ul class="dropdown-menu">
                                <c:forEach items="${item.menuItemsDropDown}" var="menuItem">
                                    <li><a href="${menuItem.url}">${menuItem.name}</a></li>
                                </c:forEach>
                            </ul>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="item"><a href="${itemUrl}">${item.name}</a></li>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
            <li class="dropdown">
                <a class="dropdown-toggle" data-toggle="dropdown" href="#">Language
                    <span class="caret"></span></a>
                <ul class="dropdown-menu">
                    <li>
                        <form action="/language.html" method="post">
                            <input type="hidden" name="language" value="en">
                            <input type="submit" class="btn btn-default" value="English">
                        </form>
                    </li>
                    <li>
                        <form action="/language.html" method="post">
                            <input type="hidden" name="language" value="ru">
                            <input type="submit" class="btn btn-default" value="Русский">
                        </form>
                    </li>
                </ul>
            </li>
        </ul>
    </div>
</nav>