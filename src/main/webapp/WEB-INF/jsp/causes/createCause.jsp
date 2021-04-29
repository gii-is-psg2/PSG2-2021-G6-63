<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<petclinic:layout pageName="causes">
<h2>Crear causa</h2>
	    <form:form modelAttribute="cause" class="form-horizontal" id="add-cause-form">
	        <div class="form-group has-feedback">	        
	    		<petclinic:inputField label="Nombre" name="name"/>
	    		<petclinic:inputField label="Descripcion" name="description"/>
	        	<petclinic:inputField label="Objetivo" name="budgetTarget"/>
	        	<petclinic:inputField label="Organizacion" name="organization"/>
	        	<input type="hidden" name="isClosed" value="false"/>
	    	</div>
	    	<button class="btn btn-default" type="submit">Guardar</button>
        </form:form>
</petclinic:layout>