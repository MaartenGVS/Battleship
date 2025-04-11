"use strict";

function generateVisualAPIErrorInConsole(error) {
    console.error('%c%s', 'background-color: red;color: white', '! An error occurred while calling the API');
    console.table(error);
}

function errorHandler(error) {
    generateVisualAPIErrorInConsole(error);
    document.querySelector(_config.errorHandlerSelector).innerText = 'Something went wrong :(';
}
