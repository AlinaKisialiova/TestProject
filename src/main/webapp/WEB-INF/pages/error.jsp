<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>


<%--
  Created by IntelliJ IDEA.
  User: akiseleva
  Date: 18.05.2015
  Time: 9:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="row">
    <div class="col-md-8 col-md-offset-4">
<h2>${message}</h2>
        </div>
    <div class="col-md-8 col-md-offset-5">
        <img src="<c:url value="/resources/img/404.jpg"/>"/>
    </div>
    <div class="col-md-4 col-md-offset-4">
        <input type="button" value="Back" onclick="history.back();" class="btn btn-block">
        </div>


    </div>
