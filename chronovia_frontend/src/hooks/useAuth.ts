import { useState, useEffect } from 'react';
import { authAPI } from '../services/api.ts';

interface AuthUser {
  id: number;
  email: string;
  role: string;
}

export const useAuth = () => {
  const [user, setUser] = useState<AuthUser | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const currentUser = authAPI.getCurrentUser();
    setUser(currentUser);
    setLoading(false);
  }, []);

  const login = (token: string) => {

    authAPI.setToken(token);
    const currentUser = authAPI.getCurrentUser();
    setUser(currentUser);
  };

  const logout = () => {
    authAPI.removeToken();
    setUser(null);
  };

  const isAuthenticated = !!user;
  const isAdmin = user?.role === 'ADMIN';

  return {
    user,
    loading,
    login,
    signOut:logout,
    isAuthenticated,
    isAdmin,
  };
};