<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="myModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">

            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">${modalTitle}</h4>
            </div>
            <div class="modal-body">
                <center><h4>${modalMessage}</h4>
                    <img src="<c:url value="/resources/img/ooops.png"/>">
                </center>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal" onclick="window.location.href=window.location.href">Close</button>
            </div>

        </div>
    </div>
</div>

<input type="hidden" value="${modalMessage}" id="mMess"/>


<script type="text/javascript" src="<c:url value="/resources/js/bootstrap.min3.0.2.js"/>"></script>
<script>
var exc = $("#mMess").val();
if (exc == '') {
$('#myModal').modal('hide');
}
else
$('#myModal').modal('show');
</script>