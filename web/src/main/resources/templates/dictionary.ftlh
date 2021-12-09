<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link href="css/main.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
    <title>Langjunkie - dictionary</title>
</head>
<body>
<div class="container ">
    <div class="row">
        <!-- Navigation -->
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark sticky-top">
            <div class="container-fluid">
                <a class="navbar-brand" href="#">${user.getFullname()}</a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNav">
                    <ul class="navbar-nav">
                        <li class="nav-item">
                            <a class="nav-link" aria-current="page" href="/">Home</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="/cards">Flash cards</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link active" href="/dictionary">Dictionary</a>
                        </li>
                    </ul>
                    <form class="navbar-text" method="post" action="/logout">
                        <input id="logout" type="submit" class="btn btn-danger" value="Logout" />
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                    </form>

                    <select id="lang_collection" name="lang" class="col form-select form-select-sm px-1" aria-label="">
                        <#list collections.getCollections() as collection>
                            <option value=${collection}>${collection}</option>
                        </#list>
                    </select>
                </div>
            </div>
        </nav>
        <!-- End of navigation-->

        <h4 class="col text-center mt-5 mx-4 w-75">Search the definition of the word</h4>
    </div>
</div>

<div class="container-md mt-5">
    <div class="row">
        <input id="searched_word" class="col-8 m-4" name="word" placeholder="Search the word"/>
        <button id="submit_btn" class="col-2 m-4 btn btn-secondary">Search!</button>
    </div>

    <div class="row position-fixed t-0 w-50" id="definitions_card_dict" hidden>
        <div class="card shadow position-relative t-50">
            <div class="card-body">
                <button id="definition_close_btn" type="button" class="btn-close" aria-label="Close"></button>
                <div id="results" class="card-text"></div>
                <div id="spinner" class="spinner-border m-5" role="status" hidden="true">
                    <span class="visually-hidden">Loading...</span>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script type="text/javascript" src="js/dictionary.js"></script>
</html>