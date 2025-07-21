// enums to mirror backend enums (adjust values as needed)
export type UserRole = 'USER' | 'ADMIN';
export type WatchMaterial = 'GOLD'| 'STAINLESS_STEEL'| 'CARBON'| 'TITANIUM'| 'CERAMIC'| 'LEATHER'|'RUBBER'
export type WatchType = 'MECHANICAL' | 'QUARTZ';

// Picture model
export interface Picture {
  id: number;
  url: string;
}

// Watch model matching WatchRequestDTO
export interface Watch {
  id: number;
  referenceNumber: string;
  price: number;
  modelName: string;
  brandName: string;
  modelDescription: string;
  caseWidth?: number | null;
  caseHeight?: number | null;
  watchMaterial: WatchMaterial;
  watchType: WatchType;
  modelYear?: number | null;
  movementCaliber: string;

  // Quartz-specific
  batteryType?: string | null;
  isSolar?: boolean | null;
  accuracy?: number | null;

  // Mechanical-specific
  isSelfWind?: boolean | null;
  powerReserveHours?: number | null;
  jewelCount?: number | null;
}

// User model matching UserRequestDTO

export interface User {
  id: number;
  username: string;
  email: string;
  role: string;
  emailVerified: boolean;
}

// Cart line item DTO - Updated to include watch details and price
export interface CartLignRequestDTO {
  watchId: number;
  quantity: number;
}

// Cart line item with full details (what we get back from API)
export interface CartItem {
  watchId: number;
  quantity: number;
  price: number;
  watch: Watch;
}

// Cart DTO - Updated to use CartItem instead of CartLignRequestDTO
export interface Cart {
  cartItems: CartItem[];
  total: number;
  userID: number;
}

// Cart request DTO for sending to backend

// Order model
export interface Order {
  id: number;
  userId: number;
  orderDate: string;
  total: number;
  orderItems: OrderItem[];
}

// Order item
export interface OrderItem {
  id: number;
  watchId: number;
  quantity: number;
  price: number;
  watch: Watch;
}

// Order DTO matching OrderRequestDTO
export interface OrderRequestDTO {
  userId: number;
  orderDate: string;  // ISO date string
  total: number;
}

export interface WatchSearchRecord {
  referenceNumber?: string;
  minPrice?: number;
  MaxPrice?: number;
  modelName?: string;
  brandName?: string;
  caseWidth?: number;
  watchMaterial?: WatchMaterial;
  watchType?: WatchType;
  modelYear?: number;
  movementCaliber?: string;
  batteryType?: string;
  isSolar?: boolean;
  accuracy?: number;
  isSelfWind?: boolean;
  powerReserveHours?: number;
  jewelCount?: number;
}

// Frontend types for user search filters
export interface UserSearchRecord {
  username?: string;
  email?: string;
}