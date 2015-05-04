<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: akiseleva
  Date: 02.04.2015
  Time: 9:54
  To change this template use File | Settings | File Templates.
--%>

<div class="row">

    <div class="span12"><h1>My Seminars</h1></div>
    <div class="span12">
    Hello, <security:authentication property="principal.username" var="user"/> ${user}!
    <a href=j_spring_security_logout> Logout</a>
    </div>
<div class="span12">
    <sec:authorize access="hasRole('ROLE_LECTOR')">
        I am know that you are a lector!
    </sec:authorize>

    <sec:authorize access="hasRole('ROLE_USER')">
        I am know that you are a user!
    </sec:authorize>
</div>

    <div class="span5"><a href="<c:url value="/informationBoard" context="/project"/>"> Seminars Information Board </a>
    </div>
    <div class="span5"><a href="#" onclick="show(null,'#SubscOnCourse')">Subscribe for the Course</a></div>


    <div id='EvalRemindBlock' style="display: none;" class="table">
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
                    <td>
                        <input type="text" name="grade" class="grade"/>
                        <input type="hidden" name="fieldForSubmit" class="fieldForSubmit"/>

                    </td>

                </tr>
                <tr>
                    <td>
                        <input type="submit" value="Save"/>
                    </td>
                    <td>
                        <input type="button" value="Cancel" onclick="hide('#EvalRemindBlock');"/>

                    </td>
                </tr>
            </table>
        </form>
    </div>


    <div id='SubscOnCourse' style="display: none;" class="table">
        <form action="mySeminars" method="post">
            <input type="hidden" class="idC" name="id"/>
            <table id="subscCourse">
                <tr>
                    <td><b> Select Course Category </b></td>
                    <td>
                        <select name="selectCategory">
                            <option value="All">All</option>
                            <option value="Project Management">Project Management</option>
                            <option value="Development">Development</option>
                        </select>
                    </td>
                    <td><input type="submit" value="Ok"/></td>
                </tr>
                <tr>
                    <td><b>Select Course Name</b></td>
                    <td>
                        <select name="selectCourse" id="selectCourse">
                            <c:forEach var="courses" items="${nameCourses}">
                                <option value="${courses.id}"> ${courses.nameCourse}</option>
                            </c:forEach>
                        </select>

                <tr>
                    <td>
                        <input type="hidden" name="fieldForSubmit" class="fieldForSubmit"/>
                        <input type="submit" value="Subscribe/Delete" onclick="setAction('SUBSCRIBE')"
                               class="btn-primary"/>

                    </td>
                    <td>
                        <input type="button" value="Cancel" onclick="hide('#SubscOnCourse');" class="btn"/>

                    </td>
                </tr>
            </table>
        </form>
    </div>
    <div class="span12"><h2> ${subscribeMessage} </h2></div>



    <table align="justify" id="tableCourse">
        <form action="mySeminars" method="post">
            <tr>
                <td><br></td>
                <td><br></td>
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
                    <a href="<c:out value="courseDetails/${course.id}" escapeXml="true"/>">
           <span class="course_${course.id}">
               <c:out value="${course.nameCourse}" escapeXml="true"/></span>
                    </a>
                </td>
                <td><c:out value="${course.category}" escapeXml="true"/></td>
                <td><c:out value="${course.subscribers.size()}" escapeXml="true"/></td>
                <td>
                    <a href="<c:out value="participantsList/${course.id}" escapeXml="true"/>">
                        <c:out value="${course.attenders.size()}" escapeXml="true"/>
                    </a>
                </td>
                <td><c:out value="${course.courseStatus}" escapeXml="true"/></td>

                <td class="grade_${course.id}">
                    <c:if test="${course.courseStatus eq 'DELIVERED'}">
                        <a href="#" onclick="show(${course.id}, '#EvalRemindBlock')">
                    <c:out value="${course.evaluation}" escapeXml="true"/>
                        </a>
                    <input type="hidden" value="${course.evaluation}" class="hiddenEval_${course.id}">
                </td>

                </c:if>

                <td>

                    <c:choose>
                        <c:when test="${(attCourseOfUser.contains(course))}">
                            <a href="<c:url value="/attendeeList/${course.id}" context="/project"/>" class="btn-danger">
                                Exclude from Attenders List </a>
                        </c:when>

                        <c:otherwise>
                            <a href="<c:url value="/attendeeList/${course.id}" context="/project"/>" class="btn-primary">
                                Include Into Attenders List </a>
                        </c:otherwise>
                    </c:choose>

                </td>
                <input type="hidden" name="fieldForSubmit" class="fieldForSubmit"/>


                <input type="hidden" class="idC" name="id"/>

            </tr>

            </c:forEach>
    </table>

</div>


<script language="JavaScript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.5.0/jquery.min.js"></script>
<script>


    function show(id, element) {
        if (element == "#EvalRemindBlock") {
            $(element).show();
            var lCourse = $(".lector_" + id).html();
            var nCourse = $(".course_" + id).html();
            var gCourse = $(".hiddenEval_" + id).val();

            $(".lect").text(lCourse);
            $(".cours").text(nCourse);
            $(".grade").val(gCourse);
            $(".idC").val(id);
            $(".fieldForSubmit").val("EVAL_REM");
        }
        if (element == "#SubscOnCourse")
            $(element).show();
    }
    function hide(element) {
        if (element == "#EvalRemindBlock")
            $(element).hide();

        if (element == "#SubscOnCourse")
            $(element).hide();
    }

    function setAction(action) {
        $(".fieldForSubmit").val(action);
        $(".idC").val(id);

    }
    function att(yesOrNo, id) {
        $(".idC").val(id);
        if (yesOrNo == "yes") {
            $(".yes").val("Not to participate");
            $(".no").val("Not to participate");
            $(".fieldForSubmit").val("ADD_IN_ATT");
        }
        else if (yesOrNo == "no") {
            $(".yes").val("Participate");
            $(".no").val("Participate");
            $(".fieldForSubmit").val("REMOTE_FROM_ATT");
        }
    }


</script>