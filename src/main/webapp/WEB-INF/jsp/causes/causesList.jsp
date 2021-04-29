<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="causes">
	<h2><fmt:message key="causes"/></h2>
	<a class="btn btn-default" href='<spring:url value="/causes/new" htmlEscape="true"/>'><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-plus-circle" viewBox="0 0 16 16">
  	<path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z"/>
  	<path d="M8 4a.5.5 0 0 1 .5.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3A.5.5 0 0 1 8 4z"/>
	</svg><b>          </b><fmt:message key="createCause"/></a>
	
	<table id="ownersTable" class="table table-striped">
	<tbody>
	
		<c:forEach items="${causes.causeList}" var="cause">
		<tr>
		<td>
		<table style=" width: 100%;">
			<tr>
				<td style="width:80%">
					<h3><c:out value="${cause.name}"></c:out></h3>
				</td>
				<td style="width:20%">
					<p> <c:out value="${cause.getTotalDonations()}"></c:out> € de <c:out value="${cause.budgetTarget}"></c:out> € </p>

				</td>
			</tr>
			
			<tr>
				<td>
					<p><c:out value="${cause.description}"></c:out></p>					
				</td>
				<td>
				<c:if test="${!cause.isClosed}">
					<a class="btn btn-default" href='<spring:url value="/causes/${cause.id}/show" htmlEscape="true"/>'>Ver detalles</a>				
					<a class="btn btn-default" href='<spring:url value="/causes/${cause.id}/donation/new" htmlEscape="true"/>'>Donar</a>	
				</c:if>
				<c:if test="${cause.isClosed}">
					<p><b>Causa cerrada</b></p>
				</c:if>
				</td>
			</tr>
		</table>	
		</td>
		</tr>
		</c:forEach>
	</tbody>
	</table>
	
</petclinic:layout>