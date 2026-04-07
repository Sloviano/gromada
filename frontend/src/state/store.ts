import { configureStore } from '@reduxjs/toolkit';
import authReducer from './authSlice';
import businessReducer from './businessSlice';
import chatReducer from './chatSlice';

export const store = configureStore({
  reducer: {
    auth: authReducer,
    business: businessReducer,
    chat: chatReducer,
  },
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
