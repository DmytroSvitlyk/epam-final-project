<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="ct" tagdir="/WEB-INF/tags" %>
<%@ attribute name="list" required="true" type="java.util.List" %>

<fmt:setBundle basename="localization.lang"/>

<table class="table table-sm table-bordered">
    <thead>
    <tr>
        <th scope="col"><fmt:message key="direction.info.depotFrom"/></th>
        <th scope="col"><fmt:message key="direction.info.depotTo"/></th>
        <th scope="col"><fmt:message key="direction.info.depotFromAddress"/></th>
        <th scope="col"><fmt:message key="direction.info.depotToAddress"/></th>
        <th scope="col"><fmt:message key="direction.info.departureTime"/></th>
        <th scope="col"><fmt:message key="direction.info.arrivalTime"/></th>
        <th scope="col"><fmt:message key="direction.info.range"/></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${list}" var="item">
        <tr>
            <ct:direction direction="${item}"/>
            <c:if test="${sessionScope.user.role.toString() ne 'ANONYMOUS'}">
                <td>
                    <a class="badge badge-success" href="directions?command=order-direction&id=${item.id}"><fmt:message key="directions.order"/></a>
                </td>
            </c:if>
            <c:if test="${sessionScope.user.role.toString() eq 'ADMIN'}">
                <td>
                    <a class="badge badge-danger" href="directions?command=direction-delete&id=${item.id}" onclick="return window.confirm('Are You Sure?')"><fmt:message key="directions.delete"/></a>
                </td>
            </c:if>
        </tr>
    </c:forEach>
    </tbody>
</table>