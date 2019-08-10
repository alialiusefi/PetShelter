<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <fmt:setLocale value="${sessionLang}"/>
    <fmt:setBundle basename="by.training.finaltask.resource.localization"/>
    <title><fmt:message key="findAPet"/></title>
    <!-- Bootstrap core CSS -->
    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <script src="js/popper.min.js"></script>
</head>
<body>

<jsp:include page="/jsp/tags/menu.jsp" flush="true"/>

<legend>
    <center><h2><b><fmt:message key="findAPet"/> </b></h2></center>
</legend>
<c:if test="${not empty message}">
    <center>
        <label class="text text-danger">
            <fmt:message key="${message}"/>
        </label>
    </center>
</c:if>
<c:if test="${not empty successMessage}">
    <center>
        <label class="text text-danger">
            <fmt:message key="${successMessage  }"/>
        </label>
    </center>
</c:if>
<br>

<div class="table table-bordered justify-content-center mx-auto p-3">
    <div class="form-row justify-content-center">
        <div class="form-group">
            <center><h3><fmt:message key="searchForm"/></h3></center>
        </div>
        <br>
        <form name="searchForm" onsubmit="return OnSubmitForm();" method="post">
            <c:if test="${authorizedUser.userRole == 'STAFF'}">
                <div class="form-group p-lg-2">
                    <select name="petStatus" id="inputStatus" class="form-control">
                        <option value="ALL">
                            <fmt:message key="allStatus"/>
                        </option>
                        <option value="SHELTERED">
                            <fmt:message key="sheltered"/>
                        </option>
                        <option value="ADOPTED">
                            <fmt:message key="adopted"/>
                        </option>
                        <option value="DEAD">
                            <fmt:message key="dead"/>
                        </option>
                    </select>
                    <i><fmt:message key="appliesToAllSearchTypes"/></i>
                </div>
            </c:if>
            <div class="form-inline col-md-12 justify-content-center ">
                <input pattern="^[a-zA-Z]{1,10}+$" name="petName" id="inputpetName" class="form-control"
                       placeholder="<fmt:message key="petName"/>" value="${sessionScope.petName}">
                &emsp;
                <input type="submit" name="findByPetName" onclick="document.pressed=this.name"
                       value="<fmt:message key="findByPetName"/>" class="btn-sm btn-primary">
            </div>
            <div class="form-inline p-md-2">
                <select name="breed" id="inputBreed" class="form-control">
                    <c:forEach items="${breedList}" var="q">
                        <option value="${q.id}">${q.name}</option>
                    </c:forEach>
                </select>
                &emsp;
                <input type="submit" name="findByBreedID" onclick="document.pressed=this.name"
                       value="<fmt:message key="findByBreed"/>" class="btn-sm btn-primary"/>
                &emsp;&emsp;&emsp;
                <select name="shelter" id="inputShelter" class="form-control">
                    <c:forEach items="${shelterList}" var="i">
                        <option value="${i.id}">${i.name}</option>
                    </c:forEach>
                </select>
                &emsp;
                <input type="submit" name="findByShelterID" onclick="document.pressed=this.name"
                       value="<fmt:message key="findByShelter"/>" class="btn-sm btn-primary">
            </div>
            <div class="form-inline" style="padding-left: 15%">
                <input type="date" pattern="^\\d{4}-\\d{2}-\\d{2}$" value="${sessionScope.birthDate}" name="birthDate">
                &emsp;
                <input type="radio" checked name="birthDateChoice" value="lessthan"><fmt:message
                    key="birthDateBeforeChoice"/>
                &emsp;
                <input type="radio" name="birthDateChoice" value="greaterthan"><fmt:message
                    key="birthDateAfterChoice"/>
                &emsp;
                <input type="submit" name="findByBirthDate" onclick="document.pressed=this.name"
                       value="<fmt:message key="findByBirthDate"/>" class="btn-sm btn-primary">
            </div>
        </form>
        <a class="btn btn-success" style="margin-right: 45%" type="button"
           href="<c:url value="/pets/findpet.html?page=1"/>">
            <fmt:message key="reset"/>
        </a>
    </div>
</div>
<div class="form-row" style="margin-left: 10%">
    <c:forEach items="${petResults}" var="i" varStatus="count">
        <div class="form-group col-md-4">
            <div class="card" style="width: 18rem;">
                <img class="card-img-top"
                     src="data:image/jpeg;base64,${petPictures[count.index]}"/>
                <div class="card-body">
                    <h5 class="card-title">${i.name}</h5>
                </div>
                <ul class="list-group list-group-flush">
                    <li class="list-group-item">
                        <fmt:message key="weight"/>: ${i.weight}
                    </li>
                    <li class="list-group-item">
                        <fmt:message key="dateofbirth"/>:
                        <fmt:formatDate type="date" dateStyle="medium"
                                        value="${i.dateOfBirth.time}"/>
                    </li>
                    <c:if test="${authorizedUser.userRole == 'STAFF'}">
                        <li class="list-group-item">
                            <fmt:message key="petStatus"/>:
                                ${i.status}
                        </li>
                    </c:if>
                </ul>
                <div class="card-body">
                    <div class="form-row justify-content-center">
                        <c:if test="${authorizedUser.userRole != 'ADMINISTRATOR' &&
                        authorizedUser.userRole != 'STAFF'}">
                            <div class="form-inline p-xl-2">
                                <form action="<c:url value="/adoptions/adoptpet.html"/>" method="post">
                                    <input type="hidden" name="petID" value="${i.id}">
                                    <input type="submit" value="<fmt:message key="adopt"/>"
                                           class="btn-sm btn-primary">
                                </form>
                            </div>
                        </c:if>
                        <div class="form-inline p-xl-2">
                            <form action="<c:url value="/pets/moreinfopet.html"/>" method="post">
                                <input type="hidden" name="petID" value="${i.id}">
                                <input type="submit" value="<fmt:message key="moreInfo"/>"
                                       class="btn-sm btn-primary">
                            </form>
                        </div>
                    </div>
                    <c:if test="${authorizedUser.userRole == 'STAFF'}">
                        <div class="form-row justify-content-center">
                            <div class="form-inline p-xl-2">
                                <form action="<c:url value="/pets/staff/editpet.html?petID=${i.id}"/>" method="post">
                                    <input type="submit" value="<fmt:message key="edit"/>"
                                           class="btn-sm btn-primary">
                                </form>
                            </div>
                            <div class="form-inline p-xl-2">
                                <form action="<c:url value="/pets/staff/deletepet.html?petID=${i.id}"/>" method="post">
                                    <input type="submit" value="<fmt:message key="delete"/>"
                                           class="btn-sm btn-primary">
                                </form>
                            </div>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
    </c:forEach>
</div>
<nav aria-label="Page navigation">
    <ul class="pagination">
        <c:if test="${param.page > 1}">
            <li class="page-item">
                <a class="page-link"
                   href="<c:url value="${paginationURL += '?page=' += (param.page - 1)}"/>">
                    Previous</a>
            </li>
        </c:if>
        <c:forEach var="i" begin="1" end="${amountOfPages}">

            <a class="page-link" href="<c:url value="${paginationURL += '?page=' += i}"/>">
                <c:out value="${i}"/></a>

        </c:forEach>
        <c:if test="${param.page < amountOfPages}">
            <li class="page-item">
                <a class="page-link" href="<c:url value="${paginationURL += '?page=' += (param.page + 1)}"/>">
                    Next</a>
            </li>
        </c:if>
    </ul>
</nav>
<script type="text/javascript" src="/js/formscript.js"></script>
<jsp:include page="/jsp/tags/footer.jsp" flush="true"/>
</body>
</html>