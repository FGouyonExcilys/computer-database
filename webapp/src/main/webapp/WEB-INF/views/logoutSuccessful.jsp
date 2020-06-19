<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page isELIgnored="false"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html>
<head>
<title><spring:message code="label.title" /></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->

<spring:url value="resources/css/bootstrap.min.css" var="bootstrapCSS" />
<spring:url value="resources/css/font-awesome.css" var="fontAwesomeCSS" />
<spring:url value="resources/css/main.css" var="mainCSS" />
<spring:url
	value="https://cdnjs.cloudflare.com/ajax/libs/flag-icon-css/3.3.0/css/flag-icon.min.css"
	var="flagCSS" />

<link href="${bootstrapCSS}" rel="stylesheet" media="screen">
<link href="${fontAwesomeCSS}" rel="stylesheet" media="screen">
<link href="${mainCSS}" rel="stylesheet" media="screen">
<link href="${flagCSS}" rel="stylesheet" media="screen">
</head>
<body>
	<section id="main">
		<div class="container">
			<jsp:include page="_menu.jsp" />

			<h1>Logout Successful!</h1>
			<br/>
			<a href="${pageContext.request.contextPath}/login" class="btn btn-primary"
				role="button">Se connecter</a>

			<spring:url value="/resources/js/jquery.min.js" var="jqueryJS" />
			<spring:url value="/resources/js/bootstrap.min.js" var="bootstrapJS" />
			<spring:url value="/resources/js/dashboard.js" var="dashboardJS" />

			<script src="${jqueryJS}"></script>
			<script src="${bootstrapJS}"></script>
			<script src="${dashboardJS}"></script>
		</div>
	</section>
</body>
</html>