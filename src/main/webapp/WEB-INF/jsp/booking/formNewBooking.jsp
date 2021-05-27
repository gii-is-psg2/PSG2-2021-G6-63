<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<petclinic:layout pageName="owners">
     <jsp:attribute name="customScript">
        <script>
            $(function () {
                $("#checkIn").datepicker({dateFormat: 'yy/mm/dd', minDate:"#actualDate"});
            });
            
            $(function () {
                $("#checkOut").datepicker({dateFormat: 'yy/mm/dd', minDate:"#checkIn"});
            });
        </script>
    </jsp:attribute>
    
    <jsp:body>
    <h2><fmt:message key="newBooking"/></h2>
    
    <b><fmt:message key="pet"/></b>
    <table class="table table-striped">
    	<thead>
        <tr>
        	<th><fmt:message key="name"/></th>
            <th><fmt:message key="birthDate"/></th>
            <th><fmt:message key="type"/></th>
            <th><fmt:message key="owner"/></th>
        </tr>
        </thead>
        <tr>
        	<td><c:out value="${booking.pet.name}"/></td>
            <td><petclinic:localDate date="${booking.pet.birthDate}" pattern="yyyy/MM/dd"/></td>
            <td><c:out value="${booking.pet.type.name}"/></td>
            <td><c:out value="${booking.pet.owner.firstName} ${pet.owner.lastName}"/></td>
        </tr>
    </table>
    
    <form:form modelAttribute="booking" class="form-horizontal">
    	<div class="form-group has-feedback">  
    	<input type="hidden" name="pet" value="${petId}"/> 
    		<petclinic:inputField label="Detalles" name="details"/>
     		<petclinic:inputField label="Fecha de entrada" name="checkIn"/>
     		<petclinic:inputField label="Fecha de salida" name="checkOut"/>
     		
     	</div>
     	
     	<div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                
                    	<input type="hidden" name="petId" value="${booking.pet.id}"/>
                    	<button class="btn btn-default" type="submit"><fmt:message key="reserve"/></button>
                    
                </div>
            </div>  	
    </form:form>

            <br/>
        <b><fmt:message key="previousBookings"/></b>
        <table class="table table-striped">
            <tr>
                <th><fmt:message key="checkIn"/></th>
                <th><fmt:message key="checkOut"/></th>
                <th><fmt:message key="details"/></th>
            </tr>
            <c:forEach var="booking" items="${booking.pet.bookings}">
                <c:if test="${!booking['new']}">
                    <tr>
                        <td><petclinic:localDate date="${booking.checkIn}" pattern="yyyy/MM/dd"/></td>
                        <td><petclinic:localDate date="${booking.checkOut}" pattern="yyyy/MM/dd"/></td>
                        <td><c:out value="${booking.details}"/></td>
                    </tr>
                </c:if>
            </c:forEach>
        </table>
    </jsp:body>
</petclinic:layout>
