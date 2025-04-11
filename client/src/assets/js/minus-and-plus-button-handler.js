"use strict";

const _MINIMUM_GRID_SIZE = 10;
const _MAXIMUM_GRID_SIZE = 15;
const _COUNTER = 1;

function minusAndPlusButtonHandler() {
    const $rowInput = document.querySelector("#row");
    const $colInput = document.querySelector("#column");
    const $boardSizeSubmit = document.querySelector(".boardSizeSubmit");

    initializeButtons($rowInput, $colInput);

    $boardSizeSubmit.addEventListener("click", (e) => {
        e.preventDefault();
        saveToStorage("rows", $rowInput.value);
        saveToStorage("cols", $colInput.value);
    });
}

function initializeButtons($rowInput, $colInput) {
    const $minusBtnRow = document.querySelector(".minusRow");
    const $plusBtnRow = document.querySelector(".plusRow");
    const $minusBtnCol = document.querySelector(".minusCol");
    const $plusBtnCol = document.querySelector(".plusCol");

    handleMinusBtn($minusBtnRow, $rowInput, _MINIMUM_GRID_SIZE, _COUNTER);
    handlePlusBtn($plusBtnRow, $rowInput, _MAXIMUM_GRID_SIZE , _COUNTER);
    handleMinusBtn($minusBtnCol, $colInput, _MINIMUM_GRID_SIZE, _COUNTER);
    handlePlusBtn($plusBtnCol, $colInput, _MAXIMUM_GRID_SIZE, _COUNTER);
}

function handleMinusBtn(minusBtn, input, minValue, value) {
    minusBtn.addEventListener("click", function () {
        if (input.value > minValue) {
            input.value = parseInt(input.value) - value;
        }
    });
}

function handlePlusBtn(plusBtn, input, maxValue, value) {
    plusBtn.addEventListener("click", function () {
        if (input.value < maxValue) {
            input.value = parseInt(input.value) + value;
        }
    });
}

