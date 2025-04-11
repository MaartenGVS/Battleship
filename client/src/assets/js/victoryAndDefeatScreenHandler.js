"use strict";

function displayVictoryScreen() {
    displayEndGameScreen("assets/media/victoryAndDefeatScreenImages/winImage.png");
}

function displayDefeatScreen() {
    displayEndGameScreen("assets/media/victoryAndDefeatScreenImages/defeatImage.png");
}

function displayEndGameScreen(imageSrc) {
    makeGridContainerTagsHiddenAndShowTheWinOrLoseScreen();
    displayVictoryOrDefeatImage(imageSrc);
    setH2Title();
    playAgainButtonClickHandler();
}


function makeGridContainerTagsHiddenAndShowTheWinOrLoseScreen() {
    const $header = document.querySelector("header");
    const $gridContainer = document.querySelector(".container");
    const $victoryOrDefeatScreenContainer = document.querySelector(".victoryOrDefeatScreen");
    makeHidden([$header, $gridContainer]);
    makeUnhidden([$victoryOrDefeatScreenContainer]);
}

function displayVictoryOrDefeatImage(src) {
    const $img = document.querySelector(".victoryOrDefeatScreen img");
    $img.setAttribute("src", src);
}

function setH2Title() {
    const $h2Title = document.querySelector(".victoryOrDefeatScreen h2");
    $h2Title.innerHTML = "Do you want to play again?";
}

function playAgainButtonClickHandler() {
    const $victoryOrDefeatScreenButtonsContainer = document.querySelector(".victoryOrDefeatScreen .buttons");
    const $yesButton = document.querySelector(".victoryOrDefeatScreen .yes");
    const $noButton = document.querySelector(".victoryOrDefeatScreen .no");
    if (!$yesButton) {
        return;
    }
    yesButtonClickHandler($victoryOrDefeatScreenButtonsContainer, $yesButton, $noButton);
    noButtonClickHandler($noButton);
}

function noButtonClickHandler($noButton){
    $noButton.addEventListener("click", (e) => {
        e.preventDefault();
        saveToStorage("winner", null);
        window.location.href = "index.html";
    });
}

function yesButtonClickHandler($victoryOrDefeatScreenButtonsContainer, $yesButton, $noButton) {
    $yesButton.addEventListener("click", (e) => {
        e.preventDefault();
        hidePlayAgainButtons($yesButton, $noButton);
        playerHasFirstChosenForAPublicGame()
            ? showPlayAgainButtonsForPublicPossibilities($victoryOrDefeatScreenButtonsContainer)
            : showPlayAgainButtonsForPrivatePossibilities($victoryOrDefeatScreenButtonsContainer);
    });
}

function hidePlayAgainButtons($yesButton, $noButton) {
    makeHidden([$yesButton, $noButton]);
}

function playerHasFirstChosenForAPublicGame() {
    return _userInput.public;
}

function showPlayAgainButtonsForPublicPossibilities($victoryOrDefeatScreenButtonsContainer) {
    generatePlayAgainButtonsForPublicHTML($victoryOrDefeatScreenButtonsContainer);
    const $againPublicButton = document.querySelector(".victoryOrDefeatScreen .againPublic");
    const $privateButton = document.querySelector(".victoryOrDefeatScreen .private");
    const $otherGameModeButton = document.querySelector(".victoryOrDefeatScreen .otherGameMode");
    againPublicButtonClickHandler($victoryOrDefeatScreenButtonsContainer, $againPublicButton, $privateButton, $otherGameModeButton);
    aPrivateGameButtonClickHandler($victoryOrDefeatScreenButtonsContainer, $againPublicButton, $privateButton, $otherGameModeButton);
    gameModeButtonClickHandler();
}

function generatePlayAgainButtonsForPublicHTML($victoryOrDefeatScreenButtonsContainer) {
    $victoryOrDefeatScreenButtonsContainer.innerHTML =
        `
        <button class="againPublic">A new public game</button>
        <button class="private">A private game</button>
        <button class="otherGameMode">Another game mode</button>
    `;
}

function showPlayAgainButtonsForPrivatePossibilities($victoryOrDefeatScreenButtonsContainer) {
    generatePlayAgainButtonsForPrivateHTML($victoryOrDefeatScreenButtonsContainer);
    const $publicButton = document.querySelector(".victoryOrDefeatScreen .public");
    const $samePersonButton = document.querySelector(".victoryOrDefeatScreen .samePerson");
    const $otherPrivateGameButton = document.querySelector(".victoryOrDefeatScreen .otherPrivateGame");
    const $otherGameModeButton = document.querySelector(".victoryOrDefeatScreen .otherGameMode");
    samePersonButtonClickHandler($victoryOrDefeatScreenButtonsContainer, $samePersonButton);
    aPublicGameButtonClickHandler($victoryOrDefeatScreenButtonsContainer, $publicButton, $samePersonButton, $otherPrivateGameButton, $otherGameModeButton);
    anotherPrivateGameButtonClickHandler($victoryOrDefeatScreenButtonsContainer, $publicButton, $samePersonButton, $otherPrivateGameButton, $otherGameModeButton);
    gameModeButtonClickHandler();
}

function generatePlayAgainButtonsForPrivateHTML($victoryOrDefeatScreenButtonsContainer) {
    $victoryOrDefeatScreenButtonsContainer.innerHTML =
        `
        <button class="samePerson">With the same person</button>
        <button class="public">A public game</button>
        <button class="otherPrivateGame">Another private game</button>
        <button class="otherGameMode">Another game mode</button>
    `;
}


function aPublicGameButtonClickHandler($victoryOrDefeatScreenButtonsContainer, $publicButton, $samePersonButton, $otherPrivateGameButton, $otherGameModeButton) {
    $publicButton.addEventListener("click", (e) => {
        e.preventDefault();
        _userInput.public = true;
        _userInput.private = false;
        saveToStorage("type", "public");
        makeHidden([$publicButton, $samePersonButton, $otherPrivateGameButton, $otherGameModeButton]);
        const $boardButtons = showBoardOptions($victoryOrDefeatScreenButtonsContainer);
        newBoardButtonClickHandler($boardButtons[0], $boardButtons[1]);
        sameBoardButtonClickHandler($boardButtons[0], $boardButtons[1]);
    });
}

function anotherPrivateGameButtonClickHandler($victoryOrDefeatScreenButtonsContainer, $publicButton, $samePersonButton, $otherPrivateGameButton, $otherGameModeButton) {
        $otherPrivateGameButton.addEventListener("click", (e) => {
            e.preventDefault();
            makeHidden([$publicButton, $samePersonButton, $otherPrivateGameButton, $otherGameModeButton]);
            showPrefixInputFieldAndHandlePrivateGameOptions($victoryOrDefeatScreenButtonsContainer);
        });
}


function samePersonButtonClickHandler($victoryOrDefeatScreenButtonsContainer, $samePersonButton) {
    $samePersonButton.addEventListener("click", (e) => {
        e.preventDefault();
        const $boardButtons = showBoardOptions($victoryOrDefeatScreenButtonsContainer);
        makeHidden([$boardButtons[1]]); // because the player has already played with the same board
        newBoardButtonClickHandler($boardButtons[0], $boardButtons[1], false);
    });
}

function againPublicButtonClickHandler($victoryOrDefeatScreenButtonsContainer, $againPublicButton, $privateButton, $otherGameModeButton) {
    $againPublicButton.addEventListener("click", (e) => {
        e.preventDefault();
        makeHidden([$againPublicButton, $privateButton, $otherGameModeButton]);
        const $boardButtons = showBoardOptions($victoryOrDefeatScreenButtonsContainer);
        newBoardButtonClickHandler($boardButtons[0], $boardButtons[1]);
        sameBoardButtonClickHandler($boardButtons[0], $boardButtons[1]);
    });
}


function showBoardOptions($victoryOrDefeatScreenButtonsContainer) {
    $victoryOrDefeatScreenButtonsContainer.innerHTML =
        `
        <button class="newBoard">A new board setting</button>
        <button class="sameBoard">With the same board</button>	
    `;
    return [document.querySelector(".victoryOrDefeatScreen .newBoard"),
        document.querySelector(".victoryOrDefeatScreen .sameBoard")];
}


function newBoardButtonClickHandler($newBoardButton, $sameBoardButton, firstChosenForPublic = true) {
    $newBoardButton.addEventListener("click", (e) => {
        e.preventDefault();
        saveToStorage("winner", null);
        makeHidden([$newBoardButton, $sameBoardButton]);
        loadNewGameWithNewBoard(firstChosenForPublic);
    });
}


function loadNewGameWithNewBoard(firstChosenForPublic = true) {
    resetGridPage();
    if (!firstChosenForPublic) {
        _userInput.public = false;
        _userInput.private = true;
        saveToStorage("type", "private");
    } else {
        _userInput.public = true;
        _userInput.private = false;
        saveToStorage("type", "public");
    }
    window.location.href = "boardSetting.html";
}


function makeHidden(array) {
    array.forEach(element => {
        if (element) {
            element.classList.add("hidden");
        }
    });
}


function makeUnhidden(array) {
    array.forEach(element => {
        if (element) {
            element.classList.remove("hidden");
        }
    });
}

function sameBoardButtonClickHandler($newBoardButton, $sameBoardButton) {
    $sameBoardButton.addEventListener("click", async (e) => {
        e.preventDefault();
        makeHidden([$newBoardButton, $sameBoardButton]);
        saveToStorage("clickedOnSameBoard", true);
        window.location.reload();
    });
}


function loadNewGameWithSameBoardAfterPageHasReloaded() {
    const $myGridDivs = document.querySelectorAll(".myGrid div");
    _ships = loadFromStorage("ships");
    if (_ships) {
        saveToStorage("clickedOnSameBoard", false);
        placeShipsOnMyGrid($myGridDivs, _ships);
        displayWaitingScreen();
        processGame();
        waitForStart(displayStartedGame);
    }
}

function placeShipsOnMyGrid(container, ships) {
    ships.forEach(ship => {
        ship.location.forEach((location, index) => {
            const [row, col] = location.split('-');
            container.forEach($div => {
                const divRow = parseInt($div.getAttribute('data-row'));
                const divCol = $div.getAttribute('data-col');
                if (divRow === parseInt(getRowCharCode(row)) && divCol === col) {
                    addClassesToMyGrid(index, ship, $div);
                }
            });
        });
    });
}


function addClassesToMyGrid(index, ship, $div) {
    if (index === ship.location.length - 1) {
        $div.classList.add("head", "eyd", `${ship.name}`);
        $div.classList.remove("cell", "none");
    } else {
        $div.classList.add("tail", `${ship.name}`, "occupied");
        $div.classList.remove("cell", "none");
    }
}


function getRowCharCode(row) {
    return row.charCodeAt(0) - 64;
}


function aPrivateGameButtonClickHandler($victoryOrDefeatScreenButtonsContainer, $againPublicButton, $privateButton, $otherGameModeButton) {
    $privateButton.addEventListener("click", (e) => {
        e.preventDefault();
        makeHidden([$privateButton, $againPublicButton, $otherGameModeButton]);
        showPrefixInputFieldAndHandlePrivateGameOptions($victoryOrDefeatScreenButtonsContainer);
    });
}


function showPrefixInputFieldAndHandlePrivateGameOptions($victoryOrDefeatScreenButtonsContainer) {
    $victoryOrDefeatScreenButtonsContainer.insertAdjacentHTML("beforeend",
        `
        <form class="prefixInput">
          <label for="prefix">Type here your prefix: </label>
            <input id="prefix" name="prefix" type="text">
            <button type="submit">Enter</button>
        </form>  
    `
    );
    prefixInputHandler($victoryOrDefeatScreenButtonsContainer);
}

function gameModeButtonClickHandler() {
    const $gameModeButton = document.querySelector(".otherGameMode");
    $gameModeButton.addEventListener("click", (e) => {
        e.preventDefault();
        saveToStorage("winner", null);
        window.location.href = "gameMode.html";
    });
}

function prefixInputHandler($victoryOrDefeatScreenButtonsContainer) {
    const $prefixForm = document.querySelector(".prefixInput");
    $prefixForm.addEventListener("submit", (e) => {
        e.preventDefault();
        const $prefixInputValue = document.querySelector(".prefixInput input[name='prefix']").value;
        handlePrefixInput($prefixInputValue, $victoryOrDefeatScreenButtonsContainer);
    });
}


function handlePrefixInput($prefixInputValue, $victoryOrDefeatScreenButtonsContainer) {
    if ($prefixInputValue.length !== 0) {
        savePrefixInput($prefixInputValue);
        const $boardButtons = showBoardOptions($victoryOrDefeatScreenButtonsContainer);
        newBoardButtonClickHandler($boardButtons[0], $boardButtons[1], false);
        sameBoardButtonClickHandler($boardButtons[0], $boardButtons[1]);
    } else {
        showErrorAnimation("input[name='prefix']");
    }
}

function savePrefixInput($prefixInputValue) {
    saveToStorage("prefix", $prefixInputValue);
    saveToStorage("type", "private");
    _userInput.private = true;
    _userInput.public = false;
    _userInput.prefix = $prefixInputValue;
}


function resetGridPage() {
    const $container = document.querySelector(".container");
    const $header = document.querySelector("header");
    const $victoryOrDefeatScreenContainer = document.querySelector(".victoryOrDefeatScreen");
    [$container, $header].forEach(element => {
        element.classList.remove("hidden");
    });
    makeHidden([$victoryOrDefeatScreenContainer]);
}


