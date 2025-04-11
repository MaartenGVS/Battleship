"use strict";

const _HEAD = "assets/media/gridImages/ships/EyedPoopOnGrass.png";
const _TAIL = "assets/media/gridImages/ships/1PoopOnGrass.png" ;

function selectAllShip() {
    if (window.location.href.includes('grid.html') && document.querySelector("aside")) {
        fetchFromServer("/ships", "GET")
            .then(data => {
                placeShipInAside(data.ships);
            })
            .catch(error => errorHandler(error));
    }
}


function placeShipInAside(ships){
    let name = "";
    let size = 0;
    ships.forEach(ship => {
        name = "#" + ship.name;
        size = ship.size;
        placeImges(name, size);
    });
}

function placeImges(name, size){
    const $div =  document.querySelector(name);
    $div.insertAdjacentHTML('beforeend',`
             <img src=${_HEAD} alt="">
        ` );
    for (let i = 0; i < parseInt(size) - 1; i++) {
        $div.insertAdjacentHTML('beforeend',`
             <img src=${_TAIL} alt="">
        ` );
    }
}
