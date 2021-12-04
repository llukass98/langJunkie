<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
    <title>Definition search</title>
</head>
<body>
<div class="container">
    <div class="row">
        <h4 class="col col-8 text-center mt-5 mx-4 w-75">Search the definition of the word</h4>
        <div class="col mx-2 mt-5"></div>
        <select id="lang_collection" name="lang" class="col form-select form-select-sm mt-5 px-3" aria-label="Default select example">
            <#list collections.getCollections() as collection>
                <option value=${collection}>${collection}</option>
            </#list>
        </select>
    </div>
</div>
<div class="container-md mt-5">
    <div class="row">
        <input id="searched_word" class="col-8 m-4" name="word" placeholder="Searched word"/>
        <button id="submit_btn" class="col-2 m-4 btn btn-secondary">Search!</button>
    </div>
    <div class="row">
        <div class="card shadow position-relative t-50">
            <div class="card-body">
                <div id="results" class="card-text"></div>
                <div id="spinner" class="spinner-border m-5" role="status" hidden="true">
                    <span class="visually-hidden">Loading...</span>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="container-md mt-5">
    <div class="row">
        <h5 class="col text-center">Text example. Click on words to get their definitions.</h5>
        <span><div class="word">زیان</div><div class="word">پدر</div></span>
    </div>
</div>
</body>
<script type="text/javascript" src="js/main.js"></script>
</html>