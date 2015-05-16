

<form class="login-form" action="j_spring_security_check" method="post">

    <div class="row">

        <div class="col-md-7 col-md-offset-5 ">   <img src="resources/img/singin.jpg"/></div>
        </div>

        <div class="row">
        <div class="col-md-8 col-md-offset-5">
    <label for="j_username">Username: </label>

    <input id="j_username" name="j_username" size="20" maxlength="50" type="text" style="width: 190px" />
</div>
            </div>

    <div class="row">
        <div class="col-sm-8  col-md-offset-5">
    <label for="j_password">Password: </label>
    <input id="j_password" name="j_password" size="20" maxlength="50" type="password" required value="1" style="width: 190px" />
<br/>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-3  col-md-offset-5">
    <input type="submit" value="Login" class="btn btn-primary btn-lg btn-block"/>
            </div>
</div>
</form>



