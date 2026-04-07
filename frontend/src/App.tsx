import { BrowserRouter, Routes, Route } from 'react-router-dom';
import { Provider } from 'react-redux';
import { store } from './state/store';

import Layout from './components/layout/Layout';
import HomePage from './components/layout/HomePage';
import LoginPage from './components/auth/LoginPage';
import RegisterPage from './components/auth/RegisterPage';
import BusinessListPage from './components/business/BusinessListPage';
import BusinessDetailPage from './components/business/BusinessDetailPage';
import BusinessDashboard from './components/business/BusinessDashboard';
import ProductListPage from './components/product/ProductListPage';
import OrderListPage from './components/order/OrderListPage';
import ChatPage from './components/chat/ChatPage';
import ClassifiedListPage from './components/classifieds/ClassifiedListPage';
import JobListPage from './components/jobs/JobListPage';
import AnnouncementListPage from './components/announcements/AnnouncementListPage';

export default function App() {
  return (
    <Provider store={store}>
      <BrowserRouter>
        <Routes>
          <Route element={<Layout />}>
            <Route path="/" element={<HomePage />} />
            <Route path="/login" element={<LoginPage />} />
            <Route path="/register" element={<RegisterPage />} />
            <Route path="/businesses" element={<BusinessListPage />} />
            <Route path="/businesses/:id" element={<BusinessDetailPage />} />
            <Route path="/dashboard" element={<BusinessDashboard />} />
            <Route path="/businesses/:id/products" element={<ProductListPage />} />
            <Route path="/orders" element={<OrderListPage />} />
            <Route path="/chat" element={<ChatPage />} />
            <Route path="/classifieds" element={<ClassifiedListPage />} />
            <Route path="/jobs" element={<JobListPage />} />
            <Route path="/announcements" element={<AnnouncementListPage />} />
          </Route>
        </Routes>
      </BrowserRouter>
    </Provider>
  );
}
