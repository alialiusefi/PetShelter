<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <c:url value="/css/bootstrap.min.css" var="cssURL"/>
    <link href="${cssURL}" rel="stylesheet" type="text/css">
    <script src="${jsURL}" type="text/javascript"></script>
    <fmt:setLocale value="${sessionLang}"/>
    <fmt:setBundle basename="by.training.finaltask.resource.localization"/>
    <title><fmt:message key="editProfile"/></title>
</head>
<body>
<!-- MenuItem -->
<jsp:include page="/jsp/tags/menu.jsp" flush="true"/>
<div class="container">
    <form method="post" id="contact_form">
        <!-- Form Name -->
        <legend>
            <center><h2><b><fmt:message key="editProfile"/> </b></h2></center>
        </legend>
        <br>
        <c:if test="${not empty successMessage}">
            <div class="text-center text-info">
                <p>Attention: <fmt:message key="${successMessage}"/></p>
            </div>
        </c:if>
        <c:if test="${not empty message}">
            <div class="text-center text-warning">
                <p>Attention: <fmt:message key="${message}"/></p>
            </div>
        </c:if>
        <!-- Text input-->
        <div class="form-row">
            <div class="form-group col-md-3">
                <label class="col-md-6 control-label"><fmt:message key="firstName"/></label>
                <div class="col-md-6 inputGroupContainer">
                    <div class="input-group">
                        <input name="firstname"
                               class="form-control" value="${oldUserInfo.firstName}" type="text">
                    </div>
                </div>
            </div>
            <div class="form-group col-md-3">
                <label class="col-md-6 control-label"><fmt:message key="lastName"/> </label>
                <div class="col-md-6 inputGroupContainer">
                    <div class="input-group">
                        <input name="lastname"
                               class="form-control" value="${oldUserInfo.lastName}" type="text">
                    </div>
                </div>
            </div>
            <div class="form-group col-md-6">
                <label class="col-md-6 control-label">
                    ${oldUserInfo.firstName}
                    <fmt:message key="dateofbirth"/>:
                    <fmt:formatDate type="date" dateStyle="long" value="${oldUserInfo.dateOfBirth.time}"/></label>
                <label class="col-md-6 control-label"><fmt:message key="dateofbirth"/></label>
                <div class="col-md-6 inputGroupContainer">
                    <div class="input-group">
                        <input name="dateofbirth"
                               class="form-control" placeholder="YYYY-MM-dd" value="<fmt:formatDate pattern="YYYY-MM-dd" type="date"
                               value="${oldUserInfo.dateOfBirth.time}"/>" type="text"
                               max="${java.time.LocalDate.now()}"/>
                    </div>
                </div>
            </div>
        </div>
        <div class="form-row">
            <div class="form-group col-md-3">
                <label class="col-md-6 control-label"><fmt:message key="username"/></label>
                <div class="col-md-6 inputGroupContainer">
                    <div class="input-group">
                        <input name="username"
                               class="form-control" value="${authorizedUser.username}" type="text">
                    </div>
                </div>
            </div>
            <div class="form-group col-md-3">
                <label class="col-md-6 control-label"><fmt:message key="password"/></label>
                <div class="col-md-6 inputGroupContainer">
                    <div class="input-group">
                        <input name="password" class="form-control"
                               type="password">
                    </div>
                </div>
            </div>
            <div class="form-group col-md-6">
                <label class="col-md-6 control-label"><fmt:message key="email"/></label>
                <div class="col-md-6 inputGroupContainer">
                    <div class="input-group">
                        <input name="email"
                               class="form-control" value="${oldUserInfo.email}" type="text">
                    </div>
                </div>
            </div>
        </div>
        <div class="form-row">
            <div class="form-group col-md-6">
                <label class="col-md-6 control-label"><fmt:message key="contactNumber"/></label>
                <div class="col-md-6 inputGroupContainer">
                    <div class="input-group">
                        <input name="contactnumber"
                               class="form-control" value="+${oldUserInfo.phone}" type="text">
                    </div>
                </div>
            </div>

            <!-- Text input-->
            <div class="form-group col-md-6">
                <label class="col-md-6 control-label"><fmt:message key="address"/></label>
                <div class="col-md-6 inputGroupContainer">
                    <div class="input-group">
                        <input name="address"
                               class="form-control" value="${oldUserInfo.address}" type="text">
                    </div>
                </div>
            </div>
        </div>

        <!-- Button -->
        <div class="form-row mx-auto" style="max-width: 55%">
            <div class="form-group col-md-5"><br>
                <button type="submit" class="btn btn-primary"><fmt:message key="editProfile"/></button>
            </div>
            <div class="form-group col-md-2"><br>
                <button type="reset" class="btn btn-primary">
                    <fmt:message key="reset"/></button>
            </div>
        </div>

    </form>
</div>
<jsp:include page="/jsp/tags/footer.jsp" flush="true"/>

</body>
</html>