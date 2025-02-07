document.addEventListener('DOMContentLoaded', function() {
    // 车型数据结构
    const carData = {
        german: {
            name: '德系',
            brands: {
                bmw: {
                    name: '宝马',
                    models: ['3系', '5系', '7系', 'X3', 'X5', 'M3', 'M5']
                },
                audi: {
                    name: '奥迪',
                    models: ['A4L', 'A6L', 'Q5L', 'RS6', 'RS7']
                },
                mercedes: {
                    name: '奔驰',
                    models: ['C级', 'E级', 'S级', 'GLC', 'AMG GT']
                },
                porsche: {
                    name: '保时捷',
                    models: ['911', 'Cayenne', 'Panamera', 'Macan', 'Taycan']
                }
            }
        },
        japanese: {
            name: '日系',
            brands: {
                toyota: {
                    name: '丰田',
                    models: ['卡罗拉', '凯美瑞', '普拉多', '兰德酷路泽']
                },
                honda: {
                    name: '本田',
                    models: ['思域', '雅阁', 'CR-V', '冠道']
                },
                nissan: {
                    name: '日产',
                    models: ['天籁', '轩逸', 'GT-R', '奇骏']
                }
            }
        }
    };

    const categorySelect = document.getElementById('carCategory');
    const brandSelect = document.getElementById('carBrand');
    const modelSelect = document.getElementById('carModel');

    // 车系选择变化时更新品牌选项
    categorySelect.addEventListener('change', function() {
        const category = this.value;
        brandSelect.innerHTML = '<option value="">请选择品牌</option>';
        modelSelect.innerHTML = '<option value="">请选择型号</option>';
        
        if (category) {
            const brands = carData[category].brands;
            Object.keys(brands).forEach(brandKey => {
                const option = document.createElement('option');
                option.value = brandKey;
                option.textContent = brands[brandKey].name;
                brandSelect.appendChild(option);
            });
        }
    });

    // 品牌选择变化时更新车型选项
    brandSelect.addEventListener('change', function() {
        const category = categorySelect.value;
        const brand = this.value;
        modelSelect.innerHTML = '<option value="">请选择型号</option>';
        
        if (brand) {
            const models = carData[category].brands[brand].models;
            models.forEach(model => {
                const option = document.createElement('option');
                option.value = model;
                option.textContent = model;
                modelSelect.appendChild(option);
            });
        }
    });

    // 模拟车型数据
    const carBrands = {
        'toyota': ['卡罗拉', '凯美瑞', '普拉多'],
        'honda': ['思域', '雅阁', 'CR-V'],
        'bmw': ['3系', '5系', 'X5'],
        // 可以继续添加更多品牌和车型
    };

    // 合法改装配置数据
    const legalMods = {
        exterior: {
            items: ['车身贴膜', '轮毂改装', '包围套件'],
            restrictions: '不得改变车身主体结构'
        },
        interior: {
            items: ['座椅改装', '方向盘更换', '内饰升级'],
            restrictions: '不得改变安全气囊系统'
        },
        performance: {
            items: ['进气系统', '排气系统', 'ECU调教'],
            restrictions: '须符合环保标准'
        }
    };

    // 初始化表单处理
    const modForm = document.getElementById('modForm');
    modForm.addEventListener('submit', function(e) {
        e.preventDefault();
        generateRecommendation();
    });

    function generateRecommendation() {
        // 获取表单数据
        const formData = new FormData(modForm);
        const selectedMods = formData.getAll('modType');
        const budget = formData.get('budget');

        // 这里可以添加推荐逻辑
        console.log('Selected modifications:', selectedMods);
        console.log('Budget:', budget);

        // TODO: 实现具体的推荐算法
    }

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

    // 添加退出功能
    document.getElementById('logoutBtn')?.addEventListener('click', function(e) {
        e.preventDefault();
        localStorage.removeItem('isLoggedIn');
        localStorage.removeItem('username');
        localStorage.removeItem('userType');
        updateUserStatus();
    });

    // 初始化用户状态
    updateUserStatus();

    // 检查URL中是否包含#featured-cases
    if (window.location.hash === '#featured-cases') {
        // 获取案例部分的元素
        const casesSection = document.getElementById('featured-cases');
        if (casesSection) {
            // 滚动到案例部分
            casesSection.scrollIntoView({ behavior: 'smooth' });
        }
    }
}); 