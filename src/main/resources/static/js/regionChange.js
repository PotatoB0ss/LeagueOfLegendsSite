$(document).ready(function() {
    $(document).on("click", "#buttonEUW", function() {
        $("#mainContent").load("blocks/mainBlockEU.html", function() {

            $.ajax({
                url: "/accountData",
                method: "POST",
                success: function(response) {
                    var data = response.model;
                    $("#euActive").text('Stock: ' + data.eu_active);
                    $("#euInactive").text('Stock: ' + data.eu_inactive);
                    $("#euIronBronze").text('Stock: ' + data.eu_iron_bronze);
                    $("#euSilver").text('Stock: ' + data.eu_silver);
                    $("#euGold").text('Stock: ' + data.eu_gold);
                    $("#euPlatinum").text('Stock: ' + data.eu_platinum);
                },
                error: function(xhr, status, error) {

                    console.log("Ошибка AJAX-запроса:", error);
                }
            });
        });
    });

    $(document).on("click", "#buttonRU", function() {
        $("#mainContent").load("blocks/mainBlockRU.html", function() {

            $.ajax({
                url: "/accountData",
                method: "POST",
                success: function(response) {
                    var data = response.model;
                    $("#ruActive").text('Stock: ' + data.ru_active);
                    $("#ruInactive").text('Stock: ' + data.ru_inactive);
                    $("#ruIronBronze").text('Stock: ' + data.ru_iron_bronze);
                    $("#ruSilver").text('Stock: ' + data.ru_silver);
                    $("#ruGold").text('Stock: ' + data.ru_gold);
                    $("#ruPlatinum").text('Stock: ' + data.ru_platinum);
                },
                error: function(xhr, status, error) {

                    console.log("Ошибка AJAX-запроса:", error);
                }
            });
        });
    });
});