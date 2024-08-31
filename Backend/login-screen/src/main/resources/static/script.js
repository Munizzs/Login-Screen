document.addEventListener('DOMContentLoaded', function() {
    loadUsers();

    const userForm = document.getElementById('userForm');
    userForm.addEventListener('submit', saveUser);
});

function loadUsers() {
    fetch('/admin/user')
        .then(response => response.json())
        .then(users => {
            const userTable = document.getElementById('users');
            userTable.innerHTML = '';

            users.forEach(user => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${user.id}</td>
                    <td>${user.name}</td>
                    <td>${user.roles.map(role => role.name).join(', ')}</td>
                    <td>
                        <button onclick="editUser(${user.id})">Edit</button>
                        <button onclick="deleteUser(${user.id})">Delete</button>
                    </td>
                `;
                userTable.appendChild(row);
            });
        });
}

function saveUser(event) {
    event.preventDefault();

    const userId = document.getElementById('userId').value;
    const name = document.getElementById('name').value;
    const password = document.getElementById('password').value;
    const enable = document.getElementById('enable').checked;
    const roles = Array.from(document.getElementById('roles').selectedOptions)
        .map(option => ({ id: option.value }));

    const user = {
        name,
        password,
        enable,
        roles
    };

    const method = userId ? 'PUT' : 'POST';
    const url = userId ? `/admin/user/${userId}` : '/admin/user';

    fetch(url, {
        method,
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(user)
    }).then(() => {
        loadUsers();
        userForm.reset();
    });
}

function editUser(id) {
    fetch(`/admin/user/${id}`)
        .then(response => response.json())
        .then(user => {
            document.getElementById('userId').value = user.id;
            document.getElementById('name').value = user.name;
            document.getElementById('password').value = ''; // Deixe o campo de senha em branco
            document.getElementById('enable').checked = user.enable;

            // Seleciona as roles do usuário
            const rolesSelect = document.getElementById('roles');
            Array.from(rolesSelect.options).forEach(option => {
                option.selected = user.roles.some(role => role.id == option.value);
            });
        });
}

function deleteUser(id) {
    fetch(`/admin/user/${id}`, {
        method: 'DELETE'
    }).then(() => loadUsers());
}
