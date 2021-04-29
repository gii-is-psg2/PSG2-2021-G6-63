<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<petclinic:layout pageName="adoptionDetails">
	<h2>
		<fmt:message key="adoptionDetails" />
	</h2>
	
		<fmt:message key="adoptant" />
		
		
	<div>
	
		<table class="table table-striped">
		<tr>
			<th><fmt:message key="name" /></th>
			<td><b><c:out value="${adoption.adoptant.firstName} ${adoption.adoptant.lastName}" /></b></td>
		</tr>
		<tr>
			<th><fmt:message key="adress" /></th>
			<td><c:out value="${adoption.adoptant.address}" /></td>
		</tr>
		<tr>
			<th><fmt:message key="city" /></th>
			<td><c:out value="${adoption.adoptant.city}" /></td>
		</tr>
		<tr>
			<th><fmt:message key="phone" /></th>
			<td><c:out value="${adoption.adoptant.telephone}" /></td>
		</tr>
		
		<tr>
			<th><fmt:message key="username" /></th>
			<td><c:out value="${adoption.adoptant.user.username}" /></td>
		</tr>
	</table>
		
	</div>
	
	<div>
		<c:out value="${adoption.description}" />
	</div>
	
	<form:form modelAttribute="adoption" class="form-horizontal">
		<input type="hidden" name="description" value="${adoption.description}" />
		<input type="hidden" name="originalOwner" value="${adoption.originalOwner.id}" />
		<input type="hidden" name="adoptant" value="${adoption.adoptant.id}" />
		<input type="hidden" name="pet" value="${adoption.pet.id}" />
		<div>
			<form:checkbox path="accepted" label="Aceptar adopcion" />
		</div>

		<button class="btn btn-default" type="submit">
			<fmt:message key="confirm" />
		</button>

	</form:form>


</petclinic:layout>
