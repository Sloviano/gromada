import { Client, IMessage } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import { ChatMessageResponse } from '../types';

const WS_URL = import.meta.env.VITE_WS_URL || 'http://localhost:8080/ws';

class WebSocketService {
  private client: Client | null = null;
  private messageHandlers: ((message: ChatMessageResponse) => void)[] = [];

  connect(userId: number): void {
    this.client = new Client({
      webSocketFactory: () => new SockJS(WS_URL),
      reconnectDelay: 5000,
      heartbeatIncoming: 10000,
      heartbeatOutgoing: 10000,
      onConnect: () => {
        console.log('WebSocket connected');

        // Subscribe to personal message queue
        this.client?.subscribe(`/user/${userId}/queue/messages`, (frame: IMessage) => {
          const message: ChatMessageResponse = JSON.parse(frame.body);
          this.messageHandlers.forEach((handler) => handler(message));
        });
      },
      onStompError: (frame) => {
        console.error('WebSocket STOMP error:', frame.headers['message']);
      },
    });

    this.client.activate();
  }

  disconnect(): void {
    if (this.client?.active) {
      this.client.deactivate();
    }
    this.messageHandlers = [];
  }

  sendMessage(conversationId: number, recipientId: number, content: string): void {
    if (!this.client?.active) {
      console.error('WebSocket not connected');
      return;
    }

    this.client.publish({
      destination: '/app/chat.private',
      body: JSON.stringify({ conversationId, recipientId, content }),
    });
  }

  markAsRead(conversationId: number): void {
    if (!this.client?.active) return;

    this.client.publish({
      destination: '/app/chat.read',
      body: JSON.stringify(conversationId),
    });
  }

  onMessage(handler: (message: ChatMessageResponse) => void): () => void {
    this.messageHandlers.push(handler);
    return () => {
      this.messageHandlers = this.messageHandlers.filter((h) => h !== handler);
    };
  }
}

export const wsService = new WebSocketService();
