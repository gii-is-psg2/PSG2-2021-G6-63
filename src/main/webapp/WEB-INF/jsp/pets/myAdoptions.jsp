<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>


<petclinic:layout pageName="myAdoptions">
	<h2>
		<fmt:message key="myAdoptions" />
	</h2>

	<table id="petsTable" class="table table-striped">
		<thead>
			<tr>
				<th><fmt:message key="adoptions" /></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${adoptions}" var="adoption">
				<tr>
					<td><spring:url value="/pets/adoptions/{adoptionId}" var="adoptionUrl">
							<spring:param name="adoptionId" value="${adoption.id}" />
						</spring:url> <a href="${fn:escapeXml(adoptionUrl)}"><c:out value="${adoption.pet.name}" /></a>
					</td>

				</tr>
			</c:forEach>
		</tbody>
	</table>
</petclinic:layout>
