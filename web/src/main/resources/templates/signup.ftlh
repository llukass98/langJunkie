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
    <h1>Sign up</h1>
    <div class="container">
        <div class="sign-up-content">
            <form method="POST" class="signup" action="/signup">
                <h2 class="form-title">Create an account</h2>

                <!-- Database constraint validation section start -->
                <#if error??>
                    <div class="error">
                        User with such ${error} is already registered.
                    </div>
                </#if>

                <#if errors?has_content>
                    <#list errors as error>
                        <p class="error">${error.getDefaultMessage()}</p>
                    </#list>
                </#if>
                <!-- Database constraint validation section end-->

                <div class="form-textbox">
                    <label for="name">Full name</label>
                    <input type="text" name="fullname" id="name" />
                </div>

                <div class="form-textbox">
                    <label for="username">Username</label>
                    <input type="text" name="username" id="username" />
                </div>

                <div class="form-textbox">
                    <label for="email">Email</label>
                    <input type="email" name="email" id="email" />
                </div>

                <div class="form-textbox">
                    <label for="pass">Password</label>
                    <input type="password" name="password" id="pass" />
                </div>

                <div class="form-textbox">
                    <input type="submit" name="submit" id="submit" class="submit" value="Create account" />
                </div>

                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form>

            <p class="loginhere">
                Already have an account ?<a href="/signin" class="loginhere-link"> Log in</a>
            </p>
        </div>
    </div>
</div>
</body>
</html>