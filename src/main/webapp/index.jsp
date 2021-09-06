<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
    <h1>
        <%= "Hello World!" %>
    </h1>
    <br/>
    <form method="post" action="hello-servlet">
        <input name = "login" type = "text" placeholder="login"/><br>
        <input name = "password" type = "password" placeholder="password"/><br>
        <input name = "fname" type = "text" placeholder="first name"/><br>
        <input name = "sname" type = "text" placeholder="second name"/><br>
        <input name = "phone" type = "tel" placeholder="phone number"/><br>
        <input name = "email" type = "email" placeholder="example@mail.com"/><br>
        <input type="submit" value="reg">
    </form>
    ${error}
</body>
</html>