import type {
  CartLignRequestDTO,
  Order,
  OrderRequestDTO,
  Picture,
  User,
  Watch,
  WatchSearchRecord,
  Cart,
} from "../types";

const API_BASE_URL = "http://localhost:8080/api";

const TOKEN_KEY = "jwt_token";

// Get JWT token from localStorage
const getAuthToken = (): string | null => {
  return localStorage.getItem(TOKEN_KEY);
};

// Create headers with authentication if needed
const getHeaders = (includeAuth = true): HeadersInit => {
  const headers: HeadersInit = {
    "Content-Type": "application/json",
  };

  if (includeAuth) {
    const token = getAuthToken();
    if (token) {
      headers.Authorization = `Bearer ${token}`;
    }
  }

  return headers;
};

// Generic API request function with error handling
const apiRequest = async <T>(
    endpoint: string,
    options: RequestInit = {}
): Promise<T> => {
  const response = await fetch(`${API_BASE_URL}${endpoint}`, {
    ...options,
    headers: {
      ...getHeaders(),
      ...options.headers,
    },
  });

  if (!response.ok) {
    // You can customize error handling here, e.g., parse JSON error response
    throw new Error(`API Error: ${response.status} ${response.statusText}`);
  }

  return response.json();
};

// Watch API Services
export const watchAPI = {
  getAllWatches: () => apiRequest<Watch[]>("/get/watch", { method: "GET" }),

  getWatchById: (watchId: number) =>
      apiRequest<Watch>(`/get/watch/${watchId}`, { method: "GET" }),

  searchWatches: (filter: WatchSearchRecord) =>
      apiRequest<Watch[]>("/search/watch", {
        method: "POST",
        body: JSON.stringify(filter),
      }),

  getWatchPictures: (watchId: number) =>
      apiRequest<Picture[]>(`/get/watch/picture/${watchId}`, { method: "GET" }),

  saveWatch: (watch: Watch) =>
      apiRequest<Watch>("/save/watch", {
        method: "POST",
        body: JSON.stringify(watch),
      }),

  deleteWatch: (watchId: number) =>
      apiRequest<boolean>(`/delete/watch/${watchId}`, { method: "DELETE" }),
};

// Cart API Services
export const cartAPI = {
  getCart: (userId: number) =>
      apiRequest<Cart>(`/get/cart/${userId}`, { method: "GET" }),

  addToCart: (userId: number, cartItem: CartLignRequestDTO) =>
      apiRequest<Cart>(`/add/product/${userId}`, {
        method: "POST",
        body: JSON.stringify(cartItem),
      }),
};

// Order API Services
export const orderAPI = {
  confirmOrder: (order: OrderRequestDTO) =>
      apiRequest<Order>("/order/confirm", {
        method: "POST",
        body: JSON.stringify(order),
      }),

  getUserOrders: (userId: number) =>
      apiRequest<Order[]>(`/orders/${userId}`, { method: "POST" }),

  confirmCart: (userId: number) =>
      apiRequest<Order>(`/order/confirm/${userId}`, { method: "GET" }),
};

// User API Services
export const userAPI = {
  getAllUsers: () => apiRequest<User[]>("/get/user", { method: "GET" }),

  getUserById: (userId: number) =>
      apiRequest<User>(`/search/user/${userId}`, { method: "GET" }),

  searchUsers: (filter: any) =>
      apiRequest<User[]>("/search/users", {
        method: "POST",
        body: JSON.stringify(filter),
      }),

  deleteUser: (userId: number) =>
      apiRequest<boolean>(`/delete/user/${userId}`, { method: "GET" }),
};

// Auth helper functions
export const authAPI = {
  setToken: (token: string) => {
    localStorage.clear()
    localStorage.setItem(TOKEN_KEY, token);
  },

  getToken: () => {
    return localStorage.getItem(TOKEN_KEY);
  },

  removeToken: () => {
    localStorage.clear()
  },

  getCurrentUser: (): User | null => {
    const token = getAuthToken();
    if (!token) return null;

    try {
      // Decode JWT payload (assumes base64url)
      const base64Url = token.split(".")[1];
      const base64 = base64Url.replace(/-/g, "+").replace(/_/g, "/");
      const jsonPayload = decodeURIComponent(
          atob(base64)
              .split("")
              .map((c) => "%" + ("00" + c.charCodeAt(0).toString(16)).slice(-2))
              .join("")
      );
      const payload = JSON.parse(jsonPayload);

      // Map payload properties to User type fields - adjust if your token uses different keys
      return {
        id: payload.id ?? 0,
        username: payload.username ?? '',
        email: payload.sub ?? '',
        role: payload.role ?? 'USER',  // adjust 'USER' default to your UserRole enum default
        enabled: payload.enabled ?? true,
        createdAt: payload.createdAt ?? new Date().toISOString(),
        lastLoginAt: payload.lastLoginAt ?? new Date().toISOString(),
      };
    } catch (error) {
      console.error("Error parsing token:", error);
      return null;
    }
  },
};

export default {
  watchAPI,
  cartAPI,
  orderAPI,
  userAPI,
  authAPI,
};
