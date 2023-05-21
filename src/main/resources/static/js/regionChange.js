$(document).ready(function() {
    $(document).on("click", "#buttonEUW", function() {
        $("#mainContent").load("blocks/mainBlockEU.html");
    });

    $(document).on("click", "#buttonRU", function() {
        $("#mainContent").load("blocks/mainBlockRU.html");
    });
});