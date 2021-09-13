<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}" scope="session"/>
<fmt:setBundle basename="localization.lang"/>

<html>
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <title><fmt:message key="label.register.title"/></title>
</head>
<body>
<jsp:include page="header.jsp"/>
    <form action="register" method="post" style="margin-left: 200px" autocomplete="off">
        <h2><fmt:message key="label.register.title"/></h2><br>
        <c:out value="${sessionScope.error}"/><br>
        <div class="form-group">
            <label for="fName"><fmt:message key="label.form.firstname"/></label>
            <input type="text" class="form-control" id="fName" name="fName" required style="width: 300px"/>
        </div>
        <div class="form-group">
            <label for="sName"><fmt:message key="label.form.secondname"/></label>
            <input type="text" class="form-control" id="sName" name="sName" required style="width: 300px"/>
        </div>
        <div class="form-group">
            <label for="login"><fmt:message key="login.form.login"/></label>
            <input type="text" class="form-control" id="login" name="login" required style="width: 300px"/>
        </div>
        <div class="form-group">
            <label for="email"><fmt:message key="login.form.email"/></label>
            <input type="email" class="form-control" id="email" name="email" required style="width: 300px"/>
        </div>
        <div class="form-group">
            <label for="phone"><fmt:message key="login.form.phone"/></label>
            <input type="tel" class="form-control" id="phone" name="phone" required style="width: 300px"/>
        </div>
        <div class="form-group">
            <label for="password"><fmt:message key="login.form.password"/></label>
            <input type="password" class="form-control" id="password" name="password" required style="width: 300px"/>
        </div>
        <div class="form-group">
            <label for="confirm-password"><fmt:message key="login.form.confirm-password"/></label>
            <input type="password" class="form-control" id="confirm-password" name="cPassword" required style="width: 300px"/>
        </div>
        <div class="form-check">
            <input type="checkbox" class="form-check-input" id="aggr" required/>
            <label class="form-check-label" for="aggr"><fmt:message key="login.form.user-agreement"/></label>
        </div><br>
        <input type="text" value="register" name="command" hidden>
        <button type="submit" class="btn btn-primary"><fmt:message key="login.form.register"/></button><br><br>
        <a href="login?command=get-login-page"><fmt:message key="link.pages.login"/></a>
    </form>
</body>
</html>
