function checkNumberRange() {
    var input = document.getElementById("numberInput");
    var priceElement = document.getElementById("productPrice");
    var initialPrice = parseFloat(priceElement.getAttribute("data-initial-price").replace("$", ""));
    var stock = document.getElementById("stock").value;
    var stockNum = parseInt(stock);

    var number = parseInt(input.value);
    var formattedPrice = parseFloat(initialPrice * number).toLocaleString(undefined, { minimumFractionDigits: 2 }).replace(",", ".");

    if(number < 1){
        formattedPrice = parseFloat(0).toLocaleString(undefined, { minimumFractionDigits: 2 }).replace(",", ".");
    }


    priceElement.textContent = "$" + formattedPrice;

    if (isNaN(number) || number < 1 || number > stockNum) {
        input.value = "0";
        priceElement.textContent = "$" + "0.00";
    }
}


