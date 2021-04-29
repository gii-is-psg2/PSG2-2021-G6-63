<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="causes">
	<h2><c:out value="${cause.name}"></c:out></h2>
	<p><c:out value="${cause.description}"></c:out></p>

	<h2>Donaciones: </h2>
	<table style="width:100%">
	<tr>
		<td style="width:75%">
		<c:if test="${cause.getTotalDonations() >= 1}">
    	<table id="donationsTable" class="table table-striped" style="width:95%">
        <thead>
        <tr>
            <th>Fecha</th>
            <th>Cantidad</th>
            <th>Cliente</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${cause.donations}" var="donation">
            <tr>
                <td>
                    <c:out value="${donation.date}"></c:out>
                </td>
                <td>
                    <c:out value="${donation.quantity}"></c:out> €
                </td>
                <td>
                	<c:out value="${donation.owner.firstName}"></c:out>
                </td>
            </tr>
		</c:forEach>

        </tbody>
    	</table>
    	</c:if>
    	
    	<c:if test="${cause.getTotalDonations() < 1}">
    	<p>No se han realizado donaciones. Sé el primero 😀</p>
    	</c:if>
    	
    
		</td>
		<td>
		
		<progress max="${cause.budgetTarget}" value="${cause.getTotalDonations()}"> </progress>
		
		<h3><b>Cantidad recaudada: </b></h3>
		<h3><c:out value="${cause.getTotalDonations()}"></c:out> €</h3>
		
		<h3><b>Cantidad objetivo: </b></h3>
		<h3><c:out value="${cause.budgetTarget}"></c:out> €</h3>

		</td>
	</tr>
	</table>
	
</petclinic:layout>