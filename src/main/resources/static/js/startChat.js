
 
 
 // Find all start-chat buttons
    const chatButtons = document.querySelectorAll('.start-chat');

  // Find chat box where messages appear
    const chatBox = document.querySelector('.chat-box');

  // Add click event to each button
    chatButtons.forEach(button => {
      button.addEventListener('click', function() {
        const businessName = this.getAttribute('data-business');
        const message = document.createElement('p');
        message.textContent = `Ви почали спілкування з ${businessName}`;
        chatBox.appendChild(message);

      // Optional: Scroll chat down after message
        chatBox.scrollTop = chatBox.scrollHeight;
      });
    });
