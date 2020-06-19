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
	<jsp:include page="_menu.jsp" />

	<section id="main">
		<div class="container">
			<h1>Login</h1>

			<!-- /login?error=true -->
			<c:if test="${param.error == 'true'}">
				<div style="color: red; margin: 10px 0px;">

					Login Failed!!!<br /> Reason :
					${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}

				</div>
			</c:if>

			<h3>Enter username and password:</h3><br/>

			<form name='f' id="f"
				action="<c:url value="/j_spring_security_check"/>" method='POST'
				role="form">
				<table>
					<tr>
						<td>User:</td>
						<td><input type='text' name='username' value=''><br/></td>
					</tr>
					<tr>
						<td>Password:</td>
						<td><input type='password' name='password' /><br/></td>
					</tr>
					<tr>
						<td><br/><input name="Submit" type="submit" value="Submit" /></td>
					</tr>
				</table>
			</form>
		</div>
	</section>
</body>
</html>