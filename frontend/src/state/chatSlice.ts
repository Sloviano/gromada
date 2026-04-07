import { createSlice, createAsyncThunk, PayloadAction } from '@reduxjs/toolkit';
import { ChatConversationResponse, ChatMessageResponse } from '../types';
import { chatApi } from '../services/api';

interface ChatState {
  conversations: ChatConversationResponse[];
  activeConversationId: number | null;
  messages: ChatMessageResponse[];
  unreadCount: number;
  loading: boolean;
}

const initialState: ChatState = {
  conversations: [],
  activeConversationId: null,
  messages: [],
  unreadCount: 0,
  loading: false,
};

export const fetchConversations = createAsyncThunk('chat/fetchConversations', async (userId: number) => {
  const response = await chatApi.getConversations(userId);
  return response.data;
});

export const fetchMessages = createAsyncThunk('chat/fetchMessages', async (conversationId: number) => {
  const response = await chatApi.getMessages(conversationId);
  return response.data;
});

export const fetchUnreadCount = createAsyncThunk('chat/fetchUnreadCount', async (userId: number) => {
  const response = await chatApi.getUnreadCount(userId);
  return response.data;
});

const chatSlice = createSlice({
  name: 'chat',
  initialState,
  reducers: {
    setActiveConversation(state, action: PayloadAction<number>) {
      state.activeConversationId = action.payload;
    },
    addMessage(state, action: PayloadAction<ChatMessageResponse>) {
      const msg = action.payload;
      // Add to messages if it belongs to active conversation
      if (msg.conversationId === state.activeConversationId) {
        const exists = state.messages.some((m) => m.id === msg.id);
        if (!exists) {
          state.messages.push(msg);
        }
      }
      // Update conversation's last message
      const conv = state.conversations.find((c) => c.id === msg.conversationId);
      if (conv) {
        conv.lastMessage = msg.content;
        conv.lastMessageAt = msg.sentAt;
      }
    },
    clearMessages(state) {
      state.messages = [];
      state.activeConversationId = null;
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(fetchConversations.fulfilled, (state, action) => {
        state.conversations = action.payload;
      })
      .addCase(fetchMessages.pending, (state) => { state.loading = true; })
      .addCase(fetchMessages.fulfilled, (state, action) => {
        state.loading = false;
        state.messages = action.payload;
      })
      .addCase(fetchUnreadCount.fulfilled, (state, action) => {
        state.unreadCount = action.payload;
      });
  },
});

export const { setActiveConversation, addMessage, clearMessages } = chatSlice.actions;
export default chatSlice.reducer;
