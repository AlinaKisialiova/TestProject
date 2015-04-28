<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: akiseleva
  Date: 13.04.2015
  Time: 6:42
  To change this template use File | Settings | File Templates.
--%>
<c:set  var="course" value="${courseList}"/>
<form method="post" action="${pageContext.request.contextPath}/approveCourse/${course.id}">
<div class="row">
    <div class="span12">
        Hello, <security:authentication property="principal.username" var="user"/> ${user}!
        <a href="<c:url value="${pageContext.request.contextPath}/j_spring_security_logout"/>"> Logout</a>
    </div>
    <div class="span12">
          <sec:authorize access="hasRole('KNOWLEDGE_MANAGER')">
            I am know that you are a KNOWLEDGE MANAGER!
              <input type="hidden"  id="manager" name="manager" value="KNOWLEDGE_MANAGER"/>
        </sec:authorize>

        <sec:authorize access="hasRole('DEPARTMENT_MANAGER')">
            I am know that you are a DEPARTMENT_MANAGER!
            <input type="hidden" name="manager" id="manager" value="DEPARTMENT_MANAGER"/>
        </sec:authorize>
    </div>
<div class="span12"> <h2>Approve Course</h2>    </div>

    <div class="span12">
        <h4>
            <a href="<c:url value="/informationBoard" context="/project"/>"> Seminars Information Board </a>
        </h4>
    </div>

<div class="span12">
    <div class="span3"><strong> Course Category</strong></div> <div class="span6"><c:out value="${course.category}" escapeXml="true"/></div>
    <div class="span3"><strong> Course Name</strong></div> <div class="span6"> <c:out  value="${course.nameCourse}" escapeXml="true"/></div>

    <div class="span3"><strong> Course Description</strong></div><div class="span6"> <c:out value="${course.description}" escapeXml="true"/></div>
    <div class="span3"><strong> Course Links</strong></div><div class="span6"> <c:out value="${course.links}" escapeXml="true"/></div>
    <div class="span3"><strong> Course Duration</strong></div><div class="span6"> <c:out value="${course.duration}" escapeXml="true"/></div>
</div>

    <div class="span12"><br/></div>

    <div class="span12">
    <div class="span2"><strong>Manager Name</strong></div>  <div class="span2"><strong>Manager Role</strong></div>
    <div class="span4"><strong>Decision</strong></div>  <div class="span2"><strong>Reason</strong></div>
        </div>

    <sec:authentication property="authorities" var="role" scope="page"/>

    <div class="span12">
    <div class="span2">Manager name</div>   <div class="span2"> Departament manager</div>
    <div class="span4"><select  id="approveDM"
            <c:if test="${role != '[DEPARTMENT_MANAGER]'}"> disabled="disabled" </c:if> onchange=>
        <option>approve</option>
        <option> not approve</option>
    </select></div>
    <div class="span2"><textarea  id="reasonDM"  <c:if test="${role != '[DEPARTMENT_MANAGER]'}"> disabled="disabled" </c:if> ></textarea></div>
    </div>


<div class="span12"> <br/></div>
    <div class="span12">
        <div class="span2">Manager name</div>   <div class="span2"> Knowledge manager</div>
        <div class="span4"><select id="approveKM"
                <c:if test="${role != '[KNOWLEDGE_MANAGER]' && course.courseStatus != '[APPROVE_DEPARTMENT_MANAGER]'}"> disabled="disabled" </c:if> >
            <option>approve</option>
            <option> not approve</option>
        </select></div>
        <div class="span2"><textarea  id="reasonKM" <c:if test="${role != '[KNOWLEDGE_MANAGER]' && course.courseStatus != '[APPROVE_DEPARTMENT_MANAGER]'}"> disabled="disabled" </c:if>></textarea></div>
    </div>


</div>
    <div class="control-group">
        <input type="submit" value="Save" name="save" class="btn-primary">
<a href="<c:url value="/informationBoard" context="/project"/>" class="btn">Cancel</a>
    </div>
</div>

</div>

<input type="text" id="approveToServ" name="approveToServ" />
<input type="text" id="reasonToServ" name="reasonToServ" />

</form>
<%--<script language="JavaScript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.5.0/jquery.min.js"></script>--%>
<script src="http://code.jquery.com/jquery-latest.js"></script>

<script>
    $('input[name="save"]').on("click",function(){

        var approveToServ;
        var reasonToServ;
var manager = $("#manager").val();


        if( manager == '[DEPARTMENT_MANAGER]') {
            approveToServ =  $("#approveDM").find("option:selected").val();
            reasonToServ = $("#reasonDM").val();

        }

        if (manager == '[KNOWLEDGE_MANAGER]') {
            approveToServ =  $("#approveKM option:selected").val();
            reasonToServ = $("#reasonKM").val();
        }


        $("#approveToServ").val(approveToServ);
        $("#reasonToServ").val(reasonToServ);


    });



    </script>