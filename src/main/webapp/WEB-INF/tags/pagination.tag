<%@tag language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@attribute name="entity" required="true" rtexprvalue="true" type="java.lang.String" %>

<c:choose>
    <c:when test="${sessionScope.language != null}">
        <fmt:setLocale value="${sessionScope.language}"/>
    </c:when>
    <c:otherwise>
        <fmt:setLocale value="en"/>
    </c:otherwise>
</c:choose>

<fmt:setBundle basename="textResources" var="textResources" scope="session"/>
<fmt:message bundle="${textResources}" key="user.list" var="user_list"/>
<fmt:message bundle="${textResources}" key="user.login" var="user_login"/>
<fmt:message bundle="${textResources}" key="user.password" var="user_password"/>
<fmt:message bundle="${textResources}" key="user.role" var="user_role"/>
<fmt:message bundle="${textResources}" key="page.next" var="next"/>
<fmt:message bundle="${textResources}" key="page.previous" var="previous"/>

<ul class="pagination justify-content-center">
    <c:url value="/${entity}" var="entity"/>
    <c:choose>
        <c:when test="${currentPage==1}">
            <li class="page-item disabled">
                <a class="page-link" aria-disabled="true" href="#">${previous}</a>
            </li>
        </c:when>
        <c:otherwise>
            <li class="page-item">
                <a class="page-link" href="${entity}/list.html?page=${currentPage-1}">${previous}</a>
            </li>
        </c:otherwise>
    </c:choose>

    <c:forEach begin="1" end="${noOfPages}" var="i">
        <c:choose>
            <c:when test="${currentPage == i}">
                <li class="page-item active" aria-current="page">
                    <a class="page-link" href="#">${i}</a>
                </li>
            </c:when>
            <c:otherwise>
                <li class="page-item"><a class="page-link" href="${entity}/list.html?page=${i}">${i}</a></li>
            </c:otherwise>
        </c:choose>
    </c:forEach>

    <c:choose>
        <c:when test="${currentPage != noOfPages}">
            <li class="page-item"><a class="page-link" href="${entity}/list.html?page=${currentPage+1}">${next}</a>
            </li>
        </c:when>
        <c:otherwise>
            <li class="page-item disabled">
                <a class="page-link" aria-disabled="true" href="#">${next}</a>
            </li>
        </c:otherwise>
    </c:choose>
</ul>