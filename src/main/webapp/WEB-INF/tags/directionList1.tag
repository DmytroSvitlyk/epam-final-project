<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="ct" tagdir="/WEB-INF/tags" %>
<%@ attribute name="list" required="true" type="java.util.List" %>

<fmt:setBundle basename="localization.lang"/>

<table class="table" style="margin: 0 50px 0 50px">
    <thead>
    <tr>
        <th scope="col">id</th>
        <th scope="col">from</th>
        <th scope="col">to</th>
        <th scope="col">range</th>
        <th scope="col">-</th>
        <th scope="col">-</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${list}" var="item">
        <tr>
            <ct:direction direction="${item}"/>
            <td>
                <a class="badge badge-danger" href="directions?command=remove-direction&id=${item.id}"><fmt:message key="directions.delete"/></a>
            </td>
            <td>
                <a class="badge badge-info" href="directions?command=direction-info&id=${item.id}"><fmt:message key="directions.more"/></a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>