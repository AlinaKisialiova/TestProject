<center>
<h1>Sign In</h1>

<h1>${message}</h1>

<form class="login-form" action="j_spring_security_check" method="post">
    <label for="j_username">Username: </label>
    <input id="j_username" name="j_username" size="20" maxlength="50" type="text" required autofocus value="elvis"/>

    <label for="j_password">Password: </label>
    <input id="j_password" name="j_password" size="20" maxlength="50" type="password" required value="1" />

    <input type="submit" value="Login" />
</br>

    <input id="remember_me"
               name="_spring_security_remember_me"
               type="checkbox"/>
        <label for="remember_me"
               class="inline">Remember me</label>
</form>

</center>