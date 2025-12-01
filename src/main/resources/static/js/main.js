document.addEventListener('DOMContentLoaded', () => {
    
    const nav = document.querySelector('.nav');
    const hamburger = document.querySelector('.hamburger');
    const mobileMenu = document.querySelector('.mobile-menu');
    const navLinks = document.querySelectorAll('.nav-menu a, .mobile-menu a');

    window.addEventListener('scroll', () => {
        if (window.scrollY > 50) {
            nav.classList.add('scrolled');
        } else {
            nav.classList.remove('scrolled');
        }
    });

    if (hamburger) {
        hamburger.addEventListener('click', () => {
            hamburger.classList.toggle('active');
            mobileMenu.classList.toggle('active');
            document.body.style.overflow = mobileMenu.classList.contains('active') ? 'hidden' : '';
        });
    }

    navLinks.forEach(link => {
        link.addEventListener('click', (e) => {
            e.preventDefault();
            const targetId = link.getAttribute('href');
            const targetSection = document.querySelector(targetId);
            
            if (targetSection) {
                if (mobileMenu.classList.contains('active')) {
                    hamburger.classList.remove('active');
                    mobileMenu.classList.remove('active');
                    document.body.style.overflow = '';
                }

                const navHeight = nav.offsetHeight;
                const targetPosition = targetSection.offsetTop - navHeight;

                window.scrollTo({
                    top: targetPosition,
                    behavior: 'smooth'
                });
            }
        });
    });

    const galleryItems = document.querySelectorAll('.gallery-item');
    const galleryModal = document.getElementById('gallery-modal');
    const galleryModalImg = document.getElementById('gallery-modal-img');
    const galleryModalCaption = document.getElementById('gallery-modal-caption');
    const modalCloses = document.querySelectorAll('.modal-close');

    galleryItems.forEach(item => {
        item.addEventListener('click', () => {
            const fullImg = item.getAttribute('data-full');
            const caption = item.getAttribute('data-caption');
            
            galleryModalImg.src = fullImg;
            galleryModalCaption.textContent = caption || '';
            
            galleryModal.classList.add('active');
            document.body.style.overflow = 'hidden';
        });
    });

    const filmCards = document.querySelectorAll('.film-card');
    const filmModal = document.getElementById('film-modal');
    
    const modalPoster = document.getElementById('film-modal-poster');
    const modalTitle = document.getElementById('film-modal-title');
    const modalYear = document.getElementById('film-modal-year');
    const modalRole = document.getElementById('film-modal-role');
    const modalDirector = document.getElementById('film-modal-director');
    const modalRuntime = document.getElementById('film-modal-runtime');
    const modalGenre = document.getElementById('film-modal-genre');
    const modalProduction = document.getElementById('film-modal-production');
    const modalSynopsis = document.getElementById('film-modal-synopsis');
    
    const modalAwardsSection = document.getElementById('film-modal-awards');
    const modalAwardsList = document.getElementById('film-modal-awards-list');
    
    const modalSupportsSection = document.getElementById('film-modal-supports');
    const modalSupportsList = document.getElementById('film-modal-supports-list');
    
    const modalGallerySection = document.getElementById('film-modal-gallery-section');
    const modalGalleryGrid = document.getElementById('film-modal-gallery-grid');
    
    const modalVideoSection = document.getElementById('film-modal-video-section');
    const modalVideoContainer = document.getElementById('film-modal-video-container');

    filmCards.forEach(card => {
        card.addEventListener('click', () => {
            const d = card.dataset;

            if(d.poster) {
                modalPoster.src = d.poster;
                modalPoster.parentElement.style.display = 'block';
            } else {
                modalPoster.parentElement.style.display = 'none';
            }
            
            modalTitle.textContent = d.title;
            modalYear.textContent = d.year;
            modalRole.textContent = d.role;
            modalDirector.textContent = d.director;
            modalRuntime.textContent = d.runtime || '-';
            modalGenre.textContent = d.genre || '-';
            modalProduction.textContent = d.production || '-';
            modalSynopsis.textContent = d.synopsis || '';

            modalAwardsList.innerHTML = '';
            if (d.awards && d.awards.length > 0) {
                const awards = d.awards.split('||');
                awards.forEach(awardStr => {
                    const parts = awardStr.split('::');
                    if (parts.length >= 2) {
                        const li = document.createElement('li');
                        li.innerHTML = `<strong>${parts[1]}</strong> ${parts[0]} <span class="year">(${parts[2]})</span>`;
                        modalAwardsList.appendChild(li);
                    }
                });
                modalAwardsSection.style.display = 'block';
            } else {
                modalAwardsSection.style.display = 'none';
            }

            modalSupportsList.innerHTML = '';
            if (d.supports && d.supports.length > 0) {
                const supports = d.supports.split('||');
                supports.forEach(supportStr => {
                    const parts = supportStr.split('::');
                    const li = document.createElement('li');
                    
                    let text = `<strong>${parts[0]}</strong>`;
                    if(parts[1] && parts[1] !== 'null') text += ` - ${parts[1]}`;
                    
                    if(parts[2] && parts[2] !== '' && parts[2] !== 'null') {
                        text += ` <a href="${parts[2]}" target="_blank" class="doc-link">[공문확인]</a>`;
                    }
                    
                    li.innerHTML = text;
                    modalSupportsList.appendChild(li);
                });
                modalSupportsSection.style.display = 'block';
            } else {
                modalSupportsSection.style.display = 'none';
            }

                if(!d.synopsis) document.getElementById('film-modal-synopsis-section').style.display = 'none';
                else document.getElementById('film-modal-synopsis-section').style.display = 'block';


            modalGalleryGrid.innerHTML = '';
            if(d.filmGalleries && d.filmGalleries.length > 0) {
                const galleries = d.filmGalleries.split('||');
                galleries.forEach(gStr => {
                    const parts = gStr.split('::');
                    const imgDiv = document.createElement('div');
                    imgDiv.className = 'modal-gallery-item';
                    const img = document.createElement('img');
                    img.src = parts[0];
                    if(parts[1] && parts[1] !== 'null') img.alt = parts[1];
                    imgDiv.appendChild(img);
                    modalGalleryGrid.appendChild(imgDiv);
                });
                modalGallerySection.style.display = 'block';
            } else {
                modalGallerySection.style.display = 'none';
            }

            modalVideoContainer.innerHTML = '';
            if(d.filmVideos && d.filmVideos.length > 0) {
                const videos = d.filmVideos.split('||');
                videos.forEach(vStr => {
                    const parts = vStr.split('::');
                    const wrapper = document.createElement('div');
                    wrapper.className = 'modal-video-wrapper';
                    
                    const iframe = document.createElement('iframe');
                    iframe.src = `https://www.youtube.com/embed/${parts[1]}`;
                    iframe.setAttribute('frameborder', '0');
                    iframe.setAttribute('allowfullscreen', 'true');
                    
                    const title = document.createElement('h5');
                    title.textContent = parts[0];
                    
                    wrapper.appendChild(iframe);
                    wrapper.appendChild(title);
                    modalVideoContainer.appendChild(wrapper);
                });
                modalVideoSection.style.display = 'block';
            } else {
                modalVideoSection.style.display = 'none';
            }

            filmModal.classList.add('active');
            document.body.style.overflow = 'hidden';
        });
    });

    modalCloses.forEach(close => {
        close.addEventListener('click', () => {
            galleryModal.classList.remove('active');
            filmModal.classList.remove('active');
            document.body.style.overflow = '';
            
            setTimeout(() => {
                if(galleryModalImg) galleryModalImg.src = '';
                if(modalPoster) modalPoster.src = '';
                modalVideoContainer.innerHTML = '';
            }, 300);
        });
    });

    window.addEventListener('click', (e) => {
        if (e.target === galleryModal) {
            galleryModal.classList.remove('active');
            document.body.style.overflow = '';
        }
        if (e.target === filmModal) {
            filmModal.classList.remove('active');
            document.body.style.overflow = '';
            modalVideoContainer.innerHTML = ''; 
        }
    });
});