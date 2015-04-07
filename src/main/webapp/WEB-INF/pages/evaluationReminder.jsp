<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: akiseleva
  Date: 07.04.2015
  Time: 7:52
  To change this template use File | Settings | File Templates.
--%>

<div class="row">
    <div class="span12">  <h1>Evaluation Reminder</h1></div>

    <div class="span12">
       <h4>
        <a href="<c:url value="/informationBoard" context="/project"/>"> Seminars Information Board </a>
       </h4>
        </div>
    <div class="span12"><br/></div>
    <div class="span12">
        <div class="control-group">
            <div class="control-label"> <b>Course Category: </b>
                ${checkCourse.category}</div>
        </div>
    </div>

    <div class="span12">
        <div class="control-group">
            <div class="control-label" ><b>Course Name: </b>
                <a href="<c:url value="/courseDetails/${checkCourse.id}" context="/project/"/>" >
                ${checkCourse.nameCourse}
                </a>
            </div>

        </div>
    </div>


    <div class="span8">
        <div class="control-group">
            <div class="control-label html-editor-bold"><b>Participants</b></div>
            <c:forEach items="${checkCourse.attenders}" var="attend">
                <div class="controls text">
                           <c:out value="${attend.name}" escapeXml="true"/>
                    <span class="controls text"><c:out value="${attend.email}" escapeXml="true"/></span>

                </div>
            </c:forEach>
        </div>
    </div>

<div class="span12">
    <div class="btn-group">
        <form action="<c:url value="/evaluationReminder/${checkCourse.id}" context="/project/"/>" method="post">
        <input type="submit" value="Start" class="btn-primary"/>
        <input type="submit" value="Reset" class="btn-info">
        </form>
        </div>
</div>
<div class="span12">
   <h2> ${startMessage}</h2>
    </div>
    <div class="span12">
        <a href="<c:url value="/informationBoard" context="/project"/>">Back</a>
    </div>

</div>
</div>
