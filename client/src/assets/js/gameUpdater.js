"use strict";

let _startedGame = false;
let _isAttackingCommander = false;
let _previousShotCoordinate = "";

function checkGameStatus(gameId) {
    fetchFromServer(`/games/${gameId}`, 'GET')
        .then(res => {
            if (res.started) {
                checkIfPlayerIsAttackingCommander(res);
                _startedGame = res.started;
                checkForWinner(res.winner);
                if (res.winner === null) {
                    setTimeout(() => checkGameStatus(gameId), _config.delay);
                }
            } else {
                setTimeout(() => checkGameStatus(gameId), _config.delay);
            }
        })
        .catch(error => errorHandler(error));
}

function isAttackingCommander(game) {
    return game.attackingCommander === _userInput.commanderName;
}

function checkIfPlayerIsAttackingCommander(game) {
    _isAttackingCommander = isAttackingCommander(game);
    if (_isAttackingCommander) {
        _salvoSize = game.salvoSize[_userInput.commanderName];
        attackingGameDisplay(game.defendingCommander, _isAttackingCommander, game.history);
    } else {
        defendingGameDisplay(game.attackingCommander, _isAttackingCommander, null, game.sunkShips);
    }
}


function attackingGameDisplay(commanderName, isAttacking = true, shotHistory = null, sunkShipData = null) {
    _enemyCommander = commanderName;
    updateCommanderStatus(isAttacking);
    displayOpponentShotHistory(shotHistory);
    displayMySunkShips();
    displayCommanderName("#foe", _enemyCommander);
}

function defendingGameDisplay(commanderName, isAttacking = true, shotHistory = null, sunkShipData = null) {
    _enemyCommander = commanderName;
    updateCommanderStatus();
    displayEnemySunkShips(sunkShipData[_enemyCommander]);
    displayCommanderName("#foe", _enemyCommander);
}

function checkForWinner(winner) {
    if (winner === null) {
        return;
    }
    winner === _userInput.commanderName ? displayVictoryScreen() : displayDefeatScreen();
    saveToStorage("winner", _userInput.commanderName);
}

function removeSelectedSalvoCells() {
    _SELECTED_CELLS.forEach($cell => $cell.classList.remove("selected"));
    _SELECTED_CELLS.splice(0, _SELECTED_CELLS.length);
}

function updateCommanderStatus(isAttacking = false) {
    const $commanderStatusTitle = document.querySelector('h1');
    const $myGrid = document.querySelector('.myGrid');
    const $opponentGrid = document.querySelector('.otherGrid');
    const $commanderImg = document.querySelector(".myCommanderName img");
    const $attackingParagraph = document.querySelector("#attack");
    const $defendingParagraph = document.querySelector("#defend");
    setCommanderDisplay($commanderStatusTitle, $myGrid , $opponentGrid ,$commanderImg,$attackingParagraph,$defendingParagraph,isAttacking );
}

function showParagraphs($attackingParagraph,$defendingParagraph) {
    if ($attackingParagraph.classList.contains('hidden') || $defendingParagraph.classList.contains('hidden')){
        $attackingParagraph.classList.remove('hidden');
        $defendingParagraph.classList.remove('hidden');

    }
}

function displayForMoveGameMode($attackingParagraph, isAttacking, waitParagraph, $defendingParagraph) {
    const isGameModeWithMove = _userInput.gameMode.includes("move");
    if (isGameModeWithMove) {
        $attackingParagraph.innerHTML = isAttacking ? "Click on a battleSHIT on the grid to initiate a move!" : waitParagraph;
        $defendingParagraph.innerHTML = isAttacking ? `Select positions on the grid to fire! SALVO SIZE: ${_salvoSize}` : waitParagraph;
    }
}

function setCommanderDisplay($commanderStatusTitle, $myGrid, $opponentGrid, $commanderImg,$attackingParagraph, $defendingParagraph, isAttacking) {
    $commanderStatusTitle.classList.remove("hidden");
    $commanderStatusTitle.innerText = isAttacking ? "ATTACK" :"DEFEND" ;
    $myGrid.style.boxShadow = isAttacking ? "0 0 20px 20px #D92525" : "0 0 20px 20px #2fc5ff";
    $opponentGrid.style.boxShadow = isAttacking ? "0 0 20px 20px #D92525" : "0 0 20px 20px #2fc5ff";
    $commanderImg.style.border = isAttacking ? "1px solid red" : "1px solid blue";
    const waitParagraph = "Wait for the opponent to make their move and fire back!";
    $attackingParagraph.innerHTML = isAttacking ? "YOUR TURN TO ATTACK" : waitParagraph;
    $defendingParagraph.innerHTML = isAttacking ? `Select positions on the grid to fire! SALVO SIZE: ${_salvoSize}` : waitParagraph;
    if (!isAttacking) {
        removeSelectedSalvoCells();
    }
    displayForMoveGameMode($attackingParagraph, isAttacking, waitParagraph, $defendingParagraph);
    showParagraphs($attackingParagraph,$defendingParagraph);
}

function displayOpponentShotHistory(history) {
    if (history.length > 0) {
        const lastEntryIndex = (history.length - 1);
        const lastFiredShotResult = history[lastEntryIndex].result;
        for (const shotCoordinate in lastFiredShotResult) {
            if (shotCoordinate !== _previousShotCoordinate) {
                displayOpponentShot(shotCoordinate, lastFiredShotResult[shotCoordinate]);
                _previousShotCoordinate = shotCoordinate;
            }
        }
    }
}

function displayOpponentShot(shotCoordinate, shotIsHit) {
    if (shotIsHit) {
        displayHitOnCell(shotCoordinate, false);
    } else {
        displayMissOnCell(shotCoordinate, false);
    }
}

function displayMySunkShips() {
    _ships.forEach(ship => {
        displayIfShipSunk(ship.name, ship.location.length);
    });
}

function displayIfShipSunk(nameOfShip, shipLength) {
    const $shipOnGridCells = document.querySelectorAll(`.${nameOfShip}.hit`);

    if ($shipOnGridCells.length === shipLength) {
        setCellAsSunk($shipOnGridCells);
        _ships = _ships.filter(ship => ship.name !== nameOfShip);
    }
}

function setCellAsSunk($cells) {
    $cells.forEach($cell => {
        resetClasses($cell);
        $cell.classList.add('sunk');
    });
}


function displayEnemySunkShips(sunkShipsData) {
    if (sunkShipsData.length > 0) {
        sunkShipsData.forEach(sunkShip => {
            const shipName = sunkShip.name.toUpperCase();
            displaySunkShip(shipName);
        });
    }
}

function displaySunkShip(shipName) {
    const $sunkShipCells = document.querySelectorAll(`.${shipName}`);

    if ($sunkShipCells !== null) {
        setCellAsSunk($sunkShipCells);
    }
}
