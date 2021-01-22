<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%-- JSTL core library https://docs.oracle.com/javaee/5/jstl/1.1/docs/tlddocs/ --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- JSTL formatting library https://docs.oracle.com/javaee/5/jstl/1.1/docs/tlddocs/ --%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%-- JSTL functions library https://docs.oracle.com/javaee/5/jstl/1.1/docs/tlddocs/ --%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%--JSP Tag Reference https://docs.oracle.com/cd/E13226_01/workshop/docs81/pdf/files/workshop/JSPTagsReference.pdf  --%>

<!DOCTYPE html>
<html>
<head>

    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">

    <title>Circle Calculator</title>

</head>
<body>
<h1>Circle Calculator</h1>

<jsp:useBean id="circleBean" class="dmit2015.model.Circle">
    <jsp:setProperty name="circleBean" property="radius" param="theRadius"/>
<%--    <jsp:setProperty name="circleBean" property="*" />--%>
</jsp:useBean>

<c:if test="${pageContext.request.method == 'POST'}">
    <p id="area">Area = <strong>${circleBean.area()}</strong></p>
    <p>Circumfernce = <strong>${circleBean.circumference()}</strong></p>
    <p>Diameter = <strong>${circleBean.diameter()}</strong></p>
</c:if>

<form method="post">
    <div>
        <label for="radius">Radius</label>
        <input type="text" id="radius" name="theRadius" value="${circleBean.radius}">
    </div>
    <button id="submit" type="submit">Submit</button>
</form>

<!-- Optional JavaScript: Bootstrap Bundle with Popper -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW"
        crossorigin="anonymous"></script>

</body>

</html>