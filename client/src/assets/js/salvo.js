"use strict";

let _salvoSize = null;
let _enemyCommander = null;
const _SELECTED_CELLS = [];

async function handleCellSelectionForSalvo(e) {
    e.preventDefault();
    if (!_isAttackingCommander) {
        return;
    }
    selectCellOnGridForAttack(e);
}

function resetDisplay() {
    const $moveButton = document.querySelector("button.move");
    const $shootButton = document.querySelector("button.shoot");
    [$moveButton, $shootButton].forEach($element => $element.classList.add("hidden"));
    removeBorderFromPreviousSelectedShip();
}

function handleShootSelection() {
    if (_SELECTED_CELLS.length > _salvoSize) {
        _SELECTED_CELLS.shift().classList.remove('selected');
        hideShootButton();
    }

    if (_SELECTED_CELLS.length === _salvoSize) {
        showShootButton();
    }
}

function selectCellOnGridForAttack(e) {
    const $targetCell = e.target;
    const isEnemyCell = $targetCell.classList.contains("enemy");
    const isSelectedEnemyCell = $targetCell.classList.contains("selected");
    const isBagged = $targetCell.classList.contains("sunk");
    const isMiss = $targetCell.classList.contains("miss");

    if (!isEnemyCell || isSelectedEnemyCell || isBagged || isMiss) {
        return;
    }

    $targetCell.classList.add("selected");
    _SELECTED_CELLS.push($targetCell);
    handleShootSelection();
}

function hideShootButton() {
    const $button = document.querySelector(".shoot");
    $button.classList.add("hidden");
}

function showShootButton() {
    const $button = document.querySelector("button.shoot");
    $button.classList.remove("hidden");
}


async function handleShot(e) {
    e.preventDefault();
    const salvo = createSalvo();
    const isSalvoValid = Object.keys(salvo.salvo).length === _salvoSize;

    if (!isSalvoValid) {
        return;
    }

    const shotResult = {};
    await processShot(salvo, shotResult);
    displayShotResult(shotResult.data);
    resetDisplay();
    _salvoSize = null;
}

function createSalvo() {
    const salvoArray = [];

    _SELECTED_CELLS.forEach($cell => {
        const row = parseInt($cell.dataset.row);
        const col = $cell.dataset.col;

        salvoArray.push(`${String.fromCharCode(64 + row)}-${col}`);
    });

    return {
        "salvo": salvoArray
    };
}

async function processShot(salvo, result) {
    const response = await sendSalvo(salvo);
    if (response === null) {
        return;
    }
    result.data = response;
}

function sendSalvo(salvo) {
    return fetchFromServer(`/games/${_gameId}/fleet/${_enemyCommander}/salvo`, 'POST', salvo)
        .then(res => {
            return res;
        })
        .catch(error => {
            console.error(error);
            return null;
        });
}

function displayShotResult(data) {
    for (const coordinate in data) {
        if (data[coordinate] !== null) {
            const shipName = data[coordinate];
            displayHitOnCell(coordinate, true, shipName);
        } else {
            displayMissOnCell(coordinate, true);
        }
    }
}

function displayHitOnCell(shotCoordinate, isEnemyGrid = true, shipName = null) {
    const $cell = findCellElement(shotCoordinate, isEnemyGrid);
    if (!$cell) {
        return;
    }
    if (isEnemyGrid) {
        resetClasses($cell);
        $cell.classList.add(shipName);
    }
    $cell.classList.remove("none", "tail", "head", "cell", "friendly", "occupied", "enemy");
    $cell.classList.add("hit");
}

function displayMissOnCell(shotCoordinate, isEnemyGrid = true) {
    const $cell = findCellElement(shotCoordinate, isEnemyGrid);
    if (!$cell) {
        return;
    }
    if (_userInput.gameMode.includes("move")) {
        return;
    }
    resetClasses($cell);
    $cell.classList.add("miss");
}

function findCellElement(coordinate, isEnemyGrid = true) {
    const row = coordinate.charCodeAt(0) - 64;
    const col = parseInt(coordinate.substring(2));

    const gridCoordinate = {row, col};

    if (isEnemyGrid) {
        return document.querySelector(`div.enemy[data-row="${gridCoordinate.row}"][data-col="${gridCoordinate.col}"]`);
    }
    return document.querySelector(`div.friendly[data-row="${gridCoordinate.row}"][data-col="${gridCoordinate.col}"]`);
}

function resetClasses(selector) {
    selector.className = '';
    if (_userInput.gameMode.includes("move")) {
        selector.classList.add("enemy");
    }
}

