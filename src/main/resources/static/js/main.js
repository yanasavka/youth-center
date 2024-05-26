function likePost(postId) {
    fetch(`/users/ userId /posts`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ postId: postId })
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            // Оновлюємо значення totalLikes на сторінці
            const postElement = document.getElementById(`post-${postId}`);
            const totalLikesElement = postElement.querySelector('.total-likes');

            // Збільшуємо лічильник лайків
            totalLikesElement.innerText = parseInt(totalLikesElement.innerText) + 1;
        })
        .catch(error => {
            console.error('There was a problem with your fetch operation:', error);
        });
}

function showAlert(){
    alert("Hello!")
}
document.addEventListener('DOMContentLoaded', function() {
    const queryInput = document.getElementById('query');
    const usersContainer = document.getElementById('users');

    queryInput.addEventListener('input', function(event) {
        const query = event.target.value.trim();
        if (query === '') {
            // Якщо поле вводу порожнє, покажемо усіх користувачів
            showAllUsers();
        } else {
            // В іншому випадку виконаємо пошук за текстом
            searchUsers(query);
        }
    });

    function showAllUsers() {
        // Показати усіх користувачів (без фільтрації)
        usersContainer.querySelectorAll('.user').forEach(function(user) {
            user.style.display = 'block';
        });
    }

    function searchUsers(query) {
        // Пошук користувачів за текстом
        usersContainer.querySelectorAll('.user').forEach(function(user) {
            const name = user.querySelector('.name').innerText.toLowerCase();
            const surname = user.querySelector('.surname').innerText.toLowerCase();
            if (name.includes(query.toLowerCase()) || surname.includes(query.toLowerCase())) {
                // Якщо ім'я або прізвище користувача містить введений текст, покажемо його
                user.style.display = 'block';
            } else {
                // Інакше приховуємо користувача
                user.style.display = 'none';
            }
        });
    }
});

// const input = document.getElementById('query');
// const userList = document.getElementById('users');
//
// input.addEventListener('keyup', async (event) => {
//     const query = event.target.value;
//     if (query.length > 0) {
//         try {
//             const response = await fetch('/my/users', {
//                 method: 'GET',
//                 headers: {'Content-Type': 'application/json'},
//                 params: {query}
//             });
//             const data = await response.json();
//             userList.innerHTML = data; // Update user list with received data
//         } catch (error) {
//             console.error('Error fetching search results:', error);
//             // Handle errors gracefully (e.g., display an error message to the user)
//         }
//     } else {
//         userList.innerHTML = ''; // Clear user list if query is empty
//     }
// });