<%@ page import="by.mogilev.model.Course" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div id='EvalRemindBlock' style="display: none;">
    <form method="post" action="informationBoard">
        <input type="hidden" class="idC" name="id"/>
        <table id="evalRemindTable">
            <tr>
                <td>Course Lector</td>
                <td class="lect">
                </td>
            </tr>
            <tr>
                <td>Course name</td>
                <td class="cours">
                </td>
            </tr>
            <tr>
                <td>Course Grade</td>
                <td><input type="text" name="grade" class="grade"/>
                    <input type="hidden" name="fieldForSubmit" class="fieldForSubmit"/>

                </td>

            </tr>
            <tr>
                <td>
                    <input type="submit" value="Save"/>
                </td>
                <td>
                    <input type="button" value="Cancel" onclick="hide();"/>

                </td>
            </tr>

        </table>
    </form>
</div>
<br>

<%--<tr>--%>
<%--<form>--%>
<%--<td><br></td>--%>
<%--<td><br></td>--%>
<%--<td><br></td>--%>
<%--<br>--%>
<%--<td><br></td>--%>
<%--<br>--%>
<%--<td><br>--%>
<%--<td><br></td>--%>
<%--<td>Filter</td>--%>
<%--<td>--%>
<%--<select class="btn dropdown-toggle">--%>
<%--<option selected value="All Course">All Course</option>--%>
<%--<option value="Given Courses">Given Courses</option>--%>
<%--<option value="Popular Courses "> Popular Courses</option>--%>
<%--<option value="Evaluation"> Evaluation</option>--%>
<%--</select></td>--%>
<%--</form>--%>
<%--</tr>--%>

<table align="justify" id="tableCourse">
    <form action="informationBoard" method="post">
        <tr>
            <th>
                <input type="submit" onclick="outPdf()" name="pdfOut"
                       value="Click for Output in PDF" class="btn-primary">
            </th>


            <th>

                <input type="submit" onclick="outExcel()" name="excelOut"
                       value="Click for Output in Excel" class="btn-primary">

            </th>

            <td><br></td>
            <td><br></td>
            <td><br>
            <td>
            <td>Course Category</td>
            <td>
                <select name="selectCategory" onchange="filter(this)">

                    <option value="All">All</option>
                    <option value="Project Management">Project Management</option>
                    <option value="Development">Development</option>
                </select>

            </td>
        </tr>


        <tr bgcolor="#b0c4de">
            <th>Lector Name</th>
            <th>Course Name</th>
            <th>Course Category</th>
            <th>Subscribed</th>
            <th>Participated</th>
            <th>Delivered Course</th>
            <th>Evaluation</th>
            <th>Actions</th>
        </tr>

        <c:forEach items="${courseList}" var="course">
        <tr>

            <td class="lector_${course.id}"><c:out value="${course.lector.name}" escapeXml="true"/></td>
            <td>
                <a href="<c:out value="courseDetails/${course.id}" escapeXml="true"/>"
                   title="click to show course detail ">
           <span class="course_${course.id}">
               <c:out value="${course.nameCourse}" escapeXml="true"/></span>
                </a>
            </td>
            <td><c:out value="${course.category}" escapeXml="true"/></td>
            <td><c:out value="${course.subscribers.size()}" escapeXml="true"/></td>
            <td>
                <a href="<c:out value="participantsList/${course.id}" escapeXml="true"/>" title="click to show list">
                    <c:out value="${course.attenders.size()}" escapeXml="true"/>
                </a>
            </td>
            <td><c:out value="${course.courseStatus}" escapeXml="true"/>
            </td>
            <td>
                <c:if test="${course.courseStatus eq 'DELIVERED'}">
                    <a href="#" onclick="show(${course.id})" title="click to put mark">
                        <c:out value="${course.evaluation}" escapeXml="true"/>
                    </a>
                    <input class="grade_${course.id}" type="hidden" value="${course.evaluation}"/>
                </c:if>
            </td>

            <td>
                <div class="btn-group">

                    <c:if test="${ user eq course.lector.username}">
                    <c:if test="${course.courseStatus != 'DELIVERED'}">
                    <input type="submit" name="delete" value="Delete" onclick="del(${course.id})" class="btn"/>
                    </c:if>
                    <c:if test="${course.courseStatus eq 'DELIVERED'}">
                    <a href="<c:url value="/evaluationReminder/${course.id}" context="/project"/>">
                        <input type="button" id="Eval" value="Evaluation Reminder" class="btn"/>
                    </a>
                    </c:if>
                    </c:if>

                    <sec:authorize access="hasAnyRole('DEPARTMENT_MANAGER','KNOWLEDGE_MANAGER')">
                    <a href="<c:url value="/approveCourse/${course.id}" context="/project"/>">
                        <input type="button" id="approve" value="Approve Course" class="btn"/>
                    </a>
                    </sec:authorize>
                    <input type="hidden" name="fieldForSubmit" class="fieldForSubmit"/>
                    <input type="hidden" class="idC" name="id" />

    </form>
    </div>
    </tr>


    </c:forEach>
</table>


    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script type="text/javascript" src="<c:url value="/resources/js/bootstrap.min3.0.2.js"/>"></script>
<script>

    var exc = $("#excMess").val();
    if (exc == '') {
        $('#myModal').modal('hide');
    }
    else
        $('#myModal').modal('show');


    function show(id) {
        $("#EvalRemindBlock").show();
        var lCourse = $(".lector_" + id).html();
        var nCourse = $(".course_" + id).html();
        var gCourse = $(".grade_" + id).val();
        $(".lect").text(lCourse);
        $(".cours").text(nCourse);
        $(".grade").val(gCourse);
        $(".idC").val(id);
        $(".fieldForSubmit").val("EVAL_REM");
    }
    function hide() {
        $("#EvalRemindBlock").hide();
    }

    function outPdf() {
        $(".fieldForSubmit").val("OUT_PDF");
    }

    function outExcel() {
        $(".fieldForSubmit").val("OUT_EXCEL");

    }
    function del(id) {
        $(".idC").val(id);
        $(".fieldForSubmit").val("DEL");
    }

</script>
