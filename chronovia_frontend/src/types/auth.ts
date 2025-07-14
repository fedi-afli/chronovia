export interface User {
  id: number;
  username: string;
  email: string;
  role: string;
  emailVerified: boolean;
}

export interface AuthResponse {
  token: string;
  type: string;
  userId: number;
  username: string;
  email: string;
  role: string;
  emailVerified: boolean;
}

export interface SignUpRequest {
  username: string;
  email: string;
  password: string;
}

export interface SignInRequest {
  emailOrUsername: string;
  password: string;
}

export interface MessageResponse {
  message: string;
  success: boolean;
}