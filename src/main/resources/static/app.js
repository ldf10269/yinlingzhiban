document.addEventListener('DOMContentLoaded', () => {
    // 页面元素选择器
    const chatSection = document.getElementById('chat-section');
    const remindersSection = document.getElementById('reminders-section');
    const chatNav = document.getElementById('chat-nav');
    const remindersNav = document.getElementById('reminders-nav');
    const messageInput = document.getElementById('message-input');
    const sendBtn = document.getElementById('send-btn');
    const chatMessages = document.getElementById('chat-messages');
    const remindersList = document.getElementById('reminders-list');
    const addReminderBtn = document.getElementById('add-reminder-btn');
    const saveReminderBtn = document.getElementById('save-reminder-btn');
    const addReminderModal = new bootstrap.Modal(document.getElementById('add-reminder-modal'));
    const logoutBtn = document.getElementById('logout-btn');

    // 获取当前登录用户信息
    function getCurrentUser() {
        return fetch('/api/users/current')
            .then(response => response.json())
            .then(user => {
                document.getElementById('username').textContent = user.username;
                return user.id;
            })
            .catch(error => {
                console.error('获取用户信息失败:', error);
                return 1; // 默认返回用户ID 1
            });
    }

    let USER_ID;
    getCurrentUser().then(id => {
        USER_ID = id;
        loadReminders(); // 初始加载提醒
    });

    // 退出登录
    logoutBtn.addEventListener('click', () => {
        fetch('/logout', { method: 'POST' })
            .then(() => {
                window.location.href = '/login.html';
            })
            .catch(error => {
                console.error('退出登录失败:', error);
            });
    });

    // 导航切换
    chatNav.addEventListener('click', () => {
        chatSection.style.display = 'block';
        remindersSection.style.display = 'none';
        chatNav.classList.add('active');
        remindersNav.classList.remove('active');
    });

    remindersNav.addEventListener('click', () => {
        chatSection.style.display = 'none';
        remindersSection.style.display = 'block';
        remindersNav.classList.add('active');
        chatNav.classList.remove('active');
        loadReminders();
    });

    // 发送消息
    function sendMessage() {
        const message = messageInput.value.trim();
        if (message) {
            addMessageToChatWindow('user', message);
            
            fetch(`/api/chat/send?userId=${USER_ID}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(message)
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error('网络响应异常');
                }
                return response.text();
            })
            .then(data => {
                addMessageToChatWindow('bot', data || '抱歉，我暂时无法回复');
            })
            .catch(error => {
                console.error('聊天错误:', error);
                addMessageToChatWindow('bot', '对不起，网络或服务异常，请稍后重试。');
            });

            messageInput.value = '';
        }
    }

    sendBtn.addEventListener('click', sendMessage);
    messageInput.addEventListener('keypress', (e) => {
        if (e.key === 'Enter') sendMessage();
    });

    function addMessageToChatWindow(sender, message) {
        const messageElement = document.createElement('div');
        messageElement.classList.add('message', sender);
        
        // 添加更多样式和安全处理
        const safeMessage = message ? 
            message.replace(/</g, '&lt;').replace(/>/g, '&gt;') : 
            '无效消息';
        
        messageElement.innerHTML = `
            <div class="message-content">
                <span class="sender-icon">${sender === 'user' ? '👤' : '🤖'}</span>
                <span class="message-text">${safeMessage}</span>
            </div>
        `;
        
        chatMessages.appendChild(messageElement);
        chatMessages.scrollTop = chatMessages.scrollHeight;
    }

    // 提醒功能
    function loadReminders() {
        fetch(`/api/reminders/user?userId=${USER_ID}`)
            .then(response => response.json())
            .then(reminders => {
                remindersList.innerHTML = '';
                reminders.forEach(reminder => {
                    const li = document.createElement('li');
                    li.classList.add('list-group-item');
                    li.innerHTML = `
                        <div>
                            <strong>${reminder.title}</strong>
                            <p class="text-muted mb-0">${reminder.description || '无描述'}</p>
                            <small>${new Date(reminder.dueDate).toLocaleString()}</small>
                        </div>
                        <div>
                            <button class="btn btn-sm btn-outline-primary me-2 edit-reminder" data-id="${reminder.id}">编辑</button>
                            <button class="btn btn-sm btn-outline-danger complete-reminder" data-id="${reminder.id}">完成</button>
                        </div>
                    `;
                    remindersList.appendChild(li);
                });
            })
            .catch(error => {
                console.error('Error:', error);
                remindersList.innerHTML = '<li class="list-group-item">加载提醒失败</li>';
            });
    }

    // 添加提醒
    addReminderBtn.addEventListener('click', () => {
        addReminderModal.show();
    });

    saveReminderBtn.addEventListener('click', () => {
        const title = document.getElementById('reminder-title').value;
        const description = document.getElementById('reminder-description').value;
        const dueDate = document.getElementById('reminder-date').value;

        if (title && dueDate) {
            fetch(`/api/reminders/create?userId=${USER_ID}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: `title=${encodeURIComponent(title)}&description=${encodeURIComponent(description)}&dueDate=${encodeURIComponent(dueDate)}`
            })
            .then(response => response.json())
            .then(data => {
                addReminderModal.hide();
                loadReminders();
            })
            .catch(error => {
                console.error('Error:', error);
                alert('添加提醒失败');
            });
        }
    });
});
