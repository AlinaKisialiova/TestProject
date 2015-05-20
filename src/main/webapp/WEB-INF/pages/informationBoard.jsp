<%@ page import="by.mogilev.model.CourseStatus" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:set var="NOT_APPROVE" value="<%=CourseStatus.NOT_APPROVE%>"/>
<c:set var="APPROVE_DEPARTMENT_MANAGER" value="<%=CourseStatus.APPROVE_DEPARTMENT_MANAGER%>"/>
<c:set var="APPROVE_KNOWLEDGE_MANAGER" value="<%=CourseStatus.APPROVE_KNOWLEDGE_MANAGER%>"/>
<c:set var="DELIVERED" value="<%=CourseStatus.DELIVERED%>"/>
<sec:authentication property="authorities" var="role" scope="page"/>
<security:authentication property="principal.username" var="user"/>

<div id='EvalRemindBlock' style="display: none;" class="col-md-4 col-md-offset-4">
    <form method="post" action="informationBoard">
        <input type="hidden" class="idC" name="id"/>
        <table id="evalRemindTable" class="table">
            <thead>
            <tr class="danger">
                <th colspan="2">Please, put mark for this course</th>
            </thead>
            <tbody>
            <tr class="active">
                <td><strong>Course Lector:</strong></td>
                <td class="lect">
                </td>
            </tr>
            <tr class="active">
                <td><strong>Course name:</strong></td>
                <td class="cours">
                </td>
            </tr>
            <tr class="danger">
                <td><strong>Course Grade:</strong></td>
                <td><input type="text" name="grade" class="grade"/>
                    <span class="errorText" id="errGrade"></span>
                    <input type="hidden" name="fieldForSubmit" class="fieldForSubmit"/>
                </td>

            </tr>
            <tr class="active">
                <td align="right">
                    <input type="submit" value="Save" onclick="return val_ev();" class="btn btn-primary"/>
                </td>
                <td>
                    <input type="button" value="Cancel" onclick="hide('#EvalRemindBlock');" class="btn"/>

                </td>
            </tr>
            </tbody>
        </table>
    </form>
</div>
<br>


<table align="justify" id="tableCourse">
    <form action="informationBoard" method="post">
        <tr>
            <th>
                <input type="submit" onclick="setAction('OUT_PDF')" name="pdfOut"
                       value="Click for Output in PDF" class="btn btn-primary">
            </th>

            <th>

                <input type="submit" onclick="setAction('OUT_EXCEL')" name="excelOut"
                       value="Click for Output in Excel" class="btn btn-primary">

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
            <th>Course Status</th>
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

            <td>
                <c:choose>
                    <c:when test="${course.courseStatus eq NOT_APPROVE}">
                        <c:out value="Not Approve" escapeXml="true"/>
                    </c:when>

                    <c:when test="${course.courseStatus == APPROVE_DEPARTMENT_MANAGER}">
                        <c:out
                                value="Approve department manager" escapeXml="true"/>
                    </c:when>
                    <c:when test="${course.courseStatus == APPROVE_KNOWLEDGE_MANAGER}">
                        <c:out
                                value="Approve knowledge manager" escapeXml="true"/></c:when>
                    <c:when test="${course.courseStatus == DELIVERED}">
                        <c:out value="Delivered"   escapeXml="true"/>
                    </c:when>
                </c:choose>
        </td>
            <td>
                <c:if test="${course.courseStatus eq 'DELIVERED'}">
                    <a href="#" onclick="show(${course.id}, '#EvalRemindBlock')" title="click to put mark">
                        <c:out value="${course.evaluation}" escapeXml="true"/>
                    </a>
                    <input class="grade_${course.id}" type="hidden" value="${course.evaluation}"/>
                </c:if>
            </td>

            <td>
                                   <c:if test="${user eq course.lector.username and course.courseStatus eq DELIVERED}">

                        <a href="<c:url value="/evaluationReminder/${course.id}" context="/project"/>">
                            <input type="button" id="Eval" value="Evaluation Reminder" class="btn btn-success"/>
                        </a>
                    </c:if>


                    <sec:authorize access="hasAnyRole('DEPARTMENT_MANAGER','KNOWLEDGE_MANAGER')">
                        <c:if test="${(course.courseStatus eq NOT_APPROVE and role eq '[DEPARTMENT_MANAGER]') or
                        (course.courseStatus eq APPROVE_DEPARTMENT_MANAGER and role eq '[KNOWLEDGE_MANAGER]')}">
                    <a href="<c:url value="/approveCourse/${course.id}" context="/project"/>">
                        <input type="button" id="approve" value="Approve Course" class="btn"/>
                    </a>
                       </c:if>
                    </sec:authorize>
                <input type="hidden" name="fieldForSubmit" class="fieldForSubmit"/>
                <input type="hidden" class="idC" name="id" />
</td>
    </tr>
    </c:forEach>
    </form>
</table>

