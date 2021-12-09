<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <!-- Font Icon -->
    <link rel="stylesheet" href="fonts/material-icon/css/material-design-iconic-font.min.css">

    <!-- Main css -->
    <link rel="stylesheet" href="css/sign_up_and_sign_in_style.css">
    <title>Langjunkie - Create account</title>
</head>
<body>
<div class="main">
    <h1>Sign in</h1>
    <div class="container">
        <div class="sign-up-content">
            <form method="POST" class="signin" action="/signin">
                <h2 class="form-title">Authorise</h2>

                <#if signin_failure?has_content>
                    <div class="error">Unable to authorise - bad credentials. Try again.</div>
                </#if>

                <div class="form-textbox">
                    <label for="username">Username</label>
                    <input type="text" name="username" id="name" />
                </div>

                <div class="form-textbox">
                    <label for="pass">Password</label>
                    <input type="password" name="password" id="pass" class="signin_pass" />
                </div>

                <div class="form-textbox">
                    <input type="submit" name="submit" id="submit" class="submit" value="Sign in" />
                </div>

                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form>

            <p class="loginhere">
                Don't have an account ?<a href="/signup" class="loginhere-link"> Sign up</a>
            </p>
        </div>
    </div>
</div>
</body>
</html>