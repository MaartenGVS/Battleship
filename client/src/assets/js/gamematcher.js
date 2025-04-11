"use strict";

let _token = null;
let _gameId = null;

async function battleButtonHandler(e) {
    e.preventDefault();
    _ships.forEach(createShipLocationsArray);
    saveToStorage("ships", _ships);
    displayWaitingScreen();
    processGame();
    waitForStart(displayStartedGame);
}

function createShipLocationsArray(ship) {
    let firstIndexIsHeadOfShip = false;
    document.querySelectorAll(".grid div").forEach($div => {
        if ($div.classList.contains(ship.name)) {
            const $row = $div.getAttribute("data-row");
            const $col = $div.getAttribute("data-col");
            ship.location.push(`${String.fromCharCode(64 + parseInt($row))}-${$col}`);
            if ($div.classList.contains("head") && ship.location.length === 1) {
                firstIndexIsHeadOfShip = true;
            }
        }
    });
    if (firstIndexIsHeadOfShip) {
        ship.location.reverse();
    }
}

function displayWaitingScreen() {
    const $aside = document.querySelector(".container");
    $aside.classList.add("hidden");
    const $h1 = document.querySelector('h1');
    $h1.classList.add("hidden");

    const $overlayDiv = document.querySelector("div.overlay");
    $overlayDiv.classList.remove("hidden");

    const $loadingImg = document.querySelector("img.loading");
    $loadingImg.classList.remove("hidden");
}

function processGame() {
    const gameMatcher = {
        commander: _userInput.commanderName,
        type: _userInput.gameMode,
        fleet: {
            rows: parseInt(_userInput.boardSize.rows),
            cols: parseInt(_userInput.boardSize.cols),
            ships: _ships,
        },
        prefix: _userInput.prefix,
    };
    connectToGame(gameMatcher);
}

function connectToGame(gameData) {
    fetchFromServer('/games', 'POST', gameData)
        .then(res => {
            _token = {};
            _token.playerToken = res.playerToken;
            _gameId = res.gameId;
            saveToStorage('playerToken', res.playerToken);
            saveToStorage('gameId', res.gameId);

            checkGameStatus(res.gameId);
        })
        .catch(error => errorHandler(error));
}

function waitForStart(callback) {
    if (_startedGame) {
        callback();
    } else {
        setTimeout(function () {
            waitForStart(callback);
        }, 1000);
    }
}

function displayStartedGame() {
    const $aside = document.querySelector("aside");
    const $buttonDiv = document.querySelector("div.buttons");
    const $gridToShow = document.querySelector("div.grid.hidden");
    const $partitionWall = document.querySelector(".partitionWall");
    const $container = document.querySelector(".container");
    const $overlayDiv = document.querySelector("div.overlay");
    const $loadingImg = document.querySelector("img.loading");
    const $randomButton = document.querySelector(".random");
    const $avatarFromOtherPlayer = document.querySelector(".otherCommanderName img");
    makeHidden([$aside, $buttonDiv, $overlayDiv, $loadingImg, $randomButton]);
    removeHidden([$gridToShow, $partitionWall, $container, $avatarFromOtherPlayer]);
    displayBattleButton();
    saveToStorage("winner", null);
}


function removeHidden(array) {
    array.forEach(element => {
        if (element) {
            element.classList.remove("hidden");
        }
    });
}


