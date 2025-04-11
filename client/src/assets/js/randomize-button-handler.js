"use strict";
const _DIRECTIONS = {
    1: "north",
    2: "east",
    3: "south",
    4: "west"
};

const _X = "x";
const _Y = "y";


function randomizedButtonHandler(e) {
    e.preventDefault();
    clearSelectedShip();
    clearBoard();
    clearAside();
    selectAllShips();
    hideButtons();
    showBattleButton();
}

function clearSelectedShip() {
    const $asideShipsToBePlaced = document.querySelector('aside.shipsToBePlaced');
    $asideShipsToBePlaced.innerHTML = `<h2>drop your shits</h2>`;

    const $selectedShip = document.querySelector('.image.selected');

    if ($selectedShip) {
        $selectedShip.classList.remove('selected');
    }
}

function showBattleButton () {
    const $battleButton = document.querySelector("button.getFleet");
    $battleButton.classList.remove("hidden");
}

function clearBoard() {
    const $cellsOfMyGrid = document.querySelectorAll(".myGrid div");
    $cellsOfMyGrid.forEach($cell => {
        if ($cell.classList.contains("cell") || $cell.classList.contains("friendly")) {
            $cell.setAttribute("class", "");
            $cell.classList.add("cell", "friendly");
        }
    });
}

function clearAside() {
    const $divsFromTheAside = document.querySelectorAll(".shipsToBePlaced div");
    $divsFromTheAside.forEach($div => {
        $div.innerHTML = "";
    });
}


function selectAllShips() {
    fetchFromServer("/ships", "GET")
        .then(data => {
            placeShips(data);
        })
        .catch(error => errorHandler(error));
}

function addClassesToCell(cell, isHead, name) {
    if (isHead) {
        cell.classList.add("head");
    } else {
        cell.classList.add("tail");
    }
    cell.classList.add("occupied");
    cell.classList.add(name);
}

function addClassesForTheNorthDirection(startingCoordinates, shipLength, name) {
    let $cell;
    for (let i = startingCoordinates[_Y]; i > startingCoordinates[_Y] - shipLength; i--) {
        $cell = getCellByCoordinate(i, startingCoordinates[_X]);
        addClassesToCell($cell, i === startingCoordinates[_Y], name);
    }
}


function addClassesForTheEastDirection(startingCoordinates, shipLength, name) {
    let $cell;
    for (let i = startingCoordinates[_X]; i < startingCoordinates[_X] + shipLength; i++) {
        $cell = getCellByCoordinate(startingCoordinates[_Y], i);
        addClassesToCell($cell, i === startingCoordinates[_X], name);
    }
}

function addClassesForTheSouthDirection(startingCoordinates, shipLength, name) {
    let $cell;
    for (let i = startingCoordinates[_Y]; i < startingCoordinates[_Y] + shipLength; i++) {
        $cell = getCellByCoordinate(i, startingCoordinates[_X]);
        addClassesToCell($cell, i === startingCoordinates[_Y], name);
    }
}

function addClassesForTheWestDirection(startingCoordinates, shipLength, name) {
    let $cell;
    for (let i = startingCoordinates[_X]; i > startingCoordinates[_X] - shipLength; i--) {
        $cell = getCellByCoordinate(startingCoordinates[_Y], i);
        addClassesToCell($cell, i === startingCoordinates[_X], name);
    }
}

function addClasses(startingCoordinates, direction, shipLength, name) {

    switch (direction) {
        default:
            console.error("OEPSIE");
            break;
        case "north":
            addClassesForTheNorthDirection(startingCoordinates, shipLength, name);
            break;
        case "east":
            addClassesForTheEastDirection(startingCoordinates, shipLength, name);
            break;
        case "south":
            addClassesForTheSouthDirection(startingCoordinates, shipLength, name);
            break;
        case "west":
            addClassesForTheWestDirection(startingCoordinates, shipLength, name);
            break;
    }
}

function placeShips(data) {
    data.ships.forEach(ship =>{
        placeShip(ship);
    });
}

function placeShip(ship) {
    const shipLength = ship.size;
    const directionNumber = getRandomNumberInRange(1, 5);
    const direction = _DIRECTIONS[directionNumber];
    const startingCoordinates = getRandomStartingCoordinates(direction, shipLength);
    const name = ship.name;

    if (canBePlaced(startingCoordinates, direction, shipLength)) {
        addClasses(startingCoordinates, direction, shipLength, name);
    } else {
        placeShip(ship);
    }
}

function getRandomStartingCoordinates(direction, shipLength) {
    const boardLengthY = _userInput.boardSize.rows;
    const boardLengthX = _userInput.boardSize.cols;
    const startingCoordinates = {};

    if (direction === "north") {
        startingCoordinates[_X] = getRandomNumberInRange(1, boardLengthX);
        startingCoordinates[_Y] = getRandomNumberInRange(shipLength, boardLengthY);
    } else if (direction === "east") {
        startingCoordinates[_X] = getRandomNumberInRange(1, boardLengthX - shipLength + 1);
        startingCoordinates[_Y] = getRandomNumberInRange(1, boardLengthY);
    } else if (direction === "south") {
        startingCoordinates[_X] = getRandomNumberInRange(1, boardLengthX);
        startingCoordinates[_Y] = getRandomNumberInRange(1, boardLengthY - shipLength + 1);
    } else if (direction === "west") {
        startingCoordinates[_X] = getRandomNumberInRange(shipLength, boardLengthX);
        startingCoordinates[_Y] = getRandomNumberInRange(1, boardLengthY);
    } else {
        console.error("Invalid direction");
    }
    return startingCoordinates;
}


function checkPlacement(possibleCoordinate, shipLength) {
    for (let i = 0; i < shipLength; i++) {
        if (!isCellFree(possibleCoordinate(i))) {
            return false;
        }
    }
    return true;
}

function canBePlaced(startingCoordinates, direction, shipLength) {
    const {x, y} = startingCoordinates;

    switch (direction) {
        case "north":
            return checkPlacement((i) => getCellByCoordinate(y - i, x), shipLength);
        case "east":
            return checkPlacement((i) => getCellByCoordinate(y, x + i), shipLength);
        case "south":
            return checkPlacement((i) => getCellByCoordinate(y + i, x), shipLength);
        case "west":
            return checkPlacement((i) => getCellByCoordinate(y, x - i), shipLength);
        default:
            errorHandler("Randomize button error, please try again.");
            return false;
    }
}

function isCellFree(cell) {
    let result = true;
    if (cell.classList.contains("occupied")) {
        result = false;
    }
    return result;

}

function getRandomNumberInRange(min, max) {
    return Math.floor(Math.random() * (max - min) + min);
}

function getCellByCoordinate(y, x) {
    let cell;
    const rows = document.querySelectorAll(`[data-row='${y.toString()}']`);

    rows.forEach($cell => {
        if ($cell.classList.contains("friendly") && $cell.getAttribute("data-col") === x.toString()) {
            cell = $cell;
        }
    });
    return cell;
}

