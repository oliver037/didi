document.addEventListener('DOMContentLoaded', function() {
    // 监听键盘事件
    document.addEventListener('keydown', function(e) {
        // 当按下 Backspace 或 Alt + 左箭头时返回上一页
        if (e.key === 'Backspace' || (e.altKey && e.key === 'ArrowLeft')) {
            e.preventDefault(); // 防止浏览器默认行为
            window.location.href = 'brands.html';
        }
    });

    // 为所有产品卡片添加点击事件
    document.querySelectorAll('.product-card').forEach(card => {
        card.addEventListener('click', function(e) {
            // 如果点击的是按钮，不进行跳转
            if (e.target.closest('.product-action')) {
                return;
            }
            // 获取跳转链接并执行跳转
            const href = this.dataset.href;
            if (href) {
                window.location.href = href;
            }
        });
    });

    // 阻止按钮点击事件冒泡
    document.querySelectorAll('.product-action button').forEach(button => {
        button.addEventListener('click', (e) => {
            e.stopPropagation();
        });
    });
});

// 声浪试听功能
function playExhaustSound(model) {
    const audio = new Audio(`audio/${model}-exhaust.mp3`);
    audio.play();
}

// 性能对比功能
function showComparison(model) {
    // 实现性能对比弹窗或跳转
    window.location.href = `product-comparison-${model}.html`;
} 