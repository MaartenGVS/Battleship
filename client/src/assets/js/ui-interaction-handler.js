"use strict";

function navigation() {
    const $nextButtons = document.querySelectorAll(".navigate");
    $nextButtons.forEach($button => {
        $button.addEventListener("click", (e) => navigateToNextPage(e, $button));
    });
}

function navigateToNextPage(e, button) {
    e.preventDefault();
    const location = button.getAttribute("data-next-page");
    if (location !== null) {
        window.location.href = location;
    }
}

function getUserInput() {
    getUserSelectedGameMode();
    getUserSelectedType();
    getUserPrefix();
    getCommanderName();
    updateUserInputFromStorage();
}

function updateUserInputFromStorage() {
    if (theUserIsOn("grid")) {
        _userInput.gameMode = loadFromStorage("gameMode") === "combo" ? "move+salvo" : loadFromStorage("gameMode");
        _userInput.private = loadFromStorage("type") === "private";
        _userInput.public = loadFromStorage("type") === "public";
        _userInput.prefix = loadFromStorage("prefix") || "lonelyThisChristmas";
        _userInput.commanderName = loadFromStorage("commanderName");
        _userInput.boardSize.rows = loadFromStorage("rows");
        _userInput.boardSize.cols = loadFromStorage("cols");
    }
}

function getUserSelectedGameMode() {
    addEventListenerToElements(".gameModes article", "gameMode", "");
}

function getUserSelectedType() {
    addEventListenerToElements(".type div", "type", "");
}

function addEventListenerToElements(selector, key, defaultValue) {
    const $elements = document.querySelectorAll(selector);
    if ($elements) {
        $elements.forEach($element => {
            $element.addEventListener("click", (e) => saveSelectedValue(e, key, $element, defaultValue));
        });
    }
}

function saveSelectedValue(e, key, element, defaultValue) {
    e.preventDefault();
    const value = element.id || defaultValue;
    saveToStorage(key, value);
}

function getUserPrefix() {
    if (theUserIsOn("roomPrefix")) {
        const $button = document.querySelector("button.navigate:nth-child(3)");
        $button.addEventListener("click", e => savePrefixAndNavigate(e));
    }
}

function savePrefixAndNavigate(e) {
    e.preventDefault();
    if (theUserIsOn("roomPrefix")){
        const $prefix = document.querySelector("input[type='text']#roomPrefix").value;
        if ($prefix.length !== 0) {
            saveToStorage("prefix", $prefix);
            window.location.href = "commanderName.html";
        } else {
            showErrorAnimation("input#roomPrefix");
        }
    }
}

function getCommanderName() {
    const $button = document.querySelector(".commanderNameFor button[type='submit']");
    if ($button) {
        $button.addEventListener("click", e => saveCommanderNameAndNavigate(e));
    }
}

function saveCommanderNameAndNavigate(e) {
    e.preventDefault();
    const $commanderName = document.querySelector("input[type='text']#commanderName").value;
    if (($commanderName.length !== 0) && ($commanderName.length <= 15)) {
        saveToStorage("commanderName", $commanderName);
        window.location.href = "boardSetting.html";
    } else {
        showErrorAnimation("input#commanderName");
    }
}

function showErrorAnimation(selector) {
    const $inputField = document.querySelector(selector);
    $inputField.classList.add("error");
    $inputField.addEventListener("animationend", () => {
        $inputField.classList.remove("error");
    });
}
