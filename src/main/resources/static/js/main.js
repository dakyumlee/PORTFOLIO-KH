document.addEventListener('DOMContentLoaded', function() {
    // Hamburger Menu
    const hamburger = document.querySelector('.hamburger');
    const mobileMenu = document.querySelector('.mobile-menu');
    const mobileLinks = document.querySelectorAll('.mobile-menu a');

    if (hamburger) {
        hamburger.addEventListener('click', function() {
            hamburger.classList.toggle('active');
            mobileMenu.classList.toggle('active');
            document.body.style.overflow = mobileMenu.classList.contains('active') ? 'hidden' : '';
        });

        mobileLinks.forEach(link => {
            link.addEventListener('click', function() {
                hamburger.classList.remove('active');
                mobileMenu.classList.remove('active');
                document.body.style.overflow = '';
            });
        });
    }

    // Nav Scroll Effect
    const nav = document.querySelector('.nav');
    window.addEventListener('scroll', function() {
        if (window.scrollY > 100) {
            nav.classList.add('scrolled');
        } else {
            nav.classList.remove('scrolled');
        }
    });

    // Smooth Scroll
    document.querySelectorAll('a[href^="#"]').forEach(anchor => {
        anchor.addEventListener('click', function(e) {
            e.preventDefault();
            const target = document.querySelector(this.getAttribute('href'));
            if (target) {
                target.scrollIntoView({
                    behavior: 'smooth',
                    block: 'start'
                });
            }
        });
    });

    // Gallery Modal
    const galleryModal = document.getElementById('gallery-modal');
    const galleryModalImg = document.getElementById('gallery-modal-img');
    const galleryModalCaption = document.getElementById('gallery-modal-caption');
    const galleryItems = document.querySelectorAll('.gallery-item');

    galleryItems.forEach(item => {
        item.addEventListener('click', function() {
            const fullUrl = this.dataset.full;
            const title = this.dataset.title || '';
            
            galleryModalImg.src = fullUrl;
            galleryModalCaption.textContent = title;
            galleryModal.classList.add('active');
            document.body.style.overflow = 'hidden';
        });
    });

    // Film Modal
    const filmModal = document.getElementById('film-modal');
    const filmCards = document.querySelectorAll('.film-card');

    filmCards.forEach(card => {
        card.addEventListener('click', function() {
            const title = this.dataset.title || '';
            const year = this.dataset.year || '';
            const role = this.dataset.role || '';
            const director = this.dataset.director || '';
            const description = this.dataset.description || '';
            const poster = this.dataset.poster || '';

            document.getElementById('film-modal-title').textContent = title;
            document.getElementById('film-modal-year').textContent = year;
            document.getElementById('film-modal-poster').src = poster;
            
            const directorWrap = document.getElementById('film-modal-director-wrap');
            const roleWrap = document.getElementById('film-modal-role-wrap');
            
            if (director) {
                document.getElementById('film-modal-director').textContent = director;
                directorWrap.style.display = 'flex';
            } else {
                directorWrap.style.display = 'none';
            }
            
            if (role) {
                document.getElementById('film-modal-role').textContent = role;
                roleWrap.style.display = 'flex';
            } else {
                roleWrap.style.display = 'none';
            }
            
            document.getElementById('film-modal-description').textContent = description;

            filmModal.classList.add('active');
            document.body.style.overflow = 'hidden';
        });
    });

    // Close Modals
    document.querySelectorAll('.modal-close').forEach(closeBtn => {
        closeBtn.addEventListener('click', function() {
            closeAllModals();
        });
    });

    document.querySelectorAll('.modal').forEach(modal => {
        modal.addEventListener('click', function(e) {
            if (e.target === modal) {
                closeAllModals();
            }
        });
    });

    document.addEventListener('keydown', function(e) {
        if (e.key === 'Escape') {
            closeAllModals();
        }
    });

    function closeAllModals() {
        document.querySelectorAll('.modal').forEach(modal => {
            modal.classList.remove('active');
        });
        document.body.style.overflow = '';
    }
});

// PWA Service Worker
if ('serviceWorker' in navigator) {
    window.addEventListener('load', function() {
        navigator.serviceWorker.register('/sw.js').catch(function(error) {
            console.log('SW registration failed');
        });
    });
}
