<%@tag language="java" pageEncoding="UTF-8" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">Медицинская клиника</a>
        </div>
        <ul class="nav navbar-nav">
            <%--			<li class="active"><a href="#">Home</a></li>--%>
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
        </ul>
    </div>
</nav>


<%--<H1>Медицинская клиника<BR>«Healthy»</H1>--%>


<%--<div id="header">--%>

<%--	<c:if test="${not empty authorizedUser}">--%>
<%--		<UL class="right">--%>
<%--			<c:forEach items="${menu}" var="item">--%>
<%--				<c:url value="${item.url}" var="itemUrl"/>--%>
<%--				<LI class="item"><A href="${itemUrl}">${item.name}</A></LI>--%>
<%--			</c:forEach>--%>
<%--			<LI class="item">--%>
<%--				<c:url value="/img/arrow.gif" var="arrowUrl"/>--%>
<%--				<A href="#" class="drop-button">${authorizedUser.login} <IMG class="arrow" src="${arrowUrl}"></A>--%>
<%--				<OL class="drop">--%>
<%--					<c:url value="/profile/edit.html" var="profileEditUrl"/>--%>
<%--					<LI><A href="${profileEditUrl}">профиль</A></LI>--%>
<%--					<c:url value="/logout.html" var="logoutUrl"/>--%>
<%--					<LI><A href="${logoutUrl}">выход</A></LI>--%>
<%--				</OL>--%>
<%--			</LI>--%>
<%--		</UL>--%>
<%--	</c:if>--%>
<%--</div>--%>
