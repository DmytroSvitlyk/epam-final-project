<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="ct" tagdir="/WEB-INF/tags" %>

<%@ attribute name="direction" required="true" type="com.delivery.delivery.model.Direction" %>

<fmt:setBundle basename="localization.lang"/>
<th scope="col">${direction.getId()}</th>
<td>${direction.getDepotFrom().getName()}</td>
<td>${direction.getDepotTo().getName()}</td>
<td>${direction.getRange()}</td>
