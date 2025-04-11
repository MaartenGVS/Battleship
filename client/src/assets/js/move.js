"use strict";

function patchMove(gameId, commander, shipName) {
    fetchFromServer(`/games/${gameId}/fleet/${commander}/${shipName}/location`, 'PATCH')
        .then(res => {
            move(res.ship.name, res.location);
        })
        .catch(error => {
            handleMoveError(error);
        });
}

function handleMoveError(error) {
    if (!error.cause) {
        return;
    }
    showError(error.cause);
}

function moveHandler() {
    const $cell = document.querySelector(".selectedToMove");
    resetDisplay();
    patchMove(_gameId, _userInput.commanderName, getShipName($cell));
}

function showError(message) {
    const $errorMessageElement = document.querySelector('#error-message');
    $errorMessageElement.textContent = message;
    $errorMessageElement.style.display = "inline-block";
    setTimeout(() => {
        $errorMessageElement.style.display = "none";
    }, 2000);
}

function getRow(position) {
    return lettersToNumber(position.charAt(0));
}

function getCol(position) {
    return position.split('-')[1];
}

function move(name, location) {
    resetClasses(name);
    handleMove(location, name);
}

function resetClasses(name) {
    const $grid = document.querySelectorAll(".friendly");
    $grid.forEach($cell => {
        if ($cell.classList.contains(name)) {
            $cell.setAttribute("class", "");
            $cell.classList.add("friendly", "cell");
        }
    });
}


function handleMove(location, name) {
    location.forEach(position => {
        if (location[location.length - 1] !== position) {
            getCell(getRow(position), getCol(position)).classList.add("occupied", "tail", name);
        } else {
            getCell(getRow(position), getCol(position)).classList.add("occupied", "head", name);
        }
    });
}


function getCell(row, col) {
    return document.querySelector(`[data-row='${row}'][data-col='${col}']`);
}

function lettersToNumber(letters) {
    return letters.split('').reduce((r, a) => r * 26 + parseInt(a, 36) - 9, 0);
}

function getShipName($cell) {
    let name = null;
    _ships.forEach(ship => {
        if (!$cell.classList.contains(ship.name)) {
            return;
        }
        name = ship.name;
    });
    return name;
}

function showMoveButton() {
    document.querySelector(".move").classList.remove("hidden");
}

function setBoarderOnSelectedShip(e) {
    const shipName = getShipName(e.target);
    if (!_isAttackingCommander || !shipName) {
        return;
    }
    const shipIsHit = Array.from(document.querySelectorAll(`.${shipName}.hit`));
    if (shipIsHit.length > 0) {
        return;
    }
    removeBorderFromPreviousSelectedShip();
    const $shipSelectedToMove = document.querySelectorAll(`.friendly.${shipName}`);
    $shipSelectedToMove.forEach($cell => $cell.classList.add("selectedToMove"));
    showMoveButton();
}

function removeBorderFromPreviousSelectedShip() {
    const $shipsSelectedToMoveCells = document.querySelectorAll(".selectedToMove");
    if (!$shipsSelectedToMoveCells) {
        return;
    }
    $shipsSelectedToMoveCells.forEach($cell => {
        $cell.classList.remove("selectedToMove");
    });
}

