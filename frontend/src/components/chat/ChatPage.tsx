import { useEffect, useState, useRef } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { AppDispatch, RootState } from '../../state/store';
import { fetchConversations, fetchMessages, setActiveConversation, addMessage } from '../../state/chatSlice';
import { wsService } from '../../services/websocket';
import { ChatMessageResponse } from '../../types';

export default function ChatPage() {
  const dispatch = useDispatch<AppDispatch>();
  const { user } = useSelector((state: RootState) => state.auth);
  const { conversations, activeConversationId, messages } = useSelector((state: RootState) => state.chat);
  const [newMessage, setNewMessage] = useState('');
  const messagesEndRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    if (user?.id) {
      dispatch(fetchConversations(user.id));
      wsService.connect(user.id);

      const unsubscribe = wsService.onMessage((msg: ChatMessageResponse) => {
        dispatch(addMessage(msg));
      });

      return () => {
        unsubscribe();
        wsService.disconnect();
      };
    }
  }, [user, dispatch]);

  useEffect(() => {
    messagesEndRef.current?.scrollIntoView({ behavior: 'smooth' });
  }, [messages]);

  const openConversation = (convId: number) => {
    dispatch(setActiveConversation(convId));
    dispatch(fetchMessages(convId));
    if (user?.id) wsService.markAsRead(convId);
  };

  const sendMessage = () => {
    if (!newMessage.trim() || !activeConversationId || !user?.id) return;
    const conv = conversations.find((c) => c.id === activeConversationId);
    if (!conv) return;

    const recipientId = conv.participantOneId === user.id ? conv.participantTwoId : conv.participantOneId;
    wsService.sendMessage(activeConversationId, recipientId, newMessage);
    setNewMessage('');
  };

  return (
    <div className="chat-page">
      <div className="conversation-list">
        <h3>Розмови</h3>
        {conversations.map((conv) => {
          const otherName = conv.participantOneId === user?.id ? conv.participantTwoName : conv.participantOneName;
          return (
            <div
              key={conv.id}
              className={`conversation-item ${conv.id === activeConversationId ? 'active' : ''}`}
              onClick={() => openConversation(conv.id)}
            >
              <strong>{otherName}</strong>
              {conv.businessName && <span className="business-tag">{conv.businessName}</span>}
              <p className="last-message">{conv.lastMessage}</p>
              {conv.unreadCount > 0 && <span className="unread-badge">{conv.unreadCount}</span>}
            </div>
          );
        })}
      </div>

      <div className="message-area">
        {activeConversationId ? (
          <>
            <div className="messages">
              {messages.map((msg) => (
                <div key={msg.id} className={`message ${msg.senderId === user?.id ? 'sent' : 'received'}`}>
                  <strong>{msg.senderName}</strong>
                  <p>{msg.content}</p>
                  <small>{new Date(msg.sentAt).toLocaleTimeString()}</small>
                </div>
              ))}
              <div ref={messagesEndRef} />
            </div>
            <div className="message-input">
              <input
                value={newMessage}
                onChange={(e) => setNewMessage(e.target.value)}
                onKeyDown={(e) => e.key === 'Enter' && sendMessage()}
                placeholder="Введіть повідомлення..."
              />
              <button onClick={sendMessage}>Надіслати</button>
            </div>
          </>
        ) : (
          <p className="no-conversation">Оберіть розмову зліва</p>
        )}
      </div>
    </div>
  );
}
