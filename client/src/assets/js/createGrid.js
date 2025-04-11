"use strict";

const _CAPITAL_LETTER_A_IN_CHAR_CODE = 65;
const _A_LABEL_CELL = 1;
const _A_INTEGER_CELL = 1;


function createGrid() {
    const $rows = parseInt(_userInput.boardSize.rows);
    const $cols = parseInt(_userInput.boardSize.cols);
    const totalCells = $rows * $cols;
    const $gridContainers = document.querySelectorAll("div.grid");
    $gridContainers.forEach($gridContainer => {
        applyGridStyles($gridContainer, $rows, $cols);
        createGridCells($gridContainer, totalCells, $cols);
    });
}

function applyGridStyles($gridContainer, numberOfRows, numberOfCols) {
    $gridContainer.style.gridTemplateColumns = `repeat(${numberOfCols + _A_INTEGER_CELL}, 2rem)`;
    $gridContainer.style.gridTemplateRows = `repeat(${numberOfRows + _A_LABEL_CELL}, 2rem)`;
    $gridContainer.style.width = `${(numberOfCols + _A_LABEL_CELL) * 2}rem`;
}

function createGridCells($gridContainer, totalCells, numberOfCols) {
    createColumnHeaders($gridContainer, numberOfCols);
    for (let index = 0; index < totalCells; index++) {
        const $currentRow = Math.floor(index / numberOfCols);
        const $currentColumn = (index % numberOfCols);
        createRowHeaderCells($gridContainer, $currentRow, $currentColumn);
        const $cell = createCellElement($gridContainer);
        setCellDataAttributes($cell, $currentRow, $currentColumn);
        assignGridCellClasses($gridContainer, $cell);
    }
}


function createColumnHeaders($gridContainer, numberOfCols) {
    makeFirstDiv($gridContainer);
    for (let index = 1; index <= numberOfCols; index++) {
        createColumnHeaderCell($gridContainer, index);
    }
}

function makeFirstDiv($gridContainer) {
    const $emptyDiv = "<div></div>";
    $gridContainer.insertAdjacentHTML("beforeend", $emptyDiv);
}


function createColumnHeaderCell($gridContainer, index) {
    $gridContainer.insertAdjacentHTML("beforeend",
        `
            <div>${index}</div>
        `
    );
}

function createRowHeaderCells($gridContainer, currentRow, currentColumn) {
    if (!isFirstColumn(currentColumn)) {
        return;
    }
    const letter = getLetter(currentRow);
    $gridContainer.insertAdjacentHTML("beforeend",
        `
                <div>${letter}</div>
            `
    );
}

function isFirstColumn(currentColumn) {
    return (currentColumn === 0);
}

function getLetter(currentRow) {
    return String.fromCharCode(_CAPITAL_LETTER_A_IN_CHAR_CODE + currentRow);
}

function createCellElement($gridContainer) {
    $gridContainer.insertAdjacentHTML("beforeend", "<div></div>");
    return $gridContainer.lastChild;
}

function assignGridCellClasses($gridContainer, $cell) {
    !$gridContainer.classList.contains("hidden") ? $cell.classList.add("cell", "friendly") : $cell.classList.add("enemy");
}

function setCellDataAttributes(cell, currentRow, currentColumn) {
    cell.dataset.row = (currentRow + _A_LABEL_CELL).toString();
    cell.dataset.col = (currentColumn + _A_INTEGER_CELL).toString();
}
