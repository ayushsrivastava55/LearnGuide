// Global state
let enrolledCourses = [];

// Initialize the application
document.addEventListener('DOMContentLoaded', () => {
    loadEnrolledCourses();
});

async function generateCourse() {
    const topic = document.getElementById('topic').value;
    const difficulty = document.getElementById('difficulty').value;
    const generateButton = document.querySelector('.generator-form button');

    if (!topic) {
        showNotification('Please enter a topic', 'error');
        return;
    }

    try {
        generateButton.disabled = true;
        generateButton.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Generating...';

        const controller = new AbortController();
        const timeoutId = setTimeout(() => controller.abort(), 30000); // 30 second timeout

        const response = await fetch('/api/courses/generate', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                topic,
                difficulty,
                userId: 'guest'
            }),
            signal: controller.signal
        });

        clearTimeout(timeoutId);
        
        const data = await response.json();
        
        if (!response.ok) {
            throw new Error(data.error || 'Failed to generate course');
        }

        displayNewCourse(data);
        showNotification('Course generated successfully!', 'success');
    } catch (error) {
        console.error('Error generating course:', error);
        showNotification(
            error.name === 'AbortError' 
                ? 'Request timed out. Please try again.' 
                : error.message,
            'error'
        );
    } finally {
        generateButton.disabled = false;
        generateButton.innerHTML = 'Generate Course';
    }
}

function displayNewCourse(course) {
    const courseGrid = document.getElementById('courseGrid');
    const courseCard = createCourseCard(course);
    courseGrid.prepend(courseCard);
}

function createCourseCard(course) {
    const card = document.createElement('div');
    card.className = 'course-card';
    
    let modulesHtml = '';
    if (course.modules && course.modules.length > 0) {
        modulesHtml = `
            <div class="modules-list">
                <h4>Modules:</h4>
                <ul>
                    ${course.modules.map(module => `
                        <li>
                            <strong>${module.topic}</strong> 
                            <span class="duration">${module.duration} mins</span>
                            <span class="difficulty">${module.difficulty}</span>
                        </li>
                    `).join('')}
                </ul>
            </div>
        `;
    }

    card.innerHTML = `
        <h3>${course.title}</h3>
        <p>${course.description}</p>
        <div class="course-meta">
            <span><i class="fas fa-clock"></i> ${course.estimatedDuration}h</span>
            <span><i class="fas fa-layer-group"></i> ${course.difficulty}</span>
        </div>
        ${modulesHtml}
        <div class="progress-bar">
            <div class="progress" style="width: ${course.progress || 0}%"></div>
        </div>
        <button onclick="continueCourse('${course.courseId}')" class="continue-btn">
            Continue Learning
        </button>
    `;
    return card;
}

async function continueCourse(courseId) {
    window.location.href = `/course/${courseId}`;
}

async function loadEnrolledCourses() {
    try {
        const response = await fetch('/api/courses/enrolled/guest');
        enrolledCourses = await response.json();
        displayEnrolledCourses();
    } catch (error) {
        console.error('Error loading enrolled courses:', error);
    }
}

function displayEnrolledCourses() {
    const courseGrid = document.getElementById('courseGrid');
    courseGrid.innerHTML = '';
    enrolledCourses.forEach(course => {
        const courseCard = createCourseCard(course);
        courseGrid.appendChild(courseCard);
    });
}

function showNotification(message, type) {
    const notification = document.getElementById('notification');
    notification.textContent = message;
    notification.className = `notification ${type}`;
    
    // Show notification
    setTimeout(() => notification.classList.remove('hidden'), 100);
    
    // Hide after 3 seconds
    setTimeout(() => notification.classList.add('hidden'), 3000);
} 