

 // Find all start-chat buttons
    const chatButtons = document.querySelectorAll('.start-chat');

  // Find chat box where messages appear
    const chatBox = document.querySelector('.chat-box');

    const chatInput  = document.getElementById('messageInput');
    const sendButton = document.getElementById('sendButton');
  
    let stompClient = null;
    let body = null;
    let businessName = null
    let payload = null;

  // Add click event to each button
    chatButtons.forEach(button => {
      button.addEventListener('click', function() {
        
        businessName = this.getAttribute('data-business');
        
        button.disabled = true;

        // 1) Create the SockJS socket
      const socket = new SockJS("/ws");

      // 2) Wrap it in a STOMP client
      stompClient = Stomp.over(socket);

      // 3) Connect to the server
      stompClient.connect(
        {}, 
        function (frame) {
          console.log("Connected: " + frame);

          // 4) Subscribe to your personal queue
          stompClient.subscribe("/user/queue/messages", function (msg) {
            body = JSON.parse(msg.body);
            console.log("BODDDDDY", body.sender)
            
          });
        },
        function (error) {
          console.error("STOMP error:", error);
          // Re-enable button so user can retry
          document.getElementById(".start-chat").disabled = false;
        }
      );

      // Optional: Scroll chat down after message
        chatBox.scrollTop = chatBox.scrollHeight;
      });



      sendButton.addEventListener('click', () => {

        // 3. Read and trim the message text
        const text = chatInput.value.trim();
        if (!text) return; // do nothing on empty input
    

        if (body === null){
          payload = {
            recipient: businessName,        // assume you set `username` earlier
            content:  text,
            // for private messaging you might also include `recipient`
          };

        }else{
          payload = {
            recipient: body.sender,        // assume you set `username` earlier
            content:  text,
            // for private messaging you might also include `recipient`
          };
        }


      
    
        // 5. Send via STOMP to the server endpoint
        stompClient.send("/app/chat.private", {}, JSON.stringify(payload));
    
        // 6. Clear the input for the next message
        chatInput.value = '';
      });





    });
