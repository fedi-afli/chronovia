import axios from 'axios';
import type {AuthResponse, SignUpRequest, SignInRequest, MessageResponse} from '../types/auth';

const API_BASE_URL = 'http://localhost:8080/api';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Add token to requests if available
api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

export const authService = {
  signUp: async (data: SignUpRequest): Promise<MessageResponse> => {
    const response = await api.post('/auth/signup', data);
    return response.data;
  },

  signIn: async (data: SignInRequest): Promise<AuthResponse> => {
    const response = await api.post('/auth/signin', data);
    return response.data;
  },

  verifyEmail: async (token: string): Promise<MessageResponse> => {
    const response = await api.post('/auth/verify-email', { token });
    return response.data;
  },

  resendVerification: async (email: string): Promise<MessageResponse> => {
    const response = await api.post(`/auth/resend-verification?email=${email}`);
    return response.data;
  },
  requestPasswordReset: async (email: string): Promise<MessageResponse> => {
    const response = await api.post(`/auth/request-password-reset?email=${email}`);
    return response.data;
  },

  resetPassword: async (data: { token: string; newPassword: string }): Promise<MessageResponse> => {
    const response = await api.post('/auth/reset-password', data);
    return response.data;
  }

};