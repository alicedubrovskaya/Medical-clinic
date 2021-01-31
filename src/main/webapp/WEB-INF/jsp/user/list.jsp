<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--
  Created by IntelliJ IDEA.
  User: Алиса
  Date: 16.01.2021
  Time: 16:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


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


<u:html title="${user_list}" message="${message}">
    <div class="container">
        <div class="main-head">
            <h2>${user_list}</h2>
        </div>
        <table class="table table-hover">
            <thead>
            <tr>
                <th>${user_login}</th>
                <th>${user_role}</th>
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
        <ul class="pagination justify-content-center">
            <c:choose>
                <c:when test="${currentPage==1}">
                    <li class="page-item disabled">
                        <a class="page-link" aria-disabled="true" href="#">${previous}</a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="page-item">
                        <a class="page-link" href="/user/list.html?page=${currentPage-1}">${previous}</a>
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
                        <li class="page-item"><a class="page-link" href="/user/list.html?page=${i}">${i}</a></li>
                    </c:otherwise>
                </c:choose>
            </c:forEach>

            <c:choose>
                <c:when test="${currentPage != noOfPages}">
                    <li class="page-item"><a class="page-link" href="/user/list.html?page=${currentPage+1}">${next}</a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="page-item disabled">
                        <a class="page-link" aria-disabled="true" href="#">${next}</a>
                    </li>
                </c:otherwise>z
            </c:choose>
        </ul>
    </div>
</u:html>
