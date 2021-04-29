<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<petclinic:layout pageName="pet">

	<h2>
		<fmt:message key="pet" />
	</h2>

	<table>

		<tr>
			<td valign="top">
				<dl class="dl-horizontal">
					<dt>
						<fmt:message key="name" />
					</dt>
					<dd>
						<c:out value="${pet.name}" />
					</dd>
					<dt>
						<fmt:message key="birthDate" />
					</dt>
					<dd>
						<petclinic:localDate date="${pet.birthDate}" pattern="yyyy-MM-dd" />
					</dd>
					<dt>
						<fmt:message key="type" />
					</dt>
					<dd>
						<c:out value="${pet.type.name}" />
					</dd>
				</dl>
			</td>

		</tr>

	</table>

	<h2>
		<fmt:message key="owner" />
	</h2>

	<table class="table table-striped">
		<tr>
			<th><fmt:message key="name" /></th>
			<td><b><c:out value="${owner.firstName} ${owner.lastName}" /></b></td>
		</tr>
		<tr>
			<th><fmt:message key="adress" /></th>
			<td><c:out value="${owner.address}" /></td>
		</tr>
		<tr>
			<th><fmt:message key="city" /></th>
			<td><c:out value="${owner.city}" /></td>
		</tr>
		<tr>
			<th><fmt:message key="phone" /></th>
			<td><c:out value="${owner.telephone}" /></td>
		</tr>
		
		<tr>
			<th><fmt:message key="username" /></th>
			<td><c:out value="${owner.user.username}" /></td>
		</tr>
	</table>

	<spring:url value="{petId}/adopt" var="adoptUrl">
		<spring:param name="petId" value="${pet.id}" />
	</spring:url>
	<a href="${fn:escapeXml(adoptUrl)}" class="btn btn-default"><fmt:message
			key="adoptPet" /></a>

</petclinic:layout>
