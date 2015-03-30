<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<h1>Seminars Information Board</h1>
Hello, <security:authentication property="principal.username" var="user"/> ${user}!
<a href=j_spring_security_logout> Logout</a>
</br>

<sec:authorize access="hasRole('ROLE_LECTOR')">
    I am know that you are a lector!
</sec:authorize>


<br/>
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


<table align="justify" >

    <tr >
        <td>My seminars</td>
        <td><a href=registrationCourse>Register Course</a></td>
        <td><br></td>
        <td><br></td>
</tr>
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



        <tr>
            <td><br></td>
            <td><br></td>
            <td><br></td>
            <td><br></td>
            <td><br>
            <td>
            <td>Course Category</td>
            <td>
                <form action="informationBoard" method="post">
                <select name="selectCategory" class="btn dropdown-toggle">
                    <option value="All">All</option>
                    <option  value="Project Management">Project Management</option>
                    <option value="Development">Development</option>
                </select>
                <input type="submit" value="ok" class="btn"/>
                </form>
            </td>
        </tr>

    </form>


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
            <td >
                <a href="<c:out value="courseDetails/${course.id}" escapeXml="true"/>">
           <span class="course_${course.id}">
               <c:out  value="${course.nameCourse}" escapeXml="true"/></span>
                </a>
            </td>
            <td><c:out value="${course.category}" escapeXml="true"/></td>
            <td><c:out value="${course.subscribers.size()}" escapeXml="true"/></td>
            <td>
                <a href="<c:out value="participantsList/${course.id}" escapeXml="true"/>">
                <c:out value="${course.attenders.size()}" escapeXml="true"/>
                </a>
            </td>
            <td><c:out value="${course.delivered}" escapeXml="true"/></td>
            <td class="grade_${course.id}"><c:out value="${course.evaluation}" escapeXml="true"/></td>
            <td>
                <div class="btn-group">
                <form action="informationBoard" method="post">
                    <c:if test="${ user eq course.lector.username}">
                <c:if test="${course.delivered}">
                    <input type="submit" value="Delete" onclick="del(${course.id})" class="btn"/>
                </c:if>
                <input type="button" id="Eval" value="Evaluation Reminder" onclick="show(${course.id})" class="btn"/>
                </c:if>
                    <input type="hidden" name="fieldForSubmit" class="fieldForSubmit"/>
                    <input type="hidden" class="idC" name="id"/>
                </form>
                </div>
        </tr>


    </c:forEach>
</table>

<script src="<c:url value="/resources/css/bootstrap-2.2.2.min.js"/>"></script>
<script  src="<c:url value="/resources/css/jquery-1.8.1.min.js"/>"></script>

<script language="JavaScript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.5.0/jquery.min.js"></script>
<script>

    function show(id) {
        $("#EvalRemindBlock").show();
        var lCourse = $(".lector_" + id).html();
        var nCourse = $(".course_" + id).html();
        var gCourse = $(".grade_"+id).html();
        $(".lect").text(lCourse);
        $(".cours").text(nCourse);
        $(".grade").val(gCourse);
        $(".idC").val(id);
        $(".fieldForSubmit").val("evalRem");
    }
    function hide(){
        $("#EvalRemindBlock").hide();
    }

    function del (id) {
        $(".idC").val(id);
        $(".fieldForSubmit").val("del");

    }



</script>
