<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="localization.lang"/>
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark justify-content-between">
    <a class="navbar-brand" href="#">MyDELIVERY</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav">
            <li class="nav-item">
                <a class="nav-link" href="home?command=get-home-page"><fmt:message key="link.pages.homepage"/><span class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="directions?command=get-directions-page"><fmt:message key="link.pages.directions"/></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#"><fmt:message key="link.pages.calcprice"/></a>
            </li>
            <c:choose>
                <c:when test="${sessionScope.user.role.toString() eq 'ANONYMOUS'}">
                    <li class="nav-item">
                        <a class="nav-link" href="login?command=get-login-page"><fmt:message key="link.pages.login"/></a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class = nav-item>
                        <a class="nav-link" href="cabinet?command=get-cabinet-page"><fmt:message key="link.pages.cabinet"/></a>
                    </li>
                    <li class = nav-item>
                        <a class="nav-link" href="logout?command=logout"><fmt:message key="link.pages.logout"/></a>
                    </li>
                </c:otherwise>
            </c:choose>
            <form class = "form-inline" style="margin-right: 100px" action="change-lang">
                <select class="form-select select-dark bg-dark text-light" name="lang" onchange="submit()">
                    <option value="en" ${sessionScope.lang == 'en' ? 'selected': ''}><fmt:message key="label.lan.en"/></option>
                    <option value="uk" ${sessionScope.lang == 'uk' ? 'selected': ''}><fmt:message key="label.lan.uk"/></option>
                </select>
                <input type="text" name="command" value="locale-change" hidden>
            </form>
        </ul>
    </div>
</nav>
<br><br>
