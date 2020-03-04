let stompClient = null;

function connectWebSocket() {
    let socket = new SockJS('/sequences-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/sequences', function (sequences) {
            showSequences(JSON.parse(sequences.body));
        });
        setSequencesLength();
    });
}

function disconnectWebSocket() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
}

function setSequencesLength() {
    let value = $("#length").val();
    value = (value === "") ?  10 : +value;
    if (typeof(value) != "number" || value < 10 || value > 100) {
        alert("Введите значение в диапазоне от 10 до 100!");
        return;
    }
    stompClient.send("/app/set-sequences-length", {}, JSON.stringify({'length': value}));
}

function showSequences(sequences) {
    let bodyOfTheTable = "";
    let sequencesLength = sequences.length;
    for (let i = 0; i < sequencesLength; i++) {
        bodyOfTheTable += "<tr>";
        let sequenceLength = sequences[i].length;
        for (let j = 0; j < sequenceLength; j++) {
            bodyOfTheTable += "<td>" + sequences[i][j] + "</td>";
        }
        bodyOfTheTable += "</tr>";
    }
    $("#sequences").html(bodyOfTheTable);
}

function sequenceGeneration() {
    stompClient.send("/app/request-sequences", null, null);
    console.log("requested new sequences");
}

function automaticSequenceGeneration() {
    stompClient.send("/app/switch-auto-generation", null, null);
    console.log("switched auto generation");
}

$(function () {
    $( "form" ).on('submit', function (e) { e.preventDefault(); });
    $( "#generate" ).click(function() { sequenceGeneration(); });
    $( "#auto-generation" ).click(function() { automaticSequenceGeneration(); });
    $( window ).on("beforeunload", function() { disconnectWebSocket(); });
    $( "#send" ).click(function() { setSequencesLength(); });

    connectWebSocket();
});