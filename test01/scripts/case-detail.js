document.addEventListener('DOMContentLoaded', function() {
    // 图片画廊功能
    const mainImage = document.getElementById('mainImage');
    const thumbnails = document.querySelectorAll('.thumbnail');

    thumbnails.forEach(thumbnail => {
        thumbnail.addEventListener('click', function() {
            // 更新主图
            mainImage.src = this.src;
            
            // 更新缩略图状态
            thumbnails.forEach(thumb => thumb.classList.remove('active'));
            this.classList.add('active');
        });
    });

    // 更新用户状态
    function updateUserStatus() {
        const isLoggedIn = localStorage.getItem('isLoggedIn') === 'true';
        const username = localStorage.getItem('username');
        const userType = localStorage.getItem('userType');
        
        const loginLink = document.getElementById('loginLink');
        const registerLink = document.getElementById('registerLink');
        const userInfo = document.getElementById('userInfo');
        const usernameSpan = document.getElementById('username');
        
        if (isLoggedIn && username) {
            loginLink.style.display = 'none';
            registerLink.style.display = 'none';
            userInfo.style.display = 'inline';
            usernameSpan.textContent = userType === 'admin' ? `管理员 ${username}` : username;
        } else {
            loginLink.style.display = 'inline';
            registerLink.style.display = 'inline';
            userInfo.style.display = 'none';
        }
    }

    // 初始化用户状态
    updateUserStatus();
}); 