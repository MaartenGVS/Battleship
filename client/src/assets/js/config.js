"use strict";

const _config = {
    groupnumber: '39',
    errorHandlerSelector: '.errormessages p',
    getAPIUrl: function () {
        return `https://project-i.ti.howest.be/battleship-${this.groupnumber}/api`;
    },
    delay: 2000
};
