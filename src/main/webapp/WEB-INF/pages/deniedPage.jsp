<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: Администратор
  Date: 24.03.2015
  Time: 10:11
  To change this template use File | Settings | File Templates.
--%>
<div class="row">
<div class="span12 offset1">
   <h1>Access to the specified resource has been forbidden.</h1>
<div class="span8 offset4">
<img src="<c:url value="/resources/denied.jpg"/>"/>
</div>
 </div>
    <div class="span12">
        <a href="<c:url value="/informationBoard" context="/project"/>">Back</a>
    </div>
</div>