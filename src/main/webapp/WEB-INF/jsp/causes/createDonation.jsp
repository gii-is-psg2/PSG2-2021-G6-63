<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<petclinic:layout pageName="donations">
	<h2>Crear donación</h2>
	<form:form modelAttribute="donation" class="form-horizontal" id="add-donation-form">
	        <div class="form-group has-feedback">	 
	        	<petclinic:inputField label="Cantidad" name="quantity"/>
	        </div>
	        <button class="btn btn-default" type="submit">Guardar</button>
	               	
	</form:form>
</petclinic:layout>