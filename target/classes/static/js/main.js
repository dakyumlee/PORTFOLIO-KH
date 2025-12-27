document.addEventListener('DOMContentLoaded', () => {
    const navItems = document.querySelectorAll('.nav-item');
    const pages = document.querySelectorAll('.page');
    const sidebar = document.querySelector('.sidebar');
    const hamburger = document.getElementById('hamburger');

    function navigate(page) {
        navItems.forEach(item => item.classList.remove('active'));
        pages.forEach(p => p.classList.remove('active'));
        
        const activeNav = document.querySelector('[data-page="' + page + '"]');
        const activePage = document.getElementById('page-' + page);
        
        if (activeNav) activeNav.classList.add('active');
        if (activePage) activePage.classList.add('active');

        if (sidebar && sidebar.classList.contains('active')) {
            sidebar.classList.remove('active');
            if (hamburger) hamburger.classList.remove('active');
        }

        loadPageData(page);
    }

    async function loadPageData(page) {
        try {
            switch(page) {
                case 'dashboard':
                    const dashData = await fetch('/api/dashboard').then(r => r.json());
                    renderDashboard(dashData);
                    break;
                case 'projects':
                    const projData = await fetch('/api/projects').then(r => r.json());
                    renderProjects(projData);
                    break;
                case 'process':
                    const procData = await fetch('/api/process').then(r => r.json());
                    renderProcess(procData);
                    break;
                case 'notes':
                    const notesData = await fetch('/api/notes').then(r => r.json());
                    renderNotes(notesData);
                    break;
                case 'contact':
                    const contactData = await fetch('/api/contact').then(r => r.json());
                    renderContact(contactData);
                    break;
            }
        } catch (err) {
            console.error('Error loading page data:', err);
        }
    }

    function renderDashboard(data) {
        const activeContainer = document.getElementById('active-projects');
        const upcomingContainer = document.getElementById('upcoming-projects');

        if (data.activeProjects && data.activeProjects.length > 0) {
            let html = '';
            data.activeProjects.forEach(p => {
                html += '<div class="project-card" data-id="' + p.id + '">';
                html += '<div class="project-card-header"><div>';
                html += '<div class="project-card-title">' + p.title + '</div>';
                html += '<div class="project-card-tags">';
                if (p.format) html += '<span class="tag format">' + p.format + '</span>';
                if (p.role) html += '<span class="tag">' + p.role + '</span>';
                html += '</div></div>';
                html += '<span class="status-badge ' + p.status.toLowerCase() + '">' + formatStatus(p.status) + '</span>';
                html += '</div>';
                html += '<p class="project-card-desc">' + (p.synopsis || '') + '</p>';
                html += '</div>';
            });
            activeContainer.innerHTML = html;
        } else {
            activeContainer.innerHTML = '<div class="empty-state">진행 중인 프로젝트가 없습니다</div>';
        }

        if (data.upcomingProjects && data.upcomingProjects.length > 0) {
            let html = '';
            data.upcomingProjects.forEach(p => {
                html += '<div class="upcoming-item">';
                html += '<span class="upcoming-item-title">' + p.title + '</span>';
                html += '<span class="upcoming-item-meta">' + (p.format || '') + ' ' + (p.year || '') + '</span>';
                html += '</div>';
            });
            upcomingContainer.innerHTML = html;
        } else {
            upcomingContainer.innerHTML = '<div class="empty-state">예정된 프로젝트가 없습니다</div>';
        }
    }

    function renderProjects(data) {
        const listContainer = document.getElementById('projects-list');
        const detailContainer = document.getElementById('project-detail');

        if (data.projects && data.projects.length > 0) {
            let html = '';
            data.projects.forEach(p => {
                html += '<div class="project-list-item" data-id="' + p.id + '">';
                html += '<div class="project-list-header">';
                html += '<span class="project-list-title">' + p.title + '</span>';
                html += '<span class="project-list-year">' + (p.year || '') + '</span>';
                html += '</div>';
                html += '<p class="project-list-summary">' + (p.synopsis || '') + '</p>';
                html += '<div class="project-list-tags">';
                if (p.format) html += '<span class="small-tag">' + p.format + '</span>';
                if (p.genre) html += '<span class="small-tag">' + p.genre + '</span>';
                html += '</div></div>';
            });
            listContainer.innerHTML = html;

            document.querySelectorAll('.project-list-item').forEach(item => {
                item.addEventListener('click', () => {
                    document.querySelectorAll('.project-list-item').forEach(i => i.classList.remove('active'));
                    item.classList.add('active');
                    const project = data.projects.find(p => p.id == item.dataset.id);
                    renderProjectDetail(project);
                });
            });
        } else {
            listContainer.innerHTML = '<div class="empty-state">등록된 프로젝트가 없습니다</div>';
        }

        detailContainer.innerHTML = '<div class="detail-placeholder"><svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1"><path d="M22 19a2 2 0 0 1-2 2H4a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h5l2 3h9a2 2 0 0 1 2 2z"></path></svg><p>프로젝트를 선택하세요</p></div>';
    }

    function renderProjectDetail(project) {
        const container = document.getElementById('project-detail');
        
        let html = '<div class="detail-content">';
        html += '<div class="detail-header">';
        html += '<h3 class="detail-title">' + project.title + '</h3>';
        html += '<div class="detail-meta">';
        if (project.year) html += '<span>' + project.year + '</span>';
        if (project.format) html += '<span>' + project.format + '</span>';
        if (project.runtime) html += '<span>' + project.runtime + '</span>';
        html += '</div>';
        html += '<div class="project-card-tags" style="margin-top: 14px;">';
        if (project.role) html += '<span class="tag">' + project.role + '</span>';
        if (project.status) html += '<span class="status-badge ' + project.status.toLowerCase() + '">' + formatStatus(project.status) + '</span>';
        html += '</div></div>';

        if (project.synopsis) {
            html += '<div class="detail-section">';
            html += '<h4 class="detail-section-title">시놉시스</h4>';
            html += '<p>' + project.synopsis + '</p>';
            html += '</div>';
        }

        if (project.director) {
            html += '<div class="detail-section">';
            html += '<h4 class="detail-section-title">감독</h4>';
            html += '<p>' + project.director + '</p>';
            html += '</div>';
        }

        if (project.genre) {
            html += '<div class="detail-section">';
            html += '<h4 class="detail-section-title">장르</h4>';
            html += '<p>' + project.genre + '</p>';
            html += '</div>';
        }

        html += '</div>';
        container.innerHTML = html;
    }

    function renderProcess(data) {
        const container = document.getElementById('process-timeline');

        if (data.steps && data.steps.length > 0) {
            let html = '';
            data.steps.forEach(step => {
                html += '<div class="timeline-item">';
                html += '<div class="timeline-dot"></div>';
                html += '<div class="timeline-number">' + String(step.stepNumber).padStart(2, '0') + '</div>';
                html += '<h3 class="timeline-title">' + step.title + '</h3>';
                html += '<p class="timeline-desc">' + (step.description || '') + '</p>';
                html += '</div>';
            });
            container.innerHTML = html;
        } else {
            container.innerHTML = '<div class="empty-state">등록된 프로세스가 없습니다</div>';
        }
    }

    function renderNotes(data) {
        const filterContainer = document.getElementById('notes-filter');
        const listContainer = document.getElementById('notes-list');

        const tags = ['전체'];
        if (data.notes) {
            data.notes.forEach(n => {
                if (n.tags) {
                    n.tags.split(',').forEach(t => {
                        const tag = t.trim();
                        if (tag && tags.indexOf(tag) === -1) tags.push(tag);
                    });
                }
            });
        }

        let filterHtml = '';
        tags.forEach((tag, i) => {
            filterHtml += '<button class="filter-btn' + (i === 0 ? ' active' : '') + '" data-tag="' + tag + '">' + tag + '</button>';
        });
        filterContainer.innerHTML = filterHtml;

        filterContainer.querySelectorAll('.filter-btn').forEach(btn => {
            btn.addEventListener('click', () => {
                filterContainer.querySelectorAll('.filter-btn').forEach(b => b.classList.remove('active'));
                btn.classList.add('active');
                filterNotes(data.notes, btn.dataset.tag);
            });
        });

        renderNotesList(data.notes);
    }

    function filterNotes(notes, tag) {
        if (tag === '전체') {
            renderNotesList(notes);
        } else {
            const filtered = notes.filter(n => n.tags && n.tags.includes(tag));
            renderNotesList(filtered);
        }
    }

    function renderNotesList(notes) {
        const container = document.getElementById('notes-list');

        if (notes && notes.length > 0) {
            let html = '';
            notes.forEach(note => {
                html += '<div class="note-card">';
                html += '<div class="note-header">';
                html += '<span class="note-date">' + (note.noteDate || '') + '</span>';
                if (note.tags) html += '<span class="note-project">' + note.tags + '</span>';
                html += '</div>';
                html += '<h3 class="note-title">' + note.title + '</h3>';
                html += '<p class="note-content">' + (note.content || '') + '</p>';
                html += '</div>';
            });
            container.innerHTML = html;
        } else {
            container.innerHTML = '<div class="empty-state">등록된 노트가 없습니다</div>';
        }
    }

    function renderContact(data) {
        const container = document.getElementById('contact-content');
        const profile = data.profile || {};

        let html = '<p class="contact-intro">';
        html += profile.bio || '프로젝트 관련 문의나 협업 제안을 환영합니다.';
        html += '</p>';
        
        html += '<div class="contact-form">';
        html += '<h3 class="contact-form-title">메시지 보내기</h3>';
        html += '<form id="contactForm">';
        html += '<div class="form-row">';
        html += '<div class="form-group"><label>이름</label><input type="text" name="name" placeholder="홍길동" required></div>';
        html += '<div class="form-group"><label>이메일</label><input type="email" name="email" placeholder="example@email.com" required></div>';
        html += '</div>';
        html += '<div class="form-group"><label>제목</label><input type="text" name="subject" placeholder="문의 제목"></div>';
        html += '<div class="form-group"><label>내용</label><textarea name="message" placeholder="문의 내용을 입력해주세요" required></textarea></div>';
        html += '<div class="form-submit"><button type="submit" class="contact-btn"><svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5"><line x1="22" y1="2" x2="11" y2="13"></line><polygon points="22 2 15 22 11 13 2 9 22 2"></polygon></svg>보내기</button></div>';
        html += '</form></div>';

        html += '<div class="contact-divider">또는</div>';

        html += '<div class="contact-buttons">';
        if (profile.email) {
            html += '<a href="mailto:' + profile.email + '" class="contact-btn secondary">';
            html += '<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5"><path d="M4 4h16c1.1 0 2 .9 2 2v12c0 1.1-.9 2-2 2H4c-1.1 0-2-.9-2-2V6c0-1.1.9-2 2-2z"></path><polyline points="22,6 12,13 2,6"></polyline></svg>';
            html += profile.email + '</a>';
        }
        if (profile.instagram) {
            html += '<a href="https://instagram.com/' + profile.instagram + '" target="_blank" class="contact-btn secondary">';
            html += '<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5"><rect x="2" y="2" width="20" height="20" rx="5" ry="5"></rect><path d="M16 11.37A4 4 0 1 1 12.63 8 4 4 0 0 1 16 11.37z"></path><line x1="17.5" y1="6.5" x2="17.51" y2="6.5"></line></svg>';
            html += '@' + profile.instagram + '</a>';
        }
        html += '</div>';

        container.innerHTML = html;

        const form = document.getElementById('contactForm');
        if (form && profile.email) {
            form.addEventListener('submit', function(e) {
                e.preventDefault();
                const formData = new FormData(form);
                const name = formData.get('name');
                const email = formData.get('email');
                const subject = formData.get('subject') || '포트폴리오 문의';
                const message = formData.get('message');
                const body = '이름: ' + name + '\n이메일: ' + email + '\n\n' + message;
                window.location.href = 'mailto:' + profile.email + '?subject=' + encodeURIComponent(subject) + '&body=' + encodeURIComponent(body);
            });
        }
    }

    function formatStatus(status) {
        const map = {
            'PREP': 'Prep',
            'SHOOTING': 'Shooting',
            'POST': 'Post',
            'RELEASED': 'Released'
        };
        return map[status] || status;
    }

    function handleRoute() {
        const hash = window.location.hash.slice(2) || 'dashboard';
        navigate(hash);
    }

    navItems.forEach(item => {
        item.addEventListener('click', function(e) {
            e.preventDefault();
            const page = this.dataset.page;
            window.location.hash = '/' + page;
        });
    });

    if (hamburger) {
        hamburger.addEventListener('click', function() {
            if (sidebar) sidebar.classList.toggle('active');
            this.classList.toggle('active');
        });
    }

    window.addEventListener('hashchange', handleRoute);
    handleRoute();
});
