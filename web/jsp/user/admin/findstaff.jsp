<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <c:url value="/index.html" var="indexActionURL"/>
    <c:url value="/login.html" var="loginActionURL"/>
    <c:url value="/register.html" var="registerActionURL"/>
    <c:url value="/logout.html" var="logoutActionURL"/>
    <c:url value="/user/profile.html" var="profileActionURL"/>
    <c:url value="/user/useredit.html" var="userEditActionURL"/>
    <c:url value="/user/userdelete.html" var="userDeleteActionURL"/>
    <fmt:setLocale value="${sessionLang}"/>
    <fmt:setBundle basename="by.training.finaltask.resource.localization"/>
    <title><fmt:message key="findStaff"/></title>
    <!-- Bootstrap core CSS -->
    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <script src="js/popper.min.js"></script>

</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark static-top">
    <div class="container">

        <a class="navbar-brand" href="${indexActionURL}">
            <fmt:message key="title"/></a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive"
                aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarResponsive">
            <ul class="navbar-nav ml-auto">
                <li class="nav-item">
                    <a class="nav-link" href="${indexActionURL}">
                        <fmt:message key="home"/></a>
                    <span class="sr-only">(current)</span></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/pets/findpet.html"><fmt:message key="findAPet"/></a><span
                        class="sr-only">(current)</span></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/adoptions/guest/adoptpet.html"><fmt:message key="adoptAPet"/><span
                            class="sr-only">(current)</span></a>
                </li>
                <c:if test="${not empty authorizedUser}">
                    <c:choose>
                        <c:when test="${authorizedUser.userRole == 'ADMINISTRATOR'}">
                            <li class="nav-item active">
                                <a class="nav-link" href="<c:url value="/user/admin/findstaff.html"/>">
                                    <fmt:message key="findStaff"/></a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="<c:url value="/user/admin/addstaff.html"/>">
                                    <fmt:message key="addStaff"/></a>
                            </li>
                        </c:when>
                    </c:choose>
                    <li class="nav-item">
                        <a class="nav-link" href="${profileActionURL}">
                            <fmt:message key="profile"/>(${authorizedUser.username})</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${logoutActionURL}"><fmt:message key="logout"/></a>
                    </li>
                </c:if>
                <c:if test="${empty authorizedUser}">
                    <li class="nav-item">
                        <a class="nav-link" href="${loginActionURL}"><fmt:message key="login"/></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${registerActionURL}"><fmt:message key="register"/></a>
                    </li>
                </c:if>
            </ul>
        </div>
    </div>
</nav>
<form action="<c:url value="/user/admin/findstaff.html"/>" method="post">
    <select name="lang" class="custom-select-sm float-right">
        <option value="${sessionLang}"><fmt:message key="pickLanguage"/></option>
        <option value="en_US"><fmt:message key="english"/></option>
        <option value="ru_RU"><fmt:message key="russian"/></option>
        <option value="de_DE"><fmt:message key="german"/></option>
    </select>
    <button class="btn float-right" type="submit"><fmt:message key="changeLanguage"/></button>
</form>

<br>
<c:if test="${not empty message}">
    <center>
        <label class="text text-danger" for="navbarResponsive"><fmt:message key="${message}"/></label>
    </center>
</c:if>

<legend>
    <center><h2><b><fmt:message key="findStaff"/> </b></h2></center>
</legend>
<br>
<div class="table">

    <table class="table">
        <thead>

        <tr>
            <th>ID</th>
            <th><fmt:message key="username"/></th>
            <th><fmt:message key="role"/></th>
            <th><fmt:message key="email"/></th>
            <th><fmt:message key="firstName"/></th>
            <th><fmt:message key="lastName"/></th>
            <th><fmt:message key="dateofbirth"/></th>
            <th><fmt:message key="address"/></th>
            <th><fmt:message key="contactNumber"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${resultUsers}" var="users" varStatus="status">
            <tr>
            <td><c:out value="${users.id}"/></td>
            <td><c:out value="${users.username}"/></td>
                <td><c:out value="${users.userRole.getName()}"/></td>
            <td><c:out value="${resultsUserInfo[status.index].email}"/></td>
            <td><c:out value="${resultsUserInfo[status.index].firstName}"/></td>
            <td><c:out value="${resultsUserInfo[status.index].lastName}"/></td>
            <td><fmt:formatDate type="date" dateStyle="medium"
                                value="${resultsUserInfo[status.index].dateOfBirth.time}"/>
            </td>
            <td><c:out value="${resultsUserInfo[status.index].address}"/></td>
            <td>+<c:out value="${resultsUserInfo[status.index].phone}"/></td>
            </tr>
        </c:forEach>
        </tbody>

    </table>
</div>
</body>
</html>
