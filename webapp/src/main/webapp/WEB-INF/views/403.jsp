<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page isELIgnored="false"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 

<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<spring:url value="resources/css/bootstrap.min.css" var="bootstrapCSS" />
<spring:url value="resources/css/font-awesome.css" var="fontAwesomeCSS" />
<spring:url value="resources/css/main.css" var="mainCSS" />
<link href="${bootstrapCSS}" rel="stylesheet" media="screen">
<link href="${fontAwesomeCSS}" rel="stylesheet" media="screen">
<link href="${mainCSS}" rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="dashboard.html"> Application -
				Computer Database </a>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<div class="alert alert-danger">
				Error 403: Access denied! <br />
				<!-- stacktrace -->
			</div>
		</div>
	</section>

	<spring:url value="/resources/js/jquery.min.js" var="jqueryJS" />
	<spring:url value="/resources/js/bootstrap.min.js" var="bootstrapJS" />
	<spring:url value="/resources/js/dashboard.js" var="dashboardJS" />
	
	<script src="${jqueryJS}"></script>
	<script src="${bootstrapJS}"></script>
	<script src="${dashboardJS}"></script>

</body>
</html>