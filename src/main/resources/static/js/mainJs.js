document.addEventListener('DOMContentLoaded', function () {
    const dropdown = document.getElementById('catalogDropdown');

    dropdown.addEventListener('mouseenter', function () {
        const menu = dropdown.querySelector('.dropdown-menu');
        menu.classList.add('show');
    });

    dropdown.addEventListener('mouseleave', function () {
        const menu = dropdown.querySelector('.dropdown-menu');
        menu.classList.remove('show');
    });
});