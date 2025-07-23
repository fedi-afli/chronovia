import type {
  CartLignRequestDTO,
  Order,
  OrderRequestDTO,
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
const getHeaders = (includeAuth = true, hasBody = false): HeadersInit => {
  const headers: HeadersInit = {};

  if (hasBody) {
    headers["Content-Type"] = "application/json";
  }

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
    options: RequestInit = {},
    includeAuth: boolean = true // ← option ajoutée ici pour forcer ou non l’auth
): Promise<T> => {
  const hasBody = !!options.body;

  const headers: HeadersInit = {
    ...(options.headers || {}),
    ...(hasBody ? { "Content-Type": "application/json" } : {}),
  };

  if (includeAuth) {
    const token = localStorage.getItem("jwt_token");
    if (token) {
      headers["Authorization"] = `Bearer ${token}`;
    }
  }

  const response = await fetch(`${API_BASE_URL}${endpoint}`, {
    ...options,
    headers,
  });

  console.log(`[API] ${options.method || 'GET'} ${endpoint}`, { headers });

  if (!response.ok) {
    const errorText = await response.text();
    console.error(`API Error: ${response.status}`, errorText);
    throw new Error(`API Error: ${response.status} ${response.statusText}`);
  }

  return response.json();
};



// Watch API Services
export const watchAPI = {
  getAllWatches: () => apiRequest<Watch[]>("/get/watch", { method: "GET" }),

  getWatchById: (watchId: number) =>
      apiRequest<Watch>(`/search/watch/${watchId}`, { method: "GET" }),

  searchWatches: (filter: WatchSearchRecord) =>
      apiRequest<Watch[]>("/search/watch", {
        method: "POST",
        body: JSON.stringify(filter),
      }),

  getWatchPictures: (watchId: number, options?: RequestInit) =>
      apiRequest<string[]>(`/get/watch/picture/${watchId}`, {
        method: "GET",
        ...options
      }),


// Save the watch data without images
  saveWatchData: (watchData: object) => {
    const token = getAuthToken();

    return fetch(`${API_BASE_URL}/save/watch`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        ...(token ? { Authorization: `Bearer ${token}` } : {}),
      },
      body: JSON.stringify(watchData),
    }).then(async (res) => {
      if (!res.ok) throw new Error(`API Error: ${res.status}`);
      return res.json(); // Assuming this returns the saved watch with an ID
    });
  },

// Upload images for a specific watchId
  uploadWatchPictures: (watchId: number, files: FileList) => {
    const token = getAuthToken();
    const formData = new FormData();

    Array.from(files).forEach(file => {
      formData.append('images', file);
    });

    return fetch(`${API_BASE_URL}/save/watch/${watchId}/images`, {
      method: 'POST',
      headers: {
        ...(token ? { Authorization: `Bearer ${token}` } : {}),
        // Note: No 'Content-Type' header here, let browser set it
      },
      body: formData,
    }).then(async (res) => {
      if (!res.ok) throw new Error(`API Error: ${res.status}`);
      return res.json();
    });
  },


  deleteWatch: (watchId: number) =>
      apiRequest<boolean>(`/delete/watch/${watchId}`, { method: "DELETE" }),
};

// Cart API Services
export const cartAPI = {
  getCart: async (userId: number): Promise<Cart> => {
    const raw = await apiRequest<any>(`/get/cart/${userId}`, { method: "GET" });

    return {
      cartItems: raw.cartLigns ?? [], // ← Fix mapping
      total: raw.total ?? 0,
      userID: raw.userID,
    };
  },

  addToCart: async (userId: number, cartItem: CartLignRequestDTO): Promise<Cart> => {
    const raw = await apiRequest<any>(`/add/product/${userId}`, {
      method: "POST",
      body: JSON.stringify(cartItem),
    });

    return {
      cartItems: raw.cartLigns ?? [],
      total: raw.total ?? 0,
      userID: raw.userID,
    };
  },
  updateQuantity: async (userId: number, cartItem: CartLignRequestDTO): Promise<void> => {
    await apiRequest(`/cart/product-quatity/${userId}`, {
      method: "POST",
      body: JSON.stringify(cartItem),
    });
  },

  removeItem: async (userId: number, cartItem: CartLignRequestDTO): Promise<void> => {
    await apiRequest(`/remove/product/${userId}`, {
      method: "POST",
      body: JSON.stringify(cartItem),
    });
  },

};

// Order API Services


export const orderAPI = {
  confirmOrder: (order: OrderRequestDTO) =>
      apiRequest<OrderRequestDTO>("/order/confirm", {
        method: "POST",
        body: JSON.stringify(order),
      }),

  getUserOrders: (userId: number) =>
      apiRequest<OrderRequestDTO[]>(`/orders/${userId}`, { method: "POST" }),  // keep POST if backend requires

  confirmCart: (userId: number) =>
      apiRequest<OrderRequestDTO>(`/order/confirm/${userId}`, { method: "GET" }),
};



// User API Services
export const userAPI = {
  getAllUsers: () => apiRequest<User[]>("/get/user", { method: "GET" }),

  getUserById: (userId: number) =>
      apiRequest<User>(`/search/user/${userId}`, { method: "GET" }),

  searchUsers: (filter: User) =>
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
      console.log("payload :"+payload);
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
