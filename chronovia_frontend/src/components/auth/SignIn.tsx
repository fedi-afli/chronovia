import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { Mail, Lock, Eye, EyeOff } from 'lucide-react';
import { Button } from '../ui/Button';
import { Input } from '../ui/Input';
import { Alert } from '../ui/Alert';
import { authService } from '../../services/authService';
import { useAuth } from '../../context/AuthContext';
import type { SignInRequest } from '../../types/auth';
import logo from '../../assets/favicon.png';

export const SignIn: React.FC = () => {
  const navigate = useNavigate();
  const { login } = useAuth();
  const [formData, setFormData] = useState<SignInRequest>({
    emailOrUsername: '',
    password: '',
  });
  const [showPassword, setShowPassword] = useState(false);
  const [errors, setErrors] = useState<Record<string, string>>({});
  const [isLoading, setIsLoading] = useState(false);
  const [message, setMessage] = useState<{ type: 'success' | 'error'; text: string } | null>(null);

  const validateForm = (): boolean => {
    const newErrors: Record<string, string> = {};

    if (!formData.emailOrUsername.trim()) {
      newErrors.emailOrUsername = 'Email or username is required';
    }

    if (!formData.password) {
      newErrors.password = 'Password is required';
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    if (!validateForm()) return;

    setIsLoading(true);
    setMessage(null);

    try {
      const response = await authService.signIn(formData);
      login(response);

      if (!response.emailVerified) {
        navigate('/verify-email', { state: { email: response.email } });
      } else {
        navigate('/dashboard');
      }
    } catch (error: any) {
      if (error.response?.data) {
        if (typeof error.response.data === 'object' && !error.response.data.message) {
          setErrors(error.response.data);
        } else {
          setMessage({ type: 'error', text: error.response.data.message || 'Sign in failed' });
        }
      } else {
        setMessage({ type: 'error', text: 'Network error. Please try again.' });
      }
    } finally {
      setIsLoading(false);
    }
  };

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
    if (errors[name]) {
      setErrors(prev => ({ ...prev, [name]: '' }));
    }
  };

  return (
      <div className="min-h-screen bg-grid-gradient flex items-center justify-center p-4">


      <div className="max-w-md w-full">
          <div className="bg-white rounded-2xl shadow-xl p-8 border border-[#D4B072]">
            {/* Logo and Header */}
            <div className="text-center mb-8">
              <div className="mx-auto w-26 h-26 mb-4 rounded-full overflow-hidden bg-white shadow-md border border-[#D4B072]">
                <img
                    src={logo}
                    alt="Chronovia Logo"
                    className="w-full h-full object-cover"
                />
              </div>
              <h2 className="text-3xl font-bold text-[#4A3B39] mb-2">Welcome </h2>
              <p className="text-[#4A3B39]">Sign in to your Chronovia account</p>
            </div>

            {message && (
                <div className="mb-6">
                  <Alert
                      type={message.type}
                      message={message.text}
                      onClose={() => setMessage(null)}
                  />
                </div>
            )}

            <form onSubmit={handleSubmit} className="space-y-6">
              <Input
                  label="Email or Username"
                  name="emailOrUsername"
                  type="text"
                  value={formData.emailOrUsername}
                  onChange={handleInputChange}
                  error={errors.emailOrUsername}
                  icon={<Mail className="h-5 w-5 text-[#4A3B39]" />}
                  placeholder="Enter your email or username"
                  required
              />

              <div className="relative">
                <Input
                    label="Password"
                    name="password"
                    type={showPassword ? 'text' : 'password'}
                    value={formData.password}
                    onChange={handleInputChange}
                    error={errors.password}
                    icon={<Lock className="h-5 w-5 text-[#4A3B39]" />}
                    placeholder="Enter your password"
                    required
                />
                <button
                    type="button"
                    className="absolute right-3 top-8 text-[#D4B072] hover:text-[#b89356]"
                    onClick={() => setShowPassword(!showPassword)}
                >
                  {showPassword ? <EyeOff className="h-5 w-5" /> : <Eye className="h-5 w-5" />}
                </button>
              </div>

              <div className="flex items-center justify-between">
                <label className="flex items-center">
                  <input
                      type="checkbox"
                      className="rounded border-gray-300 text-[#D4B072] focus:ring-[#D4B072]"
                  />
                  <span className="ml-2 text-sm text-[#4A3B39]">Remember me</span>
                </label>
                <Link
                    to="/request-password-reset"
                    className="text-sm text-[#D4B072] hover:text-[#b89356] transition-colors"
                >
                  Forgot password?
                </Link>
              </div>

              <Button
                  type="submit"
                  variant="primary"
                  size="lg"
                  className="w-full bg-[#D4B072] hover:bg-[#b89356] text-[#4A3B39] font-semibold"
                  isLoading={isLoading}
              >
                Sign In
              </Button>
            </form>

            <div className="mt-6 text-center">
              <p className="text-[#4A3B39]">
                Don't have an account?{' '}
                <Link
                    to="/signup"
                    className="text-[#D4B072] hover:text-[#b89356] font-medium transition-colors"
                >
                  Sign up
                </Link>
              </p>
            </div>
          </div>
        </div>
      </div>
  );
};
