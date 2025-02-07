document.addEventListener('DOMContentLoaded', function() {
    const filterButtons = document.querySelectorAll('.filter-btn');
    const brandCards = document.querySelectorAll('.brand-card');

    filterButtons.forEach(button => {
        button.addEventListener('click', () => {
            // 更新按钮状态
            filterButtons.forEach(btn => btn.classList.remove('active'));
            button.classList.add('active');

            // 获取选中的类别
            const selectedCategory = button.dataset.category;

            // 筛选品牌卡片
            brandCards.forEach(card => {
                if (selectedCategory === 'all' || card.dataset.category === selectedCategory) {
                    card.style.display = 'block';
                } else {
                    card.style.display = 'none';
                }
            });
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

    updateUserStatus();
}); 