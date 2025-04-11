"use strict";

function fetchFromServer(path, httpVerb, requestBody) {
    const options = constructOptions(httpVerb, requestBody);

    // return fetch(`${_config.getAPIUrl()}${path}`, options)
    return fetch(`http://localhost:8034${path}`, options)
        .then((response) => {
            // Do not catch response.ok because API handles it differently
            return response.json();
        })
        .then((jsonresponsetoparse) => {
            if (jsonresponsetoparse.failure) {
                throw jsonresponsetoparse;
            }
            return jsonresponsetoparse;
        });
}

function constructOptions(httpVerb, requestBody) {
    const options = {
        method: httpVerb,
        headers: {
            "Content-Type": "application/json"
        }
    };

    if (_token !== null) {
        options.headers["Authorization"] = "Bearer " + _token.playerToken;
    }
    // Don't forget to add data to the body when needed
    options.body = JSON.stringify(requestBody);
    return options;
}
