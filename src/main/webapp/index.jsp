<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>

<fmt:setLocale value="${sessionScope.lang}" scope="session"/>
<fmt:setBundle basename="localization.lang"/>

<html>
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <title><fmt:message key="title.pages.welcome"/></title>
</head>
<body>
    <jsp:include page="WEB-INF/jsp/header.jsp"/>
    <br>
    <br>
    <h1 style="margin-left: 50px"><fmt:message key="label.welcome.title"/></h1><br/>
    <h5 style="margin-left: 50px"><fmt:message key="label.welcome.details1"/></h5>
    <h5 style="margin-left: 50px"><fmt:message key="label.welcome.details2"/></h5>
    <h5 style="margin-left: 50px"><fmt:message key="label.welcome.details3"/></h5>
    <h5 style="margin-left: 50px"><fmt:message key="label.welcome.details4"/></h5>
</body>
</html>