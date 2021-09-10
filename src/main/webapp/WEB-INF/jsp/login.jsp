<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}" scope="session"/>
<fmt:setBundle basename="localization.lang"/>

<html>
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <title><fmt:message key="label.login.title"/></title>
</head>
<body>
<jsp:include page="header.jsp"/>
<form action="login" method="post" style="margin-left: 200px" autocomplete="off">
    <h2><fmt:message key="label.login.title"/></h2><br>
    <c:out value="${sessionScope.error}"/><br>
    <div class="form-group">
        <label for="login"><fmt:message key="label.form.login"/></label>
        <input type="text" class="form-control" id="login" name="login" required style="width: 300px"/>
    </div>
    <div class="form-group">
        <label for="password"><fmt:message key="label.form.password"/></label>
        <input type="password" class="form-control col-xs-2" id="password" name="password" required style="width: 300px"/>
    </div>
    <input type="text" value="login" name="command" hidden>
    <button type="submit" class="btn btn-primary">Submit</button><br><br>
    <a href="register?command=get-register-page"><fmt:message key="link.pages.register"/></a>
</form>
</body>
</html>
