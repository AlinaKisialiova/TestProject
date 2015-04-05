<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: akiseleva
  Date: 02.04.2015
  Time: 9:54
  To change this template use File | Settings | File Templates.
--%>

<div class="row">
    <div class="span12"> <h1>My Seminars</h1></div>

    <table align="justify" >

        <tr>
            <td colspan="3"><a href="informationBoard"> Seminars Information Board </a></td>
            <td colspan="3"><a href="" onclick="subscCours()">Subscribe for the Course</a></td>

        </tr>

<tr><td>
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
</td>
</tr>
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
                    <select name="selectCategory" class="btn dropdown-toggle">
                        <option value="All">All</option>
                        <option  value="Project Management">Project Management</option>
                        <option value="Development">Development</option>
                    </select>
                    <input type="submit" value="ok" class="btn"/>

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
                    <td class="grade_${course.id}">
                        <c:if test="${course.delivered}">
                            <c:out value="${course.evaluation}" escapeXml="true"/></td>
                        </c:if>

                    <td>
                        <c:choose>
                            <c:when test="${attCourseOfUser.contains(course)}">
                                <input type="submit" name="yes" onclick="att('yes')" value="+">
                            </c:when>
                            <c:otherwise>
                                <input type="submit" name="no" onclick="att('no')" value="-">
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

    function show(id) {
        $("#EvalRemindBlock").show();
        var lCourse = $(".lector_" + id).html();
        var nCourse = $(".course_" + id).html();
        var gCourse = $(".grade_"+id).html();


        $(".lect").text(lCourse);
        $(".cours").text(nCourse);
        $(".grade").val(gCourse);
        $(".idC").val(id);
        $(".fieldForSubmit").val("EVAL_REM");
    }
    function hide(){
        $("#EvalRemindBlock").hide();

        function att(yesOrNo) {

            if(yesOrNo=="yes") {
                $(".yes").val("Not to participate");
                $(".no").val("Not to participate");
                $(".fieldForSubmit").val("ADD_IN_ATT");
            }
            else if(yesOrNo=="no")
            { $(".yes").val("Participate");
                $(".no").val("Participate");
                $(".fieldForSubmit").val("REMOTE_FROM_ATT");
            }

        }
    }
    </script>