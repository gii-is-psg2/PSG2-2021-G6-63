<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<petclinic:layout pageName="adoption">
	<form:form modelAttribute="adoption" class="form-horizontal">
	<input type="hidden" name="originalOwner"
			value="${originalOwner.id}" />
		<input type="hidden" name="originalOwner"
			value="${originalOwner.id}" />
			<input type="hidden" name="adoptant"
			value="${adoptant.id}" />
			<input type="hidden" name="pet"
			value="${pet.id}" />
		<div class="form-group has-feedback">
			<petclinic:inputField label="Descripción" name="description" />
		</div>
		
		<div>
		
			<h2>
		<fmt:message key="owner" />
	</h2>

	<table class="table table-striped">
		<tr>
			<th><fmt:message key="name" /></th>
			<td><b><c:out value="${originalOwner.firstName} ${originalOwner.lastName}" /></b></td>
		</tr>
		<tr>
			<th><fmt:message key="adress" /></th>
			<td><c:out value="${originalOwner.address}" /></td>
		</tr>
		<tr>
			<th><fmt:message key="city" /></th>
			<td><c:out value="${originalOwner.city}" /></td>
		</tr>
		<tr>
			<th><fmt:message key="phone" /></th>
			<td><c:out value="${originalOwner.telephone}" /></td>
		</tr>
		
		<tr>
			<th><fmt:message key="username" /></th>
			<td><c:out value="${originalOwner.user.username}" /></td>
		</tr>
	</table>
			
		
		</div>
		
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<button class="btn btn-default" type="submit">
					<fmt:message key="adoptPet" />
				</button>
			</div>
		</div>
	</form:form>
</petclinic:layout>
