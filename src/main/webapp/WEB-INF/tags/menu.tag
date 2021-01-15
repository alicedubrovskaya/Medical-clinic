<%@tag language="java" pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<DIV id="header">
	<H1>Медицинская клиника<BR>«Healthy»</H1>
	<c:if test="${not empty authorizedUser}">
		<UL class="right">
			<c:forEach items="${menu}" var="item">
				<c:url value="${item.url}" var="itemUrl"/>
				<LI class="item"><A href="${itemUrl}">${item.name}</A></LI>
			</c:forEach>
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
		</UL>
	</c:if>
</DIV>
