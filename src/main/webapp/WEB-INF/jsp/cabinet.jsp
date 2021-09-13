<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="ct" tagdir="/WEB-INF/tags" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="localization.lang"/>

<html>
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <title><fmt:message key="link.pages.cabinet"/></title>
</head>
<body>
    <jsp:include page="header.jsp"/>
    <div class="wrapper">
        <div class="sidebar">
            <div class="sidebar-header">
                <h3>Bootstrap Sidebar</h3>
            </div>
            <ul class="list-unstyled components">
                <li>
                    <a href="#"><fmt:message key="cabinet.sidebar.makeorder"/></a>
                </li>
                <li>
                    <a href="#"><fmt:message key="cabinet.sidebar.orders"/></a>
                </li>
                <c:if test="${sessionScope.user.role.toString()} eq 'ADMIN'">
                    <li>
                        <a href="#"><fmt:message key="cabinet.sidebar.userorders"/></a>
                    </li>
                    <li>
                        <a href="#"><fmt:message key="cabinet.sidebar.depots"/></a>
                    </li>
                </c:if>
            </ul>
        </div>
        <div class="content">

        </div>
    </div>
    <jsp:include page="${requestScope.subpage}"/>
</body>
</html>
