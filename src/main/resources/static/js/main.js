// Post current snippet to server
function send() {
    let object = {
        "code": document.getElementById("code_snippet").value
    };

    let json = JSON.stringify(object);

    let xhr = new XMLHttpRequest();
    xhr.open("POST", '/api/code/new', false)
    xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
    xhr.send(json);

    if (xhr.status === 200) {
        alert("Success!");
        // Refresh page to show uploaded content in feed
        location.reload();
    } else {
        alert("Failure")
    }
}