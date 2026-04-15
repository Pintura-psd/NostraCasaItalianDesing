const Navbar = {

    init() {
        this.initLanguageDropdown();
        this.initScrollNavbar();
        this.initMobileMenu();
    },

    initLanguageDropdown() {
        const toggles = document.querySelectorAll('.dropdown-click .dropdown-toggle');

        toggles.forEach(toggle => {
            toggle.addEventListener('click', this.toggleDropdown);
        });

        document.addEventListener('click', this.closeOutside);
    },

    initScrollNavbar() {
        const navbar = document.querySelector('.custom-navbar');

        window.addEventListener('scroll', () => {
            if (window.scrollY > 50) {
                navbar.classList.add('scrolled');
            } else {
                navbar.classList.remove('scrolled');
            }
        });
    },

    toggleDropdown(e) {
        e.preventDefault();

        const dropdown = this.closest('.dropdown-click');

        document.querySelectorAll('.dropdown-click').forEach(d => {
            if (d !== dropdown) d.classList.remove('active');
        });

        dropdown.classList.toggle('active');
    },

    closeOutside(e) {
        if (!e.target.closest('.dropdown-click')) {
            document.querySelectorAll('.dropdown-click').forEach(d => {
                d.classList.remove('active');
            });
        }
    },

    initMobileMenu() {
        const burger = document.querySelector('.nav-burger');
        const offcanvas = document.querySelector('.nav-offcanvas');
        const closeBtn = document.querySelector('.close-offcanvas');
        const backdrop = document.querySelector('.offcanvas-backdrop');

        if (!burger || !offcanvas || !closeBtn || !backdrop) return;

        const openMenu = () => {
            offcanvas.classList.add('active');
            backdrop.classList.add('active');
            document.body.style.overflow = 'hidden'; // bloquea scroll
        };

        const closeMenu = () => {
            offcanvas.classList.remove('active');
            backdrop.classList.remove('active');
            document.body.style.overflow = ''; // restaura scroll
        };

        burger.addEventListener('click', openMenu);
        closeBtn.addEventListener('click', closeMenu);
        backdrop.addEventListener('click', closeMenu);
    }
};

document.addEventListener('DOMContentLoaded', () => {
    Navbar.init();

});