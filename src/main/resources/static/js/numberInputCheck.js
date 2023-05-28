function checkNumberRange() {
    var input = document.getElementById("numberInput");
    var number = parseInt(input.value);

    if (isNaN(number) || number < 1 || number > 999) {
        input.value = "0"; // Заменяем значение на 0
    }
}
