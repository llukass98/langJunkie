const cards = document.getElementsByClassName("flash_card");
const backSideBtns = document.getElementsByClassName("back_side_btn");
const closeBtns = document.getElementsByClassName("close_btn");
const editBtns = document.getElementsByClassName("edit_btn");
const addCardBtn = document.querySelector("#add_card_btn");
const editForm = document.querySelector("#edit_form");
const addForm = document.querySelector("#add_form");
const editCloseBtn = document.querySelector("#edit_close_btn");
const addCloseBtn = document.querySelector("#add_close_btn");

const expandCard = (e) => {
    e.target.parentElement.children[0].setAttribute("hidden", "");
    e.target.parentElement.children[1].removeAttribute("hidden");
    e.target.parentElement.style = "width: 50rem; position: absolute; top: 20%; left:25%";
}

const showBackSide = (e) => {
    const cardId = e.target.parentElement.children[4].innerText;
    document.querySelector("#back_side" + cardId).removeAttribute("hidden");
    e.target.setAttribute("hidden", "");
}

const hideBackSide = (e) => {
    const cardId = e.target.parentElement.children[4].innerText;
    document.querySelector("#back_side" + cardId).setAttribute("hidden", "");
    e.target.parentElement.children[0].removeAttribute("hidden");
    e.target.parentElement.parentElement.setAttribute("hidden", "");
}

const compressCard = (e) => {
    hideBackSide(e);
    e.target.parentElement.parentElement.parentElement.style = "width: 18rem";
    e.target.parentElement.parentElement.parentElement.firstElementChild.removeAttribute("hidden");
}

const showEditForm = (e) => {
    const cardId = e.target.parentElement.children[4].innerText;
    const word = document.querySelector("#word" + cardId).innerText;
    const language = document.querySelector("#language" + cardId).innerText;
    const front = document.querySelector("#front_side" + cardId).innerText;
    const back = document.querySelector("#back_side" + cardId).innerText;

    editForm.children[0].firstElementChild.value = word;
    editForm.children[2].firstElementChild.value = language;
    editForm.children[3].firstElementChild.value = front;
    editForm.children[4].firstElementChild.value = back;
    editForm.setAttribute("action", "/update/card/" + cardId);
    editForm.removeAttribute("hidden");
}

const hideEditForm = () => {
    editForm.setAttribute("hidden", "");
}

const showAddForm = () => {
    addForm.removeAttribute("hidden");
}

const hideAddForm = () => {
    addForm.setAttribute("hidden", "");
}

for (let i = 0; i < cards.length; i++) {
    cards[i].addEventListener("click", expandCard);
}

for (let i = 0; i < closeBtns.length; i++) {
    closeBtns[i].addEventListener("click", compressCard);
}

for (let i = 0; i < backSideBtns.length; i++) {
    backSideBtns[i].addEventListener("click", showBackSide);
}

for (let i = 0; i < editBtns.length; i++) {
    editBtns[i].addEventListener("click", showEditForm);
}

addCardBtn.addEventListener("click", showAddForm);
editCloseBtn.addEventListener("click", hideEditForm);
addCloseBtn.addEventListener("click", hideAddForm);
