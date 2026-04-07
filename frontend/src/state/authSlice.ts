import { createSlice, createAsyncThunk, PayloadAction } from '@reduxjs/toolkit';
import { UserResponse, UserRole } from '../types';
import { authApi } from '../services/api';

interface AuthState {
  user: UserResponse | null;
  isAuthenticated: boolean;
  loading: boolean;
  error: string | null;
}

const initialState: AuthState = {
  user: null,
  isAuthenticated: false,
  loading: false,
  error: null,
};

export const register = createAsyncThunk(
  'auth/register',
  async (data: { username: string; password: string; email: string; fullName: string; phone: string; role: UserRole }) => {
    const response = await authApi.register(data);
    return response.data;
  }
);

export const login = createAsyncThunk(
  'auth/login',
  async ({ username, password }: { username: string; password: string }) => {
    const response = await authApi.login(username, password);
    return response.data;
  }
);

const authSlice = createSlice({
  name: 'auth',
  initialState,
  reducers: {
    logout(state) {
      authApi.logout();
      state.user = null;
      state.isAuthenticated = false;
    },
    setUser(state, action: PayloadAction<UserResponse>) {
      state.user = action.payload;
      state.isAuthenticated = true;
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(register.pending, (state) => { state.loading = true; state.error = null; })
      .addCase(register.fulfilled, (state, action) => {
        state.loading = false;
        state.user = action.payload;
        state.isAuthenticated = true;
      })
      .addCase(register.rejected, (state, action) => {
        state.loading = false;
        state.error = action.error.message || 'Registration failed';
      })
      .addCase(login.pending, (state) => { state.loading = true; state.error = null; })
      .addCase(login.fulfilled, (state) => {
        state.loading = false;
        state.isAuthenticated = true;
      })
      .addCase(login.rejected, (state, action) => {
        state.loading = false;
        state.error = action.error.message || 'Login failed';
      });
  },
});

export const { logout, setUser } = authSlice.actions;
export default authSlice.reducer;
