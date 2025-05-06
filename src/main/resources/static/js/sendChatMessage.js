document.addEventListener('DOMContentLoaded', () => {
    // 1. Grab the input and button inside .chat-input
    const chatInput  = document.getElementById('messageInput');
    const sendButton = document.getElementById('sendButton');
  
    // 2. Add a click handler to the button
    sendButton.addEventListener('click', () => {

      // 3. Read and trim the message text
      const text = chatInput.value.trim();
      if (!text) return; // do nothing on empty input
  
    //   // 4. Build the payload (adjust fields to match your ChatMessage model)
      const payload = {
        recipient: "alice",        // assume you set `username` earlier
        content:  text,
        // for private messaging you might also include `recipient`
      };
  
      // 5. Send via STOMP to the server endpoint
      stompClient.send("/app/chat.send", {}, JSON.stringify(payload));
  
      // 6. Clear the input for the next message
      chatInput.value = '';
    });
  });