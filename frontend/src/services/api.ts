import axios from 'axios';

const API_BASE = import.meta.env.VITE_API_BASE || 'http://localhost:8080';

const api = axios.create({
  baseURL: `${API_BASE}/api`,
  headers: { 'Content-Type': 'application/json' },
  withCredentials: true,
});

// Attach auth token if available
api.interceptors.request.use((config) => {
  const token = localStorage.getItem('auth_token');
  if (token) {
    config.headers.Authorization = `Basic ${token}`;
  }
  return config;
});

export default api;

// ─── Auth ───
export const authApi = {
  register: (data: any) => api.post('/auth/register', data),
  login: (username: string, password: string) => {
    const token = btoa(`${username}:${password}`);
    localStorage.setItem('auth_token', token);
    return api.get('/users', { headers: { Authorization: `Basic ${token}` } });
  },
  logout: () => {
    localStorage.removeItem('auth_token');
  },
};

// ─── Users ───
export const userApi = {
  getAll: () => api.get('/users'),
  getById: (id: number) => api.get(`/users/${id}`),
  update: (id: number, data: any) => api.put(`/users/${id}`, data),
};

// ─── Businesses ───
export const businessApi = {
  getAll: () => api.get('/businesses'),
  getById: (id: number) => api.get(`/businesses/${id}`),
  create: (ownerId: number, data: any) => api.post(`/businesses?ownerId=${ownerId}`, data),
  update: (id: number, data: any) => api.put(`/businesses/${id}`, data),
  delete: (id: number) => api.delete(`/businesses/${id}`),
  search: (keyword: string) => api.get(`/businesses/search?keyword=${keyword}`),
  getByCategory: (category: string) => api.get(`/businesses/category/${category}`),
  getCategories: () => api.get('/businesses/categories'),
  like: (id: number) => api.post(`/businesses/${id}/like`),
  dislike: (id: number) => api.post(`/businesses/${id}/dislike`),
  getByOwner: (ownerId: number) => api.get(`/businesses/owner/${ownerId}`),
};

// ─── Products ───
export const productApi = {
  getById: (id: number) => api.get(`/products/${id}`),
  getByBusiness: (businessId: number) => api.get(`/products/business/${businessId}`),
  getAvailable: (businessId: number) => api.get(`/products/business/${businessId}/available`),
  create: (businessId: number, data: any) => api.post(`/products/business/${businessId}`, data),
  update: (id: number, data: any) => api.put(`/products/${id}`, data),
  delete: (id: number) => api.delete(`/products/${id}`),
  search: (keyword: string) => api.get(`/products/search?keyword=${keyword}`),
  updateStock: (id: number, quantity: number) => api.patch(`/products/${id}/stock?quantity=${quantity}`),
};

// ─── Orders ───
export const orderApi = {
  create: (customerId: number, data: any) => api.post(`/orders?customerId=${customerId}`, data),
  getById: (id: number) => api.get(`/orders/${id}`),
  getByCustomer: (customerId: number) => api.get(`/orders/customer/${customerId}`),
  getByBusiness: (businessId: number) => api.get(`/orders/business/${businessId}`),
  updateStatus: (id: number, status: string) => api.patch(`/orders/${id}/status?status=${status}`),
  cancel: (id: number) => api.post(`/orders/${id}/cancel`),
};

// ─── Ad Campaigns ───
export const adCampaignApi = {
  create: (businessId: number, data: any) => api.post(`/ad-campaigns/business/${businessId}`, data),
  getById: (id: number) => api.get(`/ad-campaigns/${id}`),
  getByBusiness: (businessId: number) => api.get(`/ad-campaigns/business/${businessId}`),
  getActive: () => api.get('/ad-campaigns/active'),
  updateStatus: (id: number, status: string) => api.patch(`/ad-campaigns/${id}/status?status=${status}`),
  delete: (id: number) => api.delete(`/ad-campaigns/${id}`),
};

// ─── Sell-Out Events ───
export const sellOutEventApi = {
  create: (businessId: number, data: any) => api.post(`/sell-out-events/business/${businessId}`, data),
  getById: (id: number) => api.get(`/sell-out-events/${id}`),
  getByBusiness: (businessId: number) => api.get(`/sell-out-events/business/${businessId}`),
  getActive: () => api.get('/sell-out-events/active'),
  updateStatus: (id: number, status: string) => api.patch(`/sell-out-events/${id}/status?status=${status}`),
  delete: (id: number) => api.delete(`/sell-out-events/${id}`),
};

// ─── Classifieds ───
export const classifiedApi = {
  getAll: () => api.get('/classifieds'),
  getById: (id: number) => api.get(`/classifieds/${id}`),
  create: (authorId: number, data: any) => api.post(`/classifieds?authorId=${authorId}`, data),
  update: (id: number, data: any) => api.put(`/classifieds/${id}`, data),
  delete: (id: number) => api.delete(`/classifieds/${id}`),
  getByCategory: (categoryId: number) => api.get(`/classifieds/category/${categoryId}`),
  search: (keyword: string) => api.get(`/classifieds/search?keyword=${keyword}`),
};

// ─── Jobs ───
export const jobApi = {
  getOpen: () => api.get('/jobs'),
  getById: (id: number) => api.get(`/jobs/${id}`),
  create: (userId: number, data: any) => api.post(`/jobs?userId=${userId}`, data),
  updateStatus: (id: number, status: string) => api.patch(`/jobs/${id}/status?status=${status}`),
  delete: (id: number) => api.delete(`/jobs/${id}`),
  search: (keyword: string) => api.get(`/jobs/search?keyword=${keyword}`),
};

// ─── Announcements ───
export const announcementApi = {
  getAll: () => api.get('/announcements'),
  getById: (id: number) => api.get(`/announcements/${id}`),
  create: (authorId: number, data: any) => api.post(`/announcements?authorId=${authorId}`, data),
  update: (id: number, data: any) => api.put(`/announcements/${id}`, data),
  delete: (id: number) => api.delete(`/announcements/${id}`),
  getPinned: () => api.get('/announcements/pinned'),
};

// ─── Chat (REST) ───
export const chatApi = {
  getConversations: (userId: number) => api.get(`/chat/conversations?userId=${userId}`),
  getOrCreate: (userOneId: number, userTwoId: number, businessId?: number) =>
    api.post(`/chat/conversations?userOneId=${userOneId}&userTwoId=${userTwoId}${businessId ? `&businessId=${businessId}` : ''}`),
  getMessages: (conversationId: number) => api.get(`/chat/conversations/${conversationId}/messages`),
  markAsRead: (conversationId: number, userId: number) =>
    api.post(`/chat/conversations/${conversationId}/read?userId=${userId}`),
  getUnreadCount: (userId: number) => api.get(`/chat/unread-count?userId=${userId}`),
};

// ─── Reviews ───
export const reviewApi = {
  create: (authorId: number, data: any) => api.post(`/reviews?authorId=${authorId}`, data),
  getByBusiness: (businessId: number) => api.get(`/reviews/business/${businessId}`),
  getAverageRating: (businessId: number) => api.get(`/reviews/business/${businessId}/rating`),
  delete: (id: number) => api.delete(`/reviews/${id}`),
};
