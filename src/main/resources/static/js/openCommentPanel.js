


const commentsPanel = document.createElement('div');
commentsPanel.id = 'comments-panel';
commentsPanel.innerHTML = `
  <h3>Додати коментар</h3>
  <textarea placeholder="Ваш коментар..." style="width: 100%; height: 150px;"></textarea><br><br>
  <button onclick="submitComment()">Надіслати</button>
  <button onclick="closeComments()">Закрити</button>
`;
document.body.appendChild(commentsPanel);


// Open panel when clicking comment button
document.querySelectorAll('.comment-button').forEach(button => {
    button.addEventListener('click', () => {
      document.getElementById('comments-panel').classList.add('open');
    });
  });
  
  
  // Close panel
  function closeComments() {
    document.getElementById('comments-panel').classList.remove('open');
  }