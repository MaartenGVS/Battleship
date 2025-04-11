"use strict";

document.addEventListener('DOMContentLoaded', init);

function init() {
    navigation();
    getUserInput();
    initializeMinusAndPlusButtonHandler();
    initializeGrid();
    initializeShipSelection();
    initializeGridClickListener();
    initializeButtonClickListeners();
    selectAllShip();
    initializeMove();
}

function initializeMinusAndPlusButtonHandler() {
    if (theUserIsOn("boardSetting")) {
        minusAndPlusButtonHandler();
    }
}

function displayCommanderName(selector, commanderName) {
    if (theUserIsOn("grid")) {
        const $container = document.querySelector(selector);
        if ($container.innerHTML !== "") {
            $container.innerHTML = `${commanderName}`;
            $container.classList.remove("hidden");
        }
    }
}

function initializeGrid() {
    if (theUserIsOn("grid")) {
        createGrid();
    }
    displayCommanderName('#friend', _userInput.commanderName);
}

function initializeShipSelection() {
    document.querySelectorAll("div.image").forEach(battleShip => {
        battleShip.addEventListener('click', handleShipSelection);
    });
}

function initializeGridClickListener() {
    document.querySelectorAll("div.myGrid, div.otherGrid").forEach($grid => {
        $grid.addEventListener("click", (e) => {
            $grid.classList.contains("myGrid") ? displayPlacements(e) : handleCellSelectionForSalvo(e);
        });
    });
}



function initializeButtonClickListeners() {
    if (theUserIsOn("grid")) {
        document.querySelector('button.turn').addEventListener('click', turnShip);
        document.querySelector('button.dump').addEventListener('click', confirmShipPlacement);
        document.querySelector(".getFleet").addEventListener("click", battleButtonHandler);
        document.querySelector('button.shoot').addEventListener('click', handleShot);
        initializeRandomButton();
       if (loadFromStorage("winner") !== null && loadFromStorage("clickedOnSameBoard") !== null){
           window.addEventListener("load", loadNewGameWithSameBoardAfterPageHasReloaded);
       }

    }
}

function theUserIsOn(page) {
    return window.location.pathname.includes(page);
}


function initializeRandomButton() {
    const $randomizedButton = document.querySelector('button.random');
    $randomizedButton.addEventListener("click", randomizedButtonHandler);
}

function initializeMove() {
    if (_userInput.gameMode === null || !_userInput.gameMode.includes("move")) {
        return;
    }
    document.querySelector(".myGrid").addEventListener("click", setBoarderOnSelectedShip);
    document.querySelector(".move").addEventListener("click", moveHandler);
}
