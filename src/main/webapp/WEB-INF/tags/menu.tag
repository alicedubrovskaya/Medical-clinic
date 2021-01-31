<%@tag language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:choose>
    <c:when test="${sessionScope.language != null}">
        <fmt:setLocale value="${sessionScope.language}"/>
        <c:set var="lang" value="${sessionScope.language}"/>
    </c:when>
    <c:otherwise>
        <fmt:setLocale value="en"/>
        <c:set var="lang" value="en"/>
    </c:otherwise>
</c:choose>

<fmt:setBundle basename="textResources" var="textResources" scope="session"/>
<fmt:message bundle="${textResources}" key="medicalClinic" var="medicalClinic"/>
<fmt:message bundle="${textResources}" key="entrance" var="entrance"/>
<fmt:message bundle="${textResources}" key="button.register" var="register"/>
<fmt:message bundle="${textResources}" key="logout" var="logout"/>
<fmt:message bundle="${textResources}" key="language.change" var="language"/>

<%--<link href="<c:url value="/css/menu.css"/>" rel="stylesheet">--%>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="/main.html">${medicalClinic}</a>
        </div>

        <div class="collapse navbar-collapse" id="navbarNav">
            <div class="navbar-nav">
                <c:choose>
                    <c:when test="${not empty authorizedUser}">
                        <li class="nav-item"><a class="nav-link" href="/logout.html">${logout}</a></li>
                    </c:when>
                    <c:otherwise>
                        <li class="nav-item"><a class="nav-link" href="/login.html">${entrance}</a></li>
                        <li class="nav-item"><a class="nav-link" href="/user/edit.html?role=Пациент">${register}</a>
                        </li>
                    </c:otherwise>
                </c:choose>

                <c:forEach items="${menu}" var="item">
                    <c:url value="${item.url}" var="itemUrl"/>
                    <c:choose>
                        <c:when test="${not empty item.menuItemsDropDown}">
                            <li class="nav-item dropdown">
                                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" role="button"
                                   data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    <c:choose>
                                        <c:when test="${lang =='ru'}">
                                            ${item.russianName}
                                        </c:when>
                                        <c:otherwise>
                                            ${item.englishName}
                                        </c:otherwise>
                                    </c:choose>
                                </a>
                                <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
                                    <c:forEach items="${item.menuItemsDropDown}" var="menuItem">
                                        <a class="dropdown-item" href="${menuItem.url}">
                                            <c:choose>
                                                <c:when test="${lang =='ru'}">
                                                    ${menuItem.russianName}
                                                </c:when>
                                                <c:otherwise>
                                                    ${menuItem.englishName}
                                                </c:otherwise>
                                            </c:choose>
                                        </a>
                                    </c:forEach>
                                </div>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class=class="nav-item"><a class="nav-link" href="${itemUrl}">
                                <c:choose>
                                    <c:when test="${lang =='ru'}">
                                        ${item.russianName}
                                    </c:when>
                                    <c:otherwise>
                                        ${item.englishName}
                                    </c:otherwise>
                                </c:choose></a></li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#">${language}</a>
                    <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
                        <a class="dropdown-item" href="/language.html?language=en">English</a>
                        <a class="dropdown-item" href="/language.html?language=ru">Русский</a>
                    </div>
                </li>
            </div>
        </div>
    </div>
</nav>