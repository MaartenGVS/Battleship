"use strict";
let _turnIndex = 1;
let _selectedShip = null;
const _ROW = parseInt(loadFromStorage("rows"));
const _COL = parseInt(loadFromStorage("cols"));

function handleShipSelection(e) {
    const $ships = document.querySelectorAll('div.image');
    const shipsMarkedForPlacement = Array.from($ships).filter(ship => ship.classList.contains('place'));
    const isAShipMarkedForPlacement = shipsMarkedForPlacement.length > 0;

    if (isAShipMarkedForPlacement) {
        return;
    }

    const selectedShips = Array.from($ships).filter(ship => ship.classList.contains('selected'));
    selectedShips.forEach(ship => ship.classList.remove('selected'));
    e.target.closest('div').classList.add('selected');
    _selectedShip = e.target.closest('div').id;
}

function displayPlacements(e) {
    e.preventDefault();
    const $selectedShip = document.querySelector('div.image.selected');
    const $clickedCell = e.target;
    const isValidCell = $clickedCell.classList.contains('cell');

    if (!$selectedShip || !isValidCell) {
        return;
    }

    const shipTail = getShipTailLength($selectedShip);
    displayCellAsMiddleAndShipAsPlace($clickedCell, $selectedShip);
    displayPlacementOptions($clickedCell, shipTail);
}

function displayCellAsMiddleAndShipAsPlace($clickedCell, $selectedShip) {
    $selectedShip.classList.remove("selected");
    $selectedShip.classList.add("place");
    $clickedCell.classList.add("middle");
}

function displayPlacementOptions($clickedCell, shipTail) {
    const clickedCoords = {
        row: parseInt($clickedCell.dataset.row),
        col: parseInt($clickedCell.dataset.col),
    };
    const $cells = document.querySelectorAll('div.cell');
    let coordsForPlacement = {};

    coordsForPlacement = createPossibleCoordsForPlacement(clickedCoords, shipTail);
    filterValidCoords(coordsForPlacement, shipTail);
    highlightPlacementOptions($cells, clickedCoords, coordsForPlacement);
    removeInvalidSelectedCells(shipTail);
    document.querySelector('button.turn').classList.remove('hidden');
}

function createPossibleCoordsForPlacement(coordinatesClickedCell, shipTail) {
    const coordinateObject = {leftCols: [], rightCols: [], topRows: [], bottomRows: []};

    for (let i = coordinatesClickedCell.col - shipTail; i < coordinatesClickedCell.col; i++) {
        addValidCoords(i, coordinateObject, 'leftCols', _ROW);
    }
    for (let i = coordinatesClickedCell.col + shipTail; i > coordinatesClickedCell.col; i--) {
        addValidCoords(i, coordinateObject, 'rightCols', _ROW);
    }
    for (let i = coordinatesClickedCell.row - shipTail; i < coordinatesClickedCell.row; i++) {
        addValidCoords(i, coordinateObject, 'topRows', _COL);
    }
    for (let i = coordinatesClickedCell.row + shipTail; i > coordinatesClickedCell.row; i--) {
        addValidCoords(i, coordinateObject, 'bottomRows', _COL);
    }

    return coordinateObject;
}

function addValidCoords(i, coordsObject, directionKey, max) {
    if (i > 0 && i <= max) {
        coordsObject[directionKey].push(i);
    }
}

function filterValidCoords(coordinateObject, shipTail) {
    for (const arrayOfCoordinates in coordinateObject) {
        if (coordinateObject[arrayOfCoordinates].length !== shipTail) {
            coordinateObject[arrayOfCoordinates] = [];
        }
    }
}

function highlightPlacementOptions($cells, clickedCoordinates, coordinatesForPlacement) {
    $cells.forEach($cell => {
        $cell.classList.remove('selected');
        const row = parseInt($cell.dataset.row);
        const col = parseInt($cell.dataset.col);

        if (row === clickedCoordinates.row) {
            coordinatesForPlacement.leftCols.forEach(colNumber => highlightCell(colNumber, $cell, true, true));
            coordinatesForPlacement.rightCols.forEach(colNumber => highlightCell(colNumber, $cell, true, false));
        }
        if (col === clickedCoordinates.col) {
            coordinatesForPlacement.topRows.forEach(rowNumber => highlightCell(rowNumber, $cell, false, true));
            coordinatesForPlacement.bottomRows.forEach(rowNumber => highlightCell(rowNumber, $cell, false, false));
        }
    });
}

function highlightCell(colOrRowNumber, $cell, isCol = true, isLeftOrTop = true) {
    const dimension = isCol ? 'col' : 'row';

    if (colOrRowNumber !== parseInt($cell.dataset[dimension])) {
        return;
    }

    $cell.classList.add('selected');
    if (isCol) {
        $cell.classList.add(isLeftOrTop ? 'left' : 'right');
    } else {
        $cell.classList.add(isLeftOrTop ? 'top' : 'bottom');
    }
}

function removeSelectedFromInvalidPosition(position, shipTailLength) {
    const $position = document.querySelectorAll(position);
    const positionLength = $position.length;

    if (positionLength === shipTailLength) {
        return;
    }

    $position.forEach($positionCell => {
        $positionCell.classList.remove("selected");
    });
}

function removeInvalidSelectedCells(shipTailLength) {
    const positionsClasses = ['.left', '.right', '.top', '.bottom'];

    positionsClasses.forEach(position => {
        removeSelectedFromInvalidPosition(position, shipTailLength);
    });
}

function turnButton() {
    return document.querySelector('button.turn');
}

function getShipTailLength($ship) {
    return $ship.childElementCount - 1;
}

function turnShip() {
    const $ships = document.querySelectorAll('div.image');
    const $ship = (Array.from($ships).filter($ship => $ship.classList.contains('place')))[0];

    if (!$ship) {
        return;
    }

    const stateGridCells = {
        cells: document.querySelectorAll('div.cell'),
        headPlacementCell: document.querySelector('div.cell.middle'),
        leftCells: document.querySelectorAll('div.cell.left'),
        rightCells: document.querySelectorAll('div.cell.right'),
        topCells: document.querySelectorAll('div.cell.top'),
        bottomCells: document.querySelectorAll('div.cell.bottom')
    };
    cycleTailTurningDisplay(stateGridCells, getShipTailLength($ship));
    displayButtons();
}


function cycleTailTurningDisplay(stateGridCells, shipTail) {
    const directions = ['leftCells', 'topCells', 'rightCells', 'bottomCells'];

    _turnIndex = (_turnIndex % directions.length);
    displayTail(stateGridCells, shipTail, directions[_turnIndex]);
    _turnIndex++;
}

function displayTail(stateGridCells, shipTail, direction) {
    if (stateGridCells[direction].length !== shipTail) {
        return;
    }

    placeImagesInCell(stateGridCells, stateGridCells.headPlacementCell, stateGridCells[direction]);
}

function placeImagesInCell(stateGridCells, $shipHeadCell, $directionCells) {
    stateGridCells.cells.forEach($cell => {
        $cell.classList.remove("head", "tail");
        $cell.classList.add("none");
    });

    $shipHeadCell.classList.remove("none");
    $shipHeadCell.classList.add("head");

    $directionCells.forEach($cell => {
        $cell.classList.remove("none");
        $cell.classList.add("tail");
    });
}

function displayButtons() {
    const $dumpButton = document.querySelector('button.dump');
    const $TurnButton = turnButton();
    $dumpButton.classList.remove('hidden');
    $TurnButton.innerHTML = "TURN SHITS";
}

function removePlacedShipFromAside() {
    const $shipMarkedForPlacement = document.querySelector('div.image.place');
    $shipMarkedForPlacement.classList.remove('place');
    $shipMarkedForPlacement.innerHTML = "";
}

function removeDisplayOptionClasses($cells) {
    $cells.forEach($cell => $cell.classList.remove('selected', 'middle', 'left', 'right', 'top', 'bottom', 'place'));
}

function removeCellClassesFromGrid() {
    Array.from(document.querySelectorAll(".friendly.none")).forEach($cell => $cell.classList.remove("none"));
}

function resetShipPlacementDisplay() {
    const $divs = document.querySelectorAll("div");
    removeDisplayOptionClasses($divs);
}

function confirmShipPlacement() {
    const $cells = document.querySelectorAll('div.cell');
    const cellsOfShipParts = getCellsOfShipParts($cells);

    if (cellsOfShipParts.length > 0) {
        removePlacedShipFromAside();
        removeDisplayOptionClasses($cells);
        cellsOfShipParts.forEach($cell => markCellOccupied($cell));
        hideButtons();
    } else {
        resetShipPlacementDisplay();
        hideButtons();
    }

    if (!areAllShipsPlaced()) {
        return;
    }

    showBattleButton();
    removeCellClassesFromGrid();
}

function getCellsOfShipParts($cells) {
    return Array.from($cells).filter($cell => $cell.classList.contains('head') || $cell.classList.contains('tail'));
}

function markCellOccupied($selector) {
    $selector.classList.remove("cell");
    if ($selector.classList.contains("occupied")) {
        return;
    }
    $selector.classList.add(_selectedShip);
    $selector.classList.add("occupied");
}

function areAllShipsPlaced() {
    const $allImages = document.querySelectorAll(".shipsToBePlaced div img");
    return $allImages.length === 0;
}

function displayBattleButton() {
    const $battleButton = document.querySelector("button.getFleet");
    $battleButton.classList.add("hidden");
}

function hideButtons() {
    const $buttonToDump = document.querySelector('button.dump');
    const $buttonToTurn = turnButton();
    $buttonToTurn.innerHTML = "PLACE SHITS";
    $buttonToDump.classList.add('hidden');
    $buttonToTurn.classList.add('hidden');
}
