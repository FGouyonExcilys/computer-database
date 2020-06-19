<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>  

<header class="navbar navbar-inverse navbar-fixed-top justify-content-between">
		<div class="container">
			<a class="dropdown-item mr-auto" href="?lang=<spring:message code="label.urlswitch"/>">
			<span class="flag-icon flag-icon-<spring:message code="label.flag" /> navbar-brand" style="width:1%; "></span></a>
			<a class="navbar-brand mr-auto" href="dashboard">&nbsp;&nbsp;&nbsp;Application - 
				<spring:message code="label.title"/> </a>
				
			<c:if test="${pageContext.request.userPrincipal.name != null}">
     			<a href="${pageContext.request.contextPath}/logout" class="navbar-brand mr-auto navbar-right">Logout</a>
			</c:if>
			
		</div>
</header>