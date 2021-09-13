<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="ct" tagdir="/WEB-INF/tags" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="localization.lang"/>

<c:set var="currentPage" value="${requestScope.currentPage}"/>

<html>
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <title><fmt:message key="link.pages.directions"/></title>
</head>
    <body>
        <jsp:include page="header.jsp"/>
        <form class="form-inline" action="directions">
            <div class="form-group mx-sm-3 mb-2">
                <input type="text" class="form-control" name="depotFrom" placeholder=<fmt:message key="directions.form.from"/>/>
            </div>
            <div class="form-group mx-sm-3 mb-2">
                <input type="text" class="form-control" name="depotTo" placeholder=<fmt:message key="directions.form.to"/>/>
            </div>
            <div class="form-group mx-sm-3 mb-2">
                <label for="sortBy"><fmt:message key="directions.sort.by"/></label>
                <select style="margin-left: 10px" type="text" id="sortBy" class="form-control form-select text-dark" name="sortBy">
                    <option value="none"><fmt:message key="directions.sort.none"/></option>
                    <option value="byFrom"><fmt:message key="directions.sort.from"/></option>
                    <option value="byTo"><fmt:message key="directions.sort.to"/></option>
                </select>
            </div>
            <input type="text" name="command" value="find-directions" hidden/>
            <button type="submit" class="btn btn-primary mb-2"><fmt:message key="directions.form.find"/></button>
        </form>
        <ct:directionList1 list="${requestScope.list}"/>
        <nav>
            <ul class="pagination">
                <label  style="margin: 0px 10px 0px 50px; vertical-align: middle; line-height: 200%"><fmt:message key="directions.pagination.page"/></label>
                <c:if test="${currentPage} eq 1">
                    <li class="page-item"><a class="page-link" href="#">Previous</a></li>
                </c:if>
                <c:forEach begin="0" end="${requestScope.pageCount}" step="1" var="page">
                    <li class="page-item"><a class="page-link" href="directions?command=get-directions&page=${page + 1}">${page + 1}</a></li>
                </c:forEach>
                <c:if test="${currentPage} eq ${requestScope.pageCount}">
                    <li class="page-item"><a class="page-link" href="#">Next</a></li>
                </c:if>
            </ul>
        </nav>
    </body>
</html>