<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="ct" tagdir="/WEB-INF/tags" %>

<%@ attribute name="direction" required="true" type="com.delivery.delivery.model.Direction" %>

<fmt:setBundle basename="localization.lang"/>
<td>${direction.depotFrom.name}</td>
<td>${direction.depotTo.name}</td>
<td>${direction.depotFrom.address}</td>
<td>${direction.depotTo.address}</td>
<td>${direction.departureTime}</td>
<td>${direction.arriveTime}</td>
<td>${direction.range}</td>
