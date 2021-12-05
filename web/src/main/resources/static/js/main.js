"use strict";

const langCollectionField = document.querySelector("#lang_collection");
const submitBtn = document.querySelector("#submit_btn");
const words = document.getElementsByClassName("word");

const fetchCollections = () => {
    const resp = fetch("/api/v1.0b/collections")
        .then(response => response.json())
        .then(data => langCollectionField.setAttribute("value", data.collections))
        .catch(err => console.log(err));
}

const fetchDefinitions = () => {
    const word = document.querySelector("#searched_word").value;
    const lang = document.querySelector("#lang_collection").value;
    const results = document.querySelector("#results");
    const spinner = document.querySelector("#spinner");

    spinner.removeAttribute("hidden");
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

const fetchDefinitionsText = (e) => {
    for (let i = 0; i < words.length; i++) {
        words[i].className = words[i].className.replace(" mark", "");
    }
    const word = e.target.innerText;
    const lang = document.querySelector("#lang_collection").value;
    const results = document.querySelector("#results");
    const spinner = document.querySelector("#spinner");

    e.target.className += " mark";
    spinner.removeAttribute("hidden");
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

fetchCollections();
submitBtn.addEventListener("click", fetchDefinitions);

for (let i = 0; i < words.length; i++) {
    words[i].addEventListener("click", fetchDefinitionsText);
}