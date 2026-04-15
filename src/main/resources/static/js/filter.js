document.getElementById("searchInput").addEventListener("input", function () {
    const query = this.value.toLowerCase();

    const cards = document.querySelectorAll(".product-card");

    cards.forEach(card => {
        const name = card.getAttribute("data-name");

        if (name.includes(query)) {
            card.style.display = "";
        } else {
            card.style.display = "none";
        }
    });
});