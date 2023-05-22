document.addEventListener('DOMContentLoaded', function() {
    var searchPlayerButton = document.getElementById('searchPlayerButton');
    var inputField = document.getElementById('playerName');
    var loadingOverlay = document.getElementById('loadingOverlay');
    var isLoading = false;

    searchPlayerButton.addEventListener('click', performSearch);
    inputField.addEventListener('keypress', function(event) {
        if (event.key === 'Enter') {
            performSearch();
        }
    });

    function showLoadingOverlay() {
        isLoading = true;
        loadingOverlay.style.display = 'flex';
    }

    function hideLoadingOverlay() {
        isLoading = false;
        loadingOverlay.style.display = 'none';
    }

    function performSearch() {
        if (isLoading) {
            return;
        }

        var highlightedButton = document.querySelector('button.btn.btn-dark.mt-5.me-4.btnRegion.button-highlight');
        var playerName = inputField.value;

        if (playerName.trim() === '') {
            alert('Player name field cannot be empty');
            return;
        }

        if (highlightedButton) {
            var highlightedButtonText = highlightedButton.innerText.toUpperCase();
        }

        var data = {
            username: playerName,
            region: highlightedButtonText
        };

        showLoadingOverlay();

        fetch('/mmrChecks', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
            .then(response => response.json())
            .then(result => {
                if (result.hasOwnProperty('uss')) {
                    document.getElementById('nameH1').textContent = result.uss;
                } else {
                    console.log('Incorrect data');
                }

                var imgElement = document.getElementById("rankImg");
                if (result.hasOwnProperty('elo')) {
                    if (result.elo.includes("Gold")) {
                        imgElement.src = "/assets/img/Season_2022_-_Gold.webp";
                        document.getElementById('rankH1').textContent = "RANK: " + result.elo;
                    } else if (result.elo.includes("Iron")) {
                        imgElement.src = "/assets/img/Season_2022_-_Iron.webp";
                        document.getElementById('rankH1').textContent = "RANK: " + result.elo;
                    } else if (result.elo.includes("Platinum")) {
                        imgElement.src = "/assets/img/Season_2022_-_platine.webp";
                        document.getElementById('rankH1').textContent = "RANK: " + result.elo;
                    } else if (result.elo.includes("Diamond")) {
                        imgElement.src = "/assets/img/Season_2022_-_Diamond.webp";
                        document.getElementById('rankH1').textContent = "RANK: " + result.elo;
                    } else if (result.elo.includes("Master")) {
                        imgElement.src = "/assets/img/Season_2022_-_Master.webp";
                        document.getElementById('rankH1').textContent = "RANK: " + result.elo;
                    } else if (result.elo.includes("Grandmaster")) {
                        imgElement.src = "/assets/img/Season_2022_-_Grandmaster.webp";
                        document.getElementById('rankH1').textContent = "RANK: " + result.elo;
                    } else if (result.elo.includes("Challenger")) {
                        imgElement.src = "/assets/img/Season_2022_-_Challenger.webp";
                        document.getElementById('rankH1').textContent = "RANK: " + result.elo;
                    }
                } else {
                    imgElement.src = "";
                    document.getElementById('rankH1').textContent = "";
                }

                if (result.hasOwnProperty('mmr')) {
                    document.getElementById('mmrH1').textContent = "MMR: " + result.mmr;
                } else {
                    document.getElementById('mmrH1').textContent = "";
                }

                hideLoadingOverlay();
            })
            .catch(error => {
                console.error(error);
                hideLoadingOverlay();
            });
    }
});
