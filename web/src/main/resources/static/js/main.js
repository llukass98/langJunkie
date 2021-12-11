"use strict";

const langCollectionField = document.querySelector("#lang_collection");
const definitionsCard = document.querySelector("#definitions_card");
const definitionCloseBtn = document.querySelector("#definition_close_btn");
const words = document.getElementsByClassName("word");
const textLoaderBtn = document.querySelector("#textloader");
const textArea = document.querySelector("textarea");
const saveCardBtn = document.querySelector("#save");
const collectionTable = { "faen": "persian", "enen": "english" };

// Load all available colections
const fetchCollections = () => {
    const resp = fetch("/api/v1.0b/collections")
        .then(response => response.json())
        .then(data => langCollectionField.setAttribute("value", data.collections))
        .catch(err => console.log(err));
}

const fetchDefinitions = (e) => {
    for (let i = 0; i < words.length; i++) {
        words[i].className = words[i].className.replace(" mark", "");
    }
    const word = e.target.innerText;
    const lang = document.querySelector("#lang_collection").value;
    const results = document.querySelector("#results");
    const spinner = document.querySelector("#spinner");

    e.target.className += " mark";
    spinner.removeAttribute("hidden");
    showDefinitionsCard();
    results.innerText = "";

    const resp = fetch("/api/v1.0b/definitions?lang=" + lang + "&word=" + word)
        .then(response => response.json())
        .then(data => {
            spinner.setAttribute("hidden", "true");
            printResults(data);
        })
        .catch(err => results.innerText = "Nothing has been found");
}

// Print fetched definitions
const printResults = data => {
    const resultSet = data.definitions.map(def => def.results).flat();
    const dictNames = data.definitions.map(def => def.name);
    const dictLink = data.definitions.map(def => def.link);

    results.innerHTML = "<h4><p id=\"word\">" + data.searched_word + "</p></h4> Collection: <i id=\"collection\">" + data.collection + "<p><h5>Definitions found: </p></h5>";
    for (const key of new Set(resultSet).keys()) {
        results.innerHTML += "<span><strong class=\"definition\">" + key + "</strong></span> :: ";
    }

    results.innerHTML += "<br><br><p><i>Dictionaries used: </i></p>";
    for (let i = 0; i < dictNames.length; i++) {
        results.innerHTML += "<i><a href=\"" +
            dictLink[i] + "\"class='btn btn-link' target='_blank'>"+ dictNames[i] + "</a></i>     ";
    }
}

const loadText = () => {
    const text = textArea.value.replace(/\s+/g, " ").split(/[\s,.!?؟]/g);
    const processedText = textArea.parentElement.children[1];
    const documentFragment = document.createDocumentFragment();

    textArea.setAttribute("hidden", "true");

    for (let i = 0; i < text.length; i++) {
        let span = document.createElement("span");
        span.className = "word lead h6";
        span.addEventListener("click", fetchDefinitions);
        span.innerText = text[i];
        if (i % 17 === 0) {
            documentFragment.appendChild(document.createElement("br"))
        }
        documentFragment.appendChild(span);
    }

    processedText.appendChild(documentFragment);
}

const saveCardRequest = () => {
    const request = new XMLHttpRequest();
    const csrfName = document.querySelector("#logout").parentElement.children[1].getAttribute("name")
    const csrfValue = document.querySelector("#logout").parentElement.children[1].getAttribute("value")
    const word = document.querySelector("#word").innerText;
    const definitionElements = document.getElementsByClassName("definition");
    const language = collectionTable[document.querySelector("#lang_collection").value];
    const username = document.querySelector("#username").innerText;
    let definition = "";
    console.log(word);
    console.log(csrfName);
    console.log(csrfValue);
    console.log(language);
    console.log(username);
    for (let i = 0; i < definitionElements.length; i++){
        definition += definitionElements[i].innerText + " :: ";
    }
    const params = "word="+word+"&language="+language+"&backSide="
        +definition+"&username="+username+"&"+csrfName+"="+csrfValue;

    request.open("POST", "/add/card");
    request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
    request.send(params);
    hideDefinitionsCard();
}

textLoaderBtn.addEventListener("click", loadText);

const hideDefinitionsCard = () => {
    definitionsCard.setAttribute("hidden", "true");
}

const showDefinitionsCard = () => {
    definitionsCard.removeAttribute("hidden");
}

for (let i = 0; i < words.length; i++) {
    words[i].addEventListener("click", fetchDefinitions);
}

definitionCloseBtn.addEventListener("click", hideDefinitionsCard);
saveCardBtn.addEventListener("click", saveCardRequest);
fetchCollections();