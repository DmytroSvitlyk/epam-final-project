<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}" scope="session"/>
<fmt:setBundle basename="localization.lang"/>
<html>
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <title><fmt:message key="direction.add.title"/></title>
</head>
<body>
    <jsp:include page="header.jsp"/>
    <form action="direction" method="post" style="margin-left: 200px" autocomplete="off">
        <h2><fmt:message key="direction.add.title"/></h2><br>
        <c:out value="${requestScope.error}"/><br>
        <div class="form-group">
            <label for="from"><fmt:message key="direction.info.depotFrom"/></label>
            <input type="text" class="form-control" id="from" name="depotFrom" required style="width: 300px"/>
        </div>
        <div class="form-group">
            <label for="to"><fmt:message key="direction.info.depotTo"/></label>
            <input type="text" class="form-control" id="to" name="depotTo" required style="width: 300px"/>
        </div>
        <div class="form-group">
            <label for="depTime"><fmt:message key="direction.info.departureTime"/></label>
            <input type="time" class="form-control" id="depTime" name="depTime" required style="width: 300px"/>
        </div>
        <div class="form-group">
            <label for="arrTime"><fmt:message key="direction.info.arrivalTime"/></label>
            <input type="time" class="form-control" id="arrTime" name="arrTime" required style="width: 300px"/>
        </div>
        <div class="form-group">
            <label for="range"><fmt:message key="direction.info.range"/></label>
            <input type="number" class="form-control" id="range" name="range" required style="width: 300px"/>
        </div>
        <input type="text" value="direction-add" name="command" hidden>
        <button type="submit" class="btn btn-primary"><fmt:message key="direction.add"/></button><br><br>
    </form>
</body>
</html>
