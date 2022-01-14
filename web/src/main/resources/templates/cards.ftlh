<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link href="css/main.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
    <title>Langjunkie - manage your flash cards</title>
</head>
<body>
<div class="container ">
    <div class="row">
        <!-- Navigation -->
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark sticky-top">
            <div class="container-fluid">
                <a class="navbar-brand" href="#">${user.fullName}</a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNav">
                    <ul class="navbar-nav">
                        <li class="nav-item">
                            <a class="nav-link" aria-current="page" href="/">Home</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link active" href="/cards">Flash cards</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="/dictionary">Dictionary</a>
                        </li>
                    </ul>
                    <form class="navbar-text" method="post" action="/logout">
                        <input id="logout" type="submit" class="btn btn-danger" value="Logout" />
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                    </form>

                    <select id="lang_collection" name="lang" class="col form-select form-select-sm px-1" aria-label="Default select example">
                        <#list collections.getCollections() as collection>
                            <option value=${collection}>${collection?lower_case}</option>
                        </#list>
                    </select>
                </div>
            </div>
        </nav>
        <!-- End of navigation-->

        <h4 class="col text-center mt-5 mx-5 w-75">You have ${cards.totalElements}
            <#if cards.totalElements == 1>card:<#else>cards:</#if></h4>
    </div>
</div>

<div class="container-md mt-5">
    <!-- Add flash card form ================================-->
    <form class="card shadow" id="add_form" method="POST" action="/card/add" enctype="multipart/form-data" hidden>
        <label for="card_word" class="h6">word</label>
        <div class="mb-3">
            <input id="word" type="text" name="word" class="form-control" placeholder="Enter the new word">
        </div>

        <label for="picture" class="h6">illustration</label>
        <div class="mb-3">
            <input id="picture" type="file" accept=".jpg, .png" name="picture" class="form-control">
        </div>

        <label for="dict_collection" class="h6">dictionary collection</label>
        <select id="dict_collection" name="language" class="mb-3" aria-label="Default select example">
            <#list collections.getCollections() as collection>
                <option value=${collection}>${collection?lower_case}</option>
            </#list>
        </select>

        <label for="front-side" class="h6">front side</label>
        <div class="mb-3">
            <textarea id="front-side" class="form-control" name="frontSide" placeholder="Enter the text of front side" rows="3"></textarea>
        </div>

        <label for="back-side" class="h6">back side</label>
        <div class="mb-3">
            <textarea id="back-side" class="form-control" name="backSide" placeholder="Enter the text of back side" rows="3"></textarea>
        </div>

        <input type="hidden" name="username" value="${user.username}" />
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

        <input class="col" type="submit" value="Create" />
        <div id="add_close_btn" class="col btn-sm btn-danger close_btn">Close</div>
    </form>
    <!--   -->

    <!-- Edit flash card form ================================-->
    <form class="card shadow" id="edit_form" method="POST" action="" enctype="multipart/form-data" hidden>
        <label for="edit_card_word" class="h6">word</label>
        <div class="mb-3">
            <input id="edit_card_word" type="text" name="word" class="form-control" placeholder="Enter the new word">
        </div>

        <label for="edit_picture" class="h6">illustration</label>
        <div class="mb-3">
            <input id="edit_picture" type="file" accept=".jpg, .jpeg, .png" name="picture" class="form-control">
        </div>

        <label for="edit_dict_collection" class="h6">dictionary collection</label>
        <select id="edit_dict_collection" name="language" class="mb-3" aria-label="Default select example">
            <#list collections.getCollections() as collection>
                <option value=${collection}>${collection?lower_case}</option>
            </#list>
        </select>

        <label for="edit_card_front" class="h6">front side</label>
        <div class="mb-3">
            <textarea id="edit_card_front" class="form-control" name="frontSide" placeholder="Enter the text of front side" rows="3"></textarea>
        </div>

        <label for="edit_card_back" class="h6">back side</label>
        <div class="mb-3">
            <textarea id="edit_card_back" class="form-control" name="backSide" placeholder="Enter the text of back side" rows="3"></textarea>
        </div>

        <input id="update_card_id" type="hidden" name="id" value="" />
        <input id="csrf" type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

        <input class="col" type="submit" value="Edit" />
        <div id="edit_close_btn" class="col btn-sm btn-danger close_btn">Close</div>
    </form>
    <!--   -->

    <button id="add_card_btn" class="btn btn-secondary">Create flash card</button>

    <!-- Flash cards ========================================-->
    <div class="col align-items-center">
        <#list cards.content as card>
            <div class="card mt-2 row" style="width: 18rem;">
                <h5 class="flash_card text-center mt-1">${card.word.value}</h5>
                <div class="card-body shadow" hidden>
                    <h5 id="word${card.id}" class="card-title">${card.word.value}</h5>

                    <img id="card_picture" src="#"  alt="" hidden/>

                    <h6 id="language${card.id}" hidden>${card.language}</h6>
                    <p id="front_side${card.id}" class="card-text" <#if card.language == "FAEN">style="direction:rtl"</#if>>
                        <#if card.frontSide??>${card.frontSide}</#if>
                    </p>
                    <hr class="card-text" />

                    <p id="back_side${card.id}" class="card-text back_side" hidden>${card.backSide}</p>

                    <div class="buttons">
                        <button class="btn btn-success back_side_btn">Back side</button>
                        <button class="btn btn-success edit_btn">Edit</button>
                        <button class="btn btn-secondary close_btn">Close</button>
                        <button id="delete_btn" class="btn btn-danger delete_btn">Delete</button>

                        <div id="card_id" id="id" hidden>${card.id}</div>
                    </div>
                </div>
            </div>
        </#list>

        <!-- Pagination -->
        <#if (cards.totalPages > 1)>
            <nav class="col mt-5" aria-label="...">
                <ul class="pagination pagination-sm">
                    <#assign page = cards.totalPages>
                    <#list 0..<page as i>
                        <#if cards.number == i>
                            <li class="page-item active" aria-current="page">
                                <span class="page-link">${i + 1}</span>
                            </li>
                        <#else>
                            <li class="page-item"><a class="page-link" href="/cards?page=${i}">${i + 1}</a></li>
                        </#if>
                    </#list>
                </ul>
            </nav>
        </#if>
    </div>
</div>
</body>
<script type="text/javascript" src="js/cards.js"></script>
</html>
