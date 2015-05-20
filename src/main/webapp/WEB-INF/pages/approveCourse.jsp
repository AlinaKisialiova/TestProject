<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<c:set  var="course" value="${courseList}"/>
<sec:authentication property="authorities" var="role" scope="page"/>
<form method="post" action="${pageContext.request.contextPath}/approveCourse/${course.id}">
<div class="row">

    <div class="col-md-8 col-md-offset-4">
    <div class="span3"><strong> Course Category:</strong> <c:out value="${course.category}" escapeXml="true"/></div>
        <div class="span3"><strong> Course Name:</strong> <c:out  value="${course.nameCourse}" escapeXml="true"/></div>

    <div class="span3"><strong> Course Description:</strong> <c:out value="${course.description}" escapeXml="true"/></div>
    <div class="span3"><strong> Course Links:</strong> <c:out value="${course.links}" escapeXml="true"/></div>
    <div class="span3"><strong> Course Duration:</strong> <c:out value="${course.duration}" escapeXml="true"/></div>
</div>
</div>
   <div class="row"> <hr> </div>
<div class="row">
    <div class="col-md-8 col-md-offset-4">
    <div class="col-md-1"><strong>Manager name</strong></div> <div class="col-md-2"><strong> Manager Role</strong></div>
    <div class="col-md-5"><strong>Decision</strong></div>
        </div>
</div>

    <div class="col-md-8 col-md-offset-4">

    <div class="col-md-1">
        <c:if test="${role == '[DEPARTMENT_MANAGER]'}"><c:out value="${nameUser}" escapeXml="true"/> </c:if></div>
        <div class="col-md-2"> Departament manager</div>
    <div class="col-md-5">
        <select  id="approveDM" name="approveDM"
            <c:if test="${role != '[DEPARTMENT_MANAGER]'}"> disabled="disabled" </c:if>>
        <option>approve</option>
        <option> not approve</option>
    </select>
    </div>
        </div>

    <div class="col-md-8 col-md-offset-6">
        <div class="col-md-4">
            <strong>Reason</strong></div>
        <br>
    <div class="col-md-4">
        <textarea  id="reasonDM" name="reasonDM"
                <c:if test="${role != '[DEPARTMENT_MANAGER]'}"> disabled="disabled" </c:if> >
                 <c:out value="${reasonDM}" escapeXml="true"/>
        </textarea>
    </div>

        <hr>
    </div>


    <div class="col-md-8 col-md-offset-4">
        <div class="row"> <hr></div>
        <div class="col-md-1"> <c:if test="${role == '[KNOWLEDGE_MANAGER]'}"><c:out value="${nameUser}" escapeXml="true"/> </c:if></div>
        <div class="col-md-2"> Knowledge manager</div>
        <div class="col-md-4"><select id="approveKM" name="approveKM"
                <c:if test="${role != '[KNOWLEDGE_MANAGER]' && course.courseStatus != 'APPROVE_DEPARTMENT_MANAGER'}">
                    disabled="disabled" </c:if> >
            <option>approve</option>
            <option> not approve</option>
        </select></div>
    </div>
    <div class="col-md-8 col-md-offset-6">
        <div class="col-md-4">
            <strong>Reason</strong>
        </div>
        <br>
        <div class="col-md-4">
            <textarea  id="reasonKM" name="reasonKM"
                    <c:if test="${role != '[KNOWLEDGE_MANAGER]' && course.courseStatus != 'APPROVE_DEPARTMENT_MANAGER'}">
                        disabled="disabled" </c:if>>
                        <c:out value="${reasonKM}" escapeXml="true"/>
            </textarea>
        </div>
    </div>
</div>
    <div class="row">
    <br>
        </div>
    <div class="col-md-8 col-md-offset-5">
        <br>
        <input type="submit" value="Save" name="save" class="btn btn-large btn-primary">
<a href="<c:url value="/informationBoard" context="/project"/>" class="btn">Cancel</a>

    </div>
</div>

</div>

<input type="hidden" id="approveToServ" name="approveToServ" />
<input type="hidden" id="reasonToServ" name="reasonToServ" />

    <sec:authorize access="hasRole('KNOWLEDGE_MANAGER')">
        <input type="hidden" id="manager" name="manager" value="KNOWLEDGE_MANAGER"/>
    </sec:authorize>

    <sec:authorize access="hasRole('DEPARTMENT_MANAGER')">
        <input type="hidden" name="manager" id="manager" value="DEPARTMENT_MANAGER"/>
    </sec:authorize>


</form>

<script>
    $('input[name="save"]').on("click",function(){
        var approveToServ;
        var reasonToServ;
var manager = $("#manager").val();

        if( manager == 'DEPARTMENT_MANAGER') {
            approveToServ =  $("#approveDM").find("option:selected").val();
            reasonToServ = $("#reasonDM").val();

        }

        if (manager == 'KNOWLEDGE_MANAGER') {
            approveToServ =  $("#approveKM").find("option:selected").val();
            reasonToServ = $("#reasonKM").val();

        }

        $("#approveToServ").val(approveToServ);
        $("#reasonToServ").val(reasonToServ);
    });

    </script>