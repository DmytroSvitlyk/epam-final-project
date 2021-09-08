<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="localization.lang"/>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark justify-content-between">
    <a class="navbar-brand" href="#">MyDELIVERY</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav">
            <li class="nav-item">
                <a class="nav-link" href="#"><fmt:message key="link.pages.homepage"/><span class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#"><fmt:message key="link.pages.directions"/></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#"><fmt:message key="link.pages.calcprice"/></a>
            </li>
            <li class="nav-item">
                <c:choose>
                    <c:when test="${sessionScope.user.role.toString() eq 'ANONYMOUS'}">
                        <a class="nav-link" href="#"><fmt:message key="link.pages.login"/></a>
                    </c:when>
                    <c:otherwise>
                        <a class="nav-link" href="#"><fmt:message key="link.pages.cabinet"/></a>
                        <a class="nav-link" href="#"><fmt:message key="link.pages.logout"/></a>
                    </c:otherwise>
                </c:choose>
            </li>
            <form class = "form-inline" style="margin-left: 600px" action="FrontController">
                <select class="form-select select-dark bg-dark text-light" name="lang" onchange="submit()">
                    <option value="en" ${sessionScope.lang == 'en' ? 'selected': ''}><fmt:message key="label.lan.en"/></option>
                    <option value="uk" ${sessionScope.lang == 'uk' ? 'selected': ''}><fmt:message key="label.lan.uk"/></option>
                </select>
                <input type="text" name="command" value="localeChange" hidden>
            </form>
        </ul>
    </div>
</nav>
