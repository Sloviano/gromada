function send() {
    const to = document.getElementById("to").value;
    const msg = document.getElementById("message").value;

    stompClient.send("/app/chat.private", {}, JSON.stringify({
        recipient: to,
        content: msg
    }));

    document.getElementById("message").value = "";
}