// Get courseId from URL
const courseId = window.location.pathname.split('/').pop();

async function loadCourseDetail() {
    try {
        const response = await fetch(`/api/courses/${courseId}`);
        const course = await response.json();
        
        displayCourseDetail(course);
    } catch (error) {
        console.error('Error loading course:', error);
        showNotification('Failed to load course details', 'error');
    }
}

function displayCourseDetail(course) {
    document.getElementById('courseTitle').textContent = course.title;
    document.getElementById('courseDescription').textContent = course.description;
    
    const modulesList = document.getElementById('modulesList');
    modulesList.innerHTML = course.modules.map(module => `
        <div class="module-item">
            <h3>${module.topic}</h3>
            <p>${module.description || ''}</p>
            <div class="module-meta">
                <span><i class="fas fa-clock"></i> ${module.duration} mins</span>
                <span><i class="fas fa-layer-group"></i> ${module.difficulty}</span>
            </div>
            <div class="resources">
                ${module.resources.map(resource => `
                    <div class="resource-item">
                        <i class="fas fa-${getResourceIcon(resource.type)}"></i>
                        <a href="${resource.url}" target="_blank">${resource.title}</a>
                    </div>
                `).join('')}
            </div>
        </div>
    `).join('');
}

function getResourceIcon(type) {
    switch (type) {
        case 'VIDEO': return 'video';
        case 'ARTICLE': return 'file-alt';
        case 'QUIZ': return 'question-circle';
        case 'EXERCISE': return 'tasks';
        default: return 'file';
    }
}

function showNotification(message, type) {
    const notification = document.getElementById('notification');
    notification.textContent = message;
    notification.className = `notification ${type}`;
    
    setTimeout(() => notification.classList.remove('hidden'), 100);
    setTimeout(() => notification.classList.add('hidden'), 3000);
}

// Load course detail when page loads
document.addEventListener('DOMContentLoaded', loadCourseDetail); 