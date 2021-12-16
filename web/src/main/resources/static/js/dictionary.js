"use strict";

const submitBtn = document.querySelector("#submit_btn");
const definitionsCard = document.querySelector("#definitions_card_dict");
const definitionCloseBtn = document.querySelector("#definition_close_btn");

// Load definitions
const fetchDefinitions = () => {
    const word = document.querySelector("#searched_word").value;
    const lang = document.querySelector("#lang_collection").value;
    const results = document.querySelector("#results");
    const spinner = document.querySelector("#spinner");

    spinner.removeAttribute("hidden");
    showDefinitionsCard();
    results.innerText = "";
    submitBtn.disabled = true;

    const resp = fetch("/api/v1.0b/definitions?lang=" + lang + "&word=" + word)
        .then(response => response.json())
        .then(data => {
            spinner.setAttribute("hidden", "true");
            submitBtn.disabled = false;
            printResults(data);
        })
        .catch(err => results.innerText = err.message);
}

// Print fetched definitions
const printResults = data => {
    if (data.status >= 300 || data.status < 200) { results.innerText = data.message; }
    const resultSet = data.definitions.map(def => def.results).flat();
    const dictNames = data.definitions.map(def => def.name);
    const dictLink = data.definitions.map(def => def.link);

    results.innerHTML = "<p><h5>Definitions found: </p></h5>";
    for (const key of new Set(resultSet).keys()) {
        results.innerHTML += "<span><strong>" + key + "</strong></span> :: ";
    }

    results.innerHTML += "<br><br><p><i>Dictionaries used: </i></p>";
    for (let i = 0; i < dictNames.length; i++) {
        results.innerHTML += "<i><a href=\"" +
            dictLink[i] + "\"class='btn btn-link' target='_blank'>"+ dictNames[i] + "</a></i>     ";
    }
}

const hideDefinitionsCard = () => {
    definitionsCard.setAttribute("hidden", "true");
}

const showDefinitionsCard = () => {
    definitionsCard.removeAttribute("hidden");
}

submitBtn.addEventListener("click", fetchDefinitions);
definitionCloseBtn.addEventListener("click", hideDefinitionsCard);
