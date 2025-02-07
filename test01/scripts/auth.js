document.addEventListener('DOMContentLoaded', function() {
    const loginForm = document.getElementById('loginForm');
    const registerForm = document.getElementById('registerForm');

    if (loginForm) {
        loginForm.addEventListener('submit', function(e) {
            e.preventDefault();
            const username = this.username.value;
            const password = this.password.value;
            const remember = this.remember?.checked;
            const userType = this.userType.value; // 获取用户类型

            // 这里添加登录逻辑
            console.log('Login attempt:', { username, password, remember, userType });
            
            // 模拟登录成功
            localStorage.setItem('isLoggedIn', 'true');
            localStorage.setItem('username', username);
            localStorage.setItem('userType', userType); // 保存用户类型
            
            // 根据用户类型跳转到不同页面
            if (userType === 'admin') {
                window.location.href = 'admin/dashboard.html'; // 管理员跳转到管理面板
            } else {
                window.location.href = 'index.html'; // 普通用户跳转到首页
            }
        });
    }

    if (registerForm) {
        registerForm.addEventListener('submit', function(e) {
            e.preventDefault();
            const username = this.username.value;
            const email = this.email.value;
            const password = this.password.value;
            const confirmPassword = this.confirmPassword.value;

            // 验证密码
            if (password !== confirmPassword) {
                alert('两次输入的密码不一致！');
                return;
            }

            // 这里添加注册逻辑
            console.log('Register attempt:', { username, email, password });
            
            // 模拟注册成功
            localStorage.setItem('isLoggedIn', 'true');
            localStorage.setItem('username', username);
            
            // 跳转到首页
            window.location.href = 'index.html';
        });
    }
}); 