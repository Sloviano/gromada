// ─── Enums ───

export enum UserRole {
  CITIZEN = 'CITIZEN',
  BUSINESS_OWNER = 'BUSINESS_OWNER',
  ADMIN = 'ADMIN',
}

export enum OrderStatus {
  PENDING = 'PENDING',
  CONFIRMED = 'CONFIRMED',
  PREPARING = 'PREPARING',
  OUT_FOR_DELIVERY = 'OUT_FOR_DELIVERY',
  DELIVERED = 'DELIVERED',
  CANCELLED = 'CANCELLED',
}

export enum AdCampaignStatus {
  DRAFT = 'DRAFT',
  ACTIVE = 'ACTIVE',
  PAUSED = 'PAUSED',
  COMPLETED = 'COMPLETED',
}

export enum SellOutEventStatus {
  SCHEDULED = 'SCHEDULED',
  ACTIVE = 'ACTIVE',
  ENDED = 'ENDED',
  CANCELLED = 'CANCELLED',
}

export enum JobPostingStatus {
  OPEN = 'OPEN',
  FILLED = 'FILLED',
  CLOSED = 'CLOSED',
}

export enum AnnouncementType {
  GENERAL = 'GENERAL',
  EVENT = 'EVENT',
  ALERT = 'ALERT',
  NEWS = 'NEWS',
}

// ─── Response Types ───

export interface UserResponse {
  id: number;
  username: string;
  email: string;
  fullName: string;
  phone: string;
  role: UserRole;
  createdAt: string;
  active: boolean;
}

export interface BusinessResponse {
  id: number;
  name: string;
  description: string;
  category: string;
  phone: string;
  location: string;
  rating: number;
  likes: number;
  dislikes: number;
  imageUrl: string;
  operatingHours: string;
  verified: boolean;
  ownerId: number;
  ownerName: string;
  productCount: number;
  createdAt: string;
}

export interface ProductResponse {
  id: number;
  name: string;
  description: string;
  price: number;
  discountPrice: number | null;
  stockQuantity: number;
  unit: string;
  imageUrl: string;
  available: boolean;
  businessId: number;
  businessName: string;
  categoryId: number;
  categoryName: string;
}

export interface OrderResponse {
  id: number;
  status: OrderStatus;
  totalAmount: number;
  deliveryAddress: string;
  notes: string;
  customerId: number;
  customerName: string;
  businessId: number;
  businessName: string;
  items: OrderItemResponse[];
  createdAt: string;
  deliveredAt: string | null;
}

export interface OrderItemResponse {
  id: number;
  productId: number;
  productName: string;
  quantity: number;
  unitPrice: number;
  subtotal: number;
}

export interface ChatConversationResponse {
  id: number;
  participantOneId: number;
  participantOneName: string;
  participantTwoId: number;
  participantTwoName: string;
  businessId: number | null;
  businessName: string | null;
  lastMessage: string;
  lastMessageAt: string;
  unreadCount: number;
}

export interface ChatMessageResponse {
  id: number;
  conversationId: number;
  senderId: number;
  senderName: string;
  recipientId: number;
  recipientName: string;
  content: string;
  sentAt: string;
  read: boolean;
}

export interface AdCampaign {
  id: number;
  title: string;
  description: string;
  imageUrl: string;
  status: AdCampaignStatus;
  budget: number;
  startDate: string;
  endDate: string;
  impressions: number;
  clicks: number;
}

export interface SellOutEvent {
  id: number;
  title: string;
  description: string;
  status: SellOutEventStatus;
  discountPercent: number;
  startDate: string;
  endDate: string;
}

export interface ClassifiedAd {
  id: number;
  title: string;
  description: string;
  price: number;
  contactInfo: string;
  imageUrl: string;
  active: boolean;
  createdAt: string;
}

export interface JobPosting {
  id: number;
  title: string;
  description: string;
  company: string;
  location: string;
  salaryMin: number;
  salaryMax: number;
  contactInfo: string;
  status: JobPostingStatus;
  createdAt: string;
}

export interface Announcement {
  id: number;
  title: string;
  content: string;
  type: AnnouncementType;
  imageUrl: string;
  pinned: boolean;
  createdAt: string;
}

export interface AuthResponse {
  userId: number;
  username: string;
  role: UserRole;
  token: string;
}

// ─── Request Types ───

export interface UserRegistrationRequest {
  username: string;
  password: string;
  email: string;
  fullName: string;
  phone: string;
  role: UserRole;
}

export interface BusinessRequest {
  name: string;
  description: string;
  category: string;
  phone: string;
  location: string;
  imageUrl?: string;
  operatingHours?: string;
}

export interface ProductRequest {
  name: string;
  description: string;
  price: number;
  discountPrice?: number;
  stockQuantity: number;
  unit: string;
  imageUrl?: string;
  categoryId?: number;
}

export interface OrderRequest {
  businessId: number;
  deliveryAddress: string;
  notes?: string;
  items: { productId: number; quantity: number }[];
}

export interface ChatMessageRequest {
  conversationId: number;
  recipientId: number;
  content: string;
}

export interface ReviewRequest {
  businessId: number;
  rating: number;
  comment: string;
}
