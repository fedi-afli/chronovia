import axios from 'axios';
import type { AuthResponse, SignUpRequest, SignInRequest, MessageResponse } from '../types/auth';

const API_BASE_URL = 'http://localhost:8080/api';

// Initialize Axios instance
const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Add Authorization header if token exists
api.interceptors.request.use((config) => {
  const token = localStorage.getItem('jwt_token'); // use consistent key
  if (token && config.headers) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

export const authService = {
  // Sign up
  signUp: async (data: SignUpRequest): Promise<MessageResponse> => {

    const response = await api.post('/auth/signup', data);
    return response.data;
  },

  // Sign in and handle token
      signIn: async (data: SignInRequest): Promise<AuthResponse> => {
        const response = await api.post('/auth/signin', data);

        console.log("✅ Backend login response:", response.data); // Debug

        // Store token in localStorage
        if (response.data.token) {
          localStorage.setItem('token', response.data.token);
          console.log("🔐 Token saved:", response.data.token);
        } else {
          console.warn("❌ No token received");
        }

        return response.data;
      },

  // Email verification
  verifyEmail: async (token: string): Promise<MessageResponse> => {
    const response = await api.post('/auth/verify-email', { token });
    return response.data;
  },

  // Resend verification email
  resendVerification: async (email: string): Promise<MessageResponse> => {
    const response = await api.post(`/auth/resend-verification?email=${email}`);
    return response.data;
  },

  // Request password reset
  requestPasswordReset: async (email: string): Promise<MessageResponse> => {
    const response = await api.post(`/auth/request-password-reset?email=${email}`);
    return response.data;
  },

  // Reset password
  resetPassword: async (data: { token: string; newPassword: string }): Promise<MessageResponse> => {
    const response = await api.post('/auth/reset-password', data);
    return response.data;
  },
};
