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


<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">${medicalClinic}</a>
        </div>
        <ul class="nav navbar-nav">
            <%--			<li class="active"><a href="#">Home</a></li>--%>
            <c:choose>
                <c:when test="${not empty authorizedUser}">
                    <li><a href="/logout.html">${logout}</a></li>
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
                            <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                                <c:choose>
                                    <c:when test="${lang =='ru'}">
                                        ${item.russianName}
                                    </c:when>
                                    <c:otherwise>
                                        ${item.englishName}
                                    </c:otherwise>
                                </c:choose>
                                <span class="caret"></span></a>
                            <ul class="dropdown-menu">
                                <c:forEach items="${item.menuItemsDropDown}" var="menuItem">
                                    <li>
                                        <a href="${menuItem.url}">
                                            <c:choose>
                                                <c:when test="${lang =='ru'}">
                                                    ${menuItem.russianName}
                                                </c:when>
                                                <c:otherwise>
                                                    ${menuItem.englishName}
                                                </c:otherwise>
                                            </c:choose>
                                        </a>
                                    </li>
                                </c:forEach>
                            </ul>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="item"><a href="${itemUrl}">
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
            <li class="dropdown">
                <a class="dropdown-toggle" data-toggle="dropdown" href="#">${language}
                    <span class="caret"></span></a>
                <ul class="dropdown-menu">
                    <li class="item"><a href="/language.html?language=en">English</a></li>
                    <li class="item"><a href="/language.html?language=ru">Русский</a></li>
                </ul>
            </li>
        </ul>
    </div>
</nav>