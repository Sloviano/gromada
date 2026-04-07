import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import { BusinessResponse } from '../types';
import { businessApi } from '../services/api';

interface BusinessState {
  businesses: BusinessResponse[];
  selectedBusiness: BusinessResponse | null;
  categories: string[];
  loading: boolean;
  error: string | null;
}

const initialState: BusinessState = {
  businesses: [],
  selectedBusiness: null,
  categories: [],
  loading: false,
  error: null,
};

export const fetchBusinesses = createAsyncThunk('business/fetchAll', async () => {
  const response = await businessApi.getAll();
  return response.data;
});

export const fetchBusinessById = createAsyncThunk('business/fetchById', async (id: number) => {
  const response = await businessApi.getById(id);
  return response.data;
});

export const searchBusinesses = createAsyncThunk('business/search', async (keyword: string) => {
  const response = await businessApi.search(keyword);
  return response.data;
});

export const fetchCategories = createAsyncThunk('business/fetchCategories', async () => {
  const response = await businessApi.getCategories();
  return response.data;
});

const businessSlice = createSlice({
  name: 'business',
  initialState,
  reducers: {
    clearSelectedBusiness(state) {
      state.selectedBusiness = null;
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(fetchBusinesses.pending, (state) => { state.loading = true; })
      .addCase(fetchBusinesses.fulfilled, (state, action) => {
        state.loading = false;
        state.businesses = action.payload;
      })
      .addCase(fetchBusinesses.rejected, (state, action) => {
        state.loading = false;
        state.error = action.error.message || 'Failed to fetch businesses';
      })
      .addCase(fetchBusinessById.fulfilled, (state, action) => {
        state.selectedBusiness = action.payload;
      })
      .addCase(searchBusinesses.fulfilled, (state, action) => {
        state.businesses = action.payload;
      })
      .addCase(fetchCategories.fulfilled, (state, action) => {
        state.categories = action.payload;
      });
  },
});

export const { clearSelectedBusiness } = businessSlice.actions;
export default businessSlice.reducer;
