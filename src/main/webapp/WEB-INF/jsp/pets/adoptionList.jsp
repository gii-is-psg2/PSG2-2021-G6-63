<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>


<petclinic:layout pageName="petsInAdoption">
	<h2>
		<fmt:message key="petsInAdoption" />
	</h2>

	<table id="petsTable" class="table table-striped">
		<thead>
			<tr>
				<th><fmt:message key="name" /></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${pets}" var="pet">
				<tr>
					<td><spring:url value="/pets/{petId}/adopt" var="petUrl">
							<spring:param name="petId" value="${pet.id}" />
						</spring:url> <a href="${fn:escapeXml(petUrl)}"><c:out value="${pet.name}" /></a>
					</td>



					<!--
                <td> 
                    <c:out value="${owner.user.username}"/> 
                </td>
                <td> 
                   <c:out value="${owner.user.password}"/> 
                </td> 
-->

				</tr>
			</c:forEach>
		</tbody>
	</table>
	<spring:url value="/pets/myAdoptions" var="myAdoptionsUrl">
	</spring:url>
	<a href="${fn:escapeXml(myAdoptionsUrl)}" class="btn btn-default"><fmt:message
			key="myAdoptions" /></a>
</petclinic:layout>
