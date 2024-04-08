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