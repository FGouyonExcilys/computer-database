<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page isELIgnored="false"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>  

<!DOCTYPE html>
<html>
<head>
<title><spring:message code="label.title"/></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->

<spring:url value="resources/css/bootstrap.min.css" var="bootstrapCSS" />
<spring:url value="resources/css/font-awesome.css" var="fontAwesomeCSS" />
<spring:url value="resources/css/main.css" var="mainCSS" />
<spring:url value="https://cdnjs.cloudflare.com/ajax/libs/flag-icon-css/3.3.0/css/flag-icon.min.css" var="flagCSS" />

<link href="${bootstrapCSS}" rel="stylesheet" media="screen">
<link href="${fontAwesomeCSS}" rel="stylesheet" media="screen">
<link href="${mainCSS}" rel="stylesheet" media="screen">
<link href="${flagCSS}" rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="dropdown-item" href="?lang=<spring:message code="label.urlswitch"/>
			
							<c:if test="${ step != null }">&step=${ step }</c:if>
							<c:if test="${ search != '' }">&search=${ search }</c:if>
							<c:if test="${ orderBy != '' }">&orderBy=${ orderBy }</c:if>">
			<span class="flag-icon flag-icon-<spring:message code="label.flag" /> navbar-brand" style="width:1%; "></span></a>
			<a class="navbar-brand" href="dashboard">&nbsp;&nbsp;&nbsp;Application - 
				<spring:message code="label.title"/> </a>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<c:choose>
				<c:when test="${ addSuccess == '1'}">
					<div class="alert alert-success" role="alert">
						<b><i class="fa fa-check fa-lg"></i>&nbsp;&nbsp;<spring:message code="label.computerAddedSuccessfully" /></b>
					</div>
				</c:when>
				<c:when test="${ editSuccess == '1'}">
					<div class="alert alert-success" role="alert">
						<b><i class="fa fa-check fa-lg"></i>&nbsp;&nbsp;<spring:message code="label.computerModifiedSuccessfully" /></b>
					</div>
				</c:when>
				<c:when test="${ deleteSuccess == '1'}">
					<div class="alert alert-success" role="alert">
						<b><i class="fa fa-check fa-lg"></i>&nbsp;&nbsp;<spring:message code="label.computerDeletedSuccessfully" /></b>
					</div>
				</c:when>
			</c:choose>
			<c:choose>
				<c:when test="${ search != '' }">
					<c:if test="${ computerList.size() != 1 }">
						<h1 id="homeTitle">${ computerList.size() } <spring:message code="label.computersFoundFor"/> "${ search }" </h1>
					</c:if>
					<c:if test="${ computerList.size() == 1 }">
						<h1 id="homeTitle">${ computerList.size() } <spring:message code="label.computerFoundFor"/> "${ search }" </h1>
					</c:if>
				</c:when>
				<c:otherwise>
					<c:if test="${ computerList.size() != 1 }">
						<h1 id="homeTitle">${ computerList.size() } <spring:message code="label.computersFound"/></h1>
					</c:if>
					<c:if test="${ computerList.size() == 1 }">
						<h1 id="homeTitle">${ computerList.size() } <spring:message code="label.computerFound"/></h1>
					</c:if>
				</c:otherwise>
			</c:choose>

			<c:choose>
				<c:when
					test="${ pageIterator > 0 && pageIterator <= lastPageIndex }">
					<h4>Page ${ pageIterator }</h4>
				</c:when>
				<c:otherwise>
					<h4><spring:message code="label.noPageFound"/></h4>
				</c:otherwise>
			</c:choose>

			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="#" method="GET" class="form-inline" >
						<input type="search" id="search" name="search"
							class="form-control" placeholder="<spring:message code="label.searchName"/>" />
						<input type="submit" id="searchsubmit" name="searchsubmit"
							value="<spring:message code="label.filterByName"/>" class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer" href="addComputer">
						<spring:message code="label.addComputer" /></a> 
					<a class="btn btn-default" id="editComputer" href="#"
						onclick="$.fn.toggleEditMode();"><spring:message code="label.edit" /></a>
				</div>
			</div>
		</div>

		<form id="deleteForm" action="dashboard" method="POST">
			<input type="hidden" name="selection" id="selection" >
		</form>

		<div class="container" style="margin-top: 10px;">
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<!-- Variable declarations for passing labels as parameters -->
						<!-- Table header for Computer Name -->

						<th class="editMode" style="width: 60px; height: 22px;"><input
							type="checkbox" id="selectall" /> <span
							style="vertical-align: top;"> - <a href="#"
								id="deleteSelected" onclick="$.fn.deleteSelected();"> <i class="fa fa-trash-o fa-lg"></i>
							</a>
						</span></th>

						<th><c:choose>
								<c:when test="${ orderBy == 'name asc' }">
									<a href="dashboard?orderBy=name desc
										<c:if test="${ search != '' }">&search=${ search }</c:if>
										<c:if test="${ step != null }">&step=${ step }</c:if>"
										id="orderByComputerName"
										class="alert-link"><spring:message code="label.computerName" /></a> &#x25bc;
								</c:when>
								<c:when test="${ orderBy == 'name desc' }">
									<a href="dashboard?orderBy=name asc
										<c:if test="${ search != '' }">&search=${ search }</c:if>
										<c:if test="${ step != null }">&step=${ step }</c:if>"
										id="orderByComputerName"
										class="alert-link"><spring:message code="label.computerName" /></a> &#x25b2;
								</c:when>
								<c:otherwise>
									<a href="dashboard?orderBy=name asc
										<c:if test="${ search != '' }">&search=${ search }</c:if>
										<c:if test="${ step != null }">&step=${ step }</c:if>"
										id="orderByComputerName"
										class="alert-link"><spring:message code="label.computerName" /></a>
								</c:otherwise>
							</c:choose></th>

						<th><c:choose>
								<c:when test="${ orderBy == 'introduced asc' }">
									<a href="dashboard?orderBy=introduced desc
										<c:if test="${ search != '' }">&search=${ search }</c:if>
										<c:if test="${ step != null }">&step=${ step }</c:if>"
										id="orderByIntroduced"
										class="alert-link"><spring:message code="label.dateIntro" /></a> &#x25bc;
								</c:when>
								<c:when test="${ orderBy == 'introduced desc' }">
									<a href="dashboard?orderBy=introduced asc
										<c:if test="${ search != '' }">&search=${ search }</c:if>
										<c:if test="${ step != null }">&step=${ step }</c:if>"
										id="orderByIntroduced"
										class="alert-link"><spring:message code="label.dateIntro" /></a> &#x25b2;
								</c:when>
								<c:otherwise>
									<a href="dashboard?orderBy=introduced asc
										<c:if test="${ search != '' }">&search=${ search }</c:if>
										<c:if test="${ step != null }">&step=${ step }</c:if>"
										id="orderByIntroduced"
										class="alert-link"><spring:message code="label.dateIntro" /></a>
								</c:otherwise>
							</c:choose></th>
						<!-- Table header for Discontinued Date -->
						<th><c:choose>
								<c:when test="${ orderBy == 'discontinued asc' }">
									<a href="dashboard?orderBy=discontinued desc
										<c:if test="${ search != '' }">&search=${ search }</c:if>
										<c:if test="${ step != null }">&step=${ step }</c:if>"
										id="orderByDiscontinued"
										class="alert-link"><spring:message code="label.dateDisc" /></a> &#x25bc;
								</c:when>
								<c:when test="${ orderBy == 'discontinued desc' }">
									<a href="dashboard?orderBy=discontinued asc
										<c:if test="${ search != '' }">&search=${ search }</c:if>
										<c:if test="${ step != null }">&step=${ step }</c:if>"
										id="orderByDiscontinued"
										class="alert-link"><spring:message code="label.dateDisc" /></a> &#x25b2;
								</c:when>
								<c:otherwise>
									<a href="dashboard?orderBy=discontinued asc
										<c:if test="${ search != '' }">&search=${ search }</c:if>
										<c:if test="${ step != null }">&step=${ step }</c:if>"
										id="orderByDiscontinued"
										class="alert-link"><spring:message code="label.dateDisc" /></a>
								</c:otherwise>
							</c:choose></th>
						<!-- Table header for Company -->
						<th><c:choose>
								<c:when test="${ orderBy == 'company.name asc' }">
									<a href="dashboard?orderBy=company.name desc
										<c:if test="${ search != '' }">&search=${ search }</c:if>
										<c:if test="${ step != null }">&step=${ step }</c:if>"
										id="orderByCompanyName"
										class="alert-link"><spring:message code="label.company" /></a> &#x25bc;
								</c:when>
								<c:when test="${ orderBy == 'company.name desc' }">
									<a href="dashboard?orderBy=company.name asc
										<c:if test="${ search != '' }">&search=${ search }</c:if>
										<c:if test="${ step != null }">&step=${ step }</c:if>"
										id="orderByCompanyName"
										class="alert-link"><spring:message code="label.company" /></a> &#x25b2;
								</c:when>
								<c:otherwise>
									<a href="dashboard?orderBy=company.name asc
										<c:if test="${ search != '' }">&search=${ search }</c:if>
										<c:if test="${ step != null }">&step=${ step }</c:if>"
										id="orderByCompanyName"
										class="alert-link"><spring:message code="label.company" /></a>
								</c:otherwise>
							</c:choose></th>

					</tr>
				</thead>
				<!-- Browse attribute computers -->
				<tbody id="results">
					<c:choose>
						<c:when test="${ search != '' }">
							<c:forEach items="${ computerListDisplayed }" var="computer">
								<tr>
									<td class="editMode"><input type="checkbox" name="cb"
										class="cb" value="${ computer.getId() }"></td>
									<td><a href="editComputer?id=${ computer.getId() } "
										onclick=""><c:out value="${ computer.getName() }" /></a></td>
									<td><c:out value="${ computer.getIntroduced() }" /></td>
									<td><c:out value="${ computer.getDiscontinued() }" /></td>
									<td><c:out value="${ computer.getCompany().getName() }" /></td>
								<tr>
							</c:forEach>

						</c:when>
						<c:otherwise>
							<c:forEach items="${ computerListDisplayed }" var="computer">
								<tr>
									<td class="editMode"><input type="checkbox" name="cb"
										class="cb" value="${ computer.getId() }"></td>
									<td><a href="editComputer?id=${ computer.getId() } "
										onclick=""><c:out value="${ computer.getName() }" /></a></td>
									<td><c:out value="${ computer.getIntroduced() }" /></td>
									<td><c:out value="${ computer.getDiscontinued() }" /></td>
									<td><c:out value="${ computer.getCompany().getName() }" /></td>
								<tr>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>
		</div>
	</section>

	<footer class="navbar-fixed-bottom">
		<div class="container text-center">
			<ul class="pagination">
				<c:choose>
					<c:when test="${ lastPageIndex > 6 }">
						<li><c:choose>
								<c:when test="${ pageIterator >= 2 }">
									<a 
										href="dashboard?pageIterator=${ pageIterator - 1 }
										<c:if test="${ step != null }">&step=${ step }</c:if>
										<c:if test="${ search != '' }">&search=${ search }</c:if>
										<c:if test="${ orderBy != '' }">&orderBy=${ orderBy }</c:if>"
										aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
									</a>
								</c:when>
							</c:choose></li>
						<li><a
							href="dashboard?pageIterator=1
								<c:if test="${ step != null }">&step=${ step }</c:if>
								<c:if test="${ search != '' }">&search=${ search }</c:if>
								<c:if test="${ orderBy != '' }">&orderBy=${ orderBy }</c:if>">
								<c:out value="1" />
						</a></li>

						<c:choose>
							<c:when test="${ pageIterator > 4 }">
								<li><a href="#">...</a></li>
							</c:when>
						</c:choose>

						<c:choose>
							<c:when test="${ pageIterator <= 3 }">
								<c:forEach var="i" begin="2" end="5" step="1">
									<li><a
										href="dashboard?pageIterator=${ i }
										<c:if test="${ step != null }">&step=${ step }</c:if>
										<c:if test="${ search != '' }">&search=${ search }</c:if>
										<c:if test="${ orderBy != '' }">&orderBy=${ orderBy }</c:if>">
											<c:out value="${ i }" />
									</a></li>
								</c:forEach>
							</c:when>
							<c:when
								test="${ pageIterator > 3 && pageIterator < lastPageIndex - 3 }">
								<c:forEach var="i" begin="${ pageIterator - 2 }"
									end="${ pageIterator + 2 }" step="1">
									<li><a
										href="dashboard?pageIterator=${ i }
										<c:if test="${ step != null }">&step=${ step }</c:if>
										<c:if test="${ search != '' }">&search=${ search }</c:if>
										<c:if test="${ orderBy != '' }">&orderBy=${ orderBy }</c:if>">
											<c:out value="${ i }" />
									</a></li>
								</c:forEach>
							</c:when>
							<c:when test="${ pageIterator >= lastPageIndex - 3 }">
								<c:forEach var="i" begin="${ lastPageIndex - 5}"
									end="${ lastPageIndex -1 }" step="1">
									<li><a
										href="dashboard?pageIterator=${ i }
										<c:if test="${ step != null }">&step=${ step }</c:if>
										<c:if test="${ search != '' }">&search=${ search }</c:if>
										<c:if test="${ orderBy != '' }">&orderBy=${ orderBy }</c:if>">
											<c:out value="${ i }" />
									</a></li>
								</c:forEach>
							</c:when>
						</c:choose>

						<c:choose>
							<c:when test="${ pageIterator < lastPageIndex - 3 }">
								<li><a href="#">...</a></li>
							</c:when>
						</c:choose>

						<li><a
							href="dashboard?pageIterator=${ lastPageIndex }
							<c:if test="${ step != null }">&step=${ step }</c:if>
							<c:if test="${ search != '' }">&search=${ search }</c:if>
							<c:if test="${ orderBy != '' }">&orderBy=${ orderBy }</c:if>">
								<c:out value="${ lastPageIndex }" />
						</a></li>

						<li><c:choose>
								<c:when test="${ pageIterator != lastPageIndex }">
									<a
										href="dashboard?pageIterator=${ pageIterator + 1 }
										<c:if test="${ step != null }">&step=${ step }</c:if>
										<c:if test="${ search != '' }">&search=${ search }</c:if>
										<c:if test="${ orderBy != '' }">&orderBy=${ orderBy }</c:if>"
										aria-label="Next"><span aria-hidden="true">&raquo;</span></a>
								</c:when>
							</c:choose></li>
					</c:when>
					<c:otherwise>
						<li><c:choose>
								<c:when test="${ pageIterator >= 2 }">
									<a
										href="dashboard?pageIterator=${ pageIterator - 1 }
										<c:if test="${ step != null }">&step=${ step }</c:if>
										<c:if test="${ search != '' }">&search=${ search }</c:if>
										<c:if test="${ orderBy != '' }">&orderBy=${ orderBy }</c:if>"
										aria-label="Previous"> <span aria-hidden="true">&laquo;</span></a>
								</c:when>
							</c:choose></li>
						<c:forEach var="i" begin="1" end="${ lastPageIndex }" step="1">
							<li><a
								href="dashboard?pageIterator=${ i }
								<c:if test="${ step != null }">&step=${ step }</c:if>
								<c:if test="${ search != '' }">&search=${ search }</c:if>
								<c:if test="${ orderBy != '' }">&orderBy=${ orderBy }</c:if>">
									<c:out value="${ i }" />
							</a></li>
						</c:forEach>
						<li><c:choose>
								<c:when test="${ pageIterator != lastPageIndex }">
									<a
										href="dashboard?pageIterator=${ pageIterator + 1 }
										<c:if test="${ step != null }">&step=${ step }</c:if>
										<c:if test="${ search != '' }">&search=${ search }</c:if>
										<c:if test="${ orderBy != '' }">&orderBy=${ orderBy }</c:if>"
										aria-label="Next"><span aria-hidden="true">&raquo;</span></a>
								</c:when>
							</c:choose></li>
					</c:otherwise>
				</c:choose>
			</ul>
			<div class="btn-group btn-group-sm pull-right" role="group">
				<a href="dashboard?pageIterator=1&step=10
					<c:if test="${ search != '' }">&search=${ search }</c:if>
					<c:if test="${ orderBy != '' }">&orderBy=${ orderBy }</c:if>">
					<button type="button" class="btn btn-default"
						onclick="<c:set var="pageIterator" value="1" />">10</button>
				</a>
				<a href="dashboard?pageIterator=1&step=50
					<c:if test="${ search != '' }">&search=${ search }</c:if>
					<c:if test="${ orderBy != '' }">&orderBy=${ orderBy }</c:if>">
					<button
						type="button" class="btn btn-default"
						onclick="<c:set var="pageIterator" value="1" />">50</button>
				</a>
				<a href="dashboard?pageIterator=1&step=100
					<c:if test="${ search != '' }">&search=${ search }</c:if>
					<c:if test="${ orderBy != '' }">&orderBy=${ orderBy }</c:if>">
					<button
						type="button" class="btn btn-default"
						onclick="<c:set var="pageIterator" value="1" />">100</button>
				</a>
			</div>
		</div>
	</footer>
	
	<spring:url value="/resources/js/jquery.min.js" var="jqueryJS" />
	<spring:url value="/resources/js/bootstrap.min.js" var="bootstrapJS" />
	<spring:url value="/resources/js/dashboard.js" var="dashboardJS" />
	
	<script src="${jqueryJS}"></script>
	<script src="${bootstrapJS}"></script>
	<script src="${dashboardJS}"></script>

</body>
</html>