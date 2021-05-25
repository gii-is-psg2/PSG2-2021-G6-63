<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="home">
    <h1><b><fmt:message key="welcome"/></b></h1>
    <div class="row">
        <div class="col-md-12">
            <spring:url value="/resources/images/unnamed.png" htmlEscape="true" var="petsImage"/>
            <img class="img-responsive" src="${petsImage}" style="max-width: 1200px; max-height: 800px"/>
        </div>
    </div>
</petclinic:layout>
