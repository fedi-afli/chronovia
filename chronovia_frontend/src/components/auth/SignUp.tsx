import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import { User, Mail, Lock, Eye, EyeOff } from 'lucide-react';
import { Button } from '../ui/Button';
import { Input } from '../ui/Input';
import { Alert } from '../ui/Alert';
import { authService } from '../../services/authService';
import type { SignUpRequest } from '../../types/auth';
import logo from '../../assets/logo.png';

export const SignUp: React.FC = () => {
  const [formData, setFormData] = useState<SignUpRequest>({
    username: '',
    email: '',
    password: '',
  });
  const [confirmPassword, setConfirmPassword] = useState('');
  const [showPassword, setShowPassword] = useState(false);
  const [showConfirmPassword, setShowConfirmPassword] = useState(false);
  const [errors, setErrors] = useState<Record<string, string>>({});
  const [isLoading, setIsLoading] = useState(false);
  const [message, setMessage] = useState<{ type: 'success' | 'error'; text: string } | null>(null);

  const validateForm = (): boolean => {
    const newErrors: Record<string, string> = {};

    if (!formData.username.trim()) {
      newErrors.username = 'Username is required';
    } else if (formData.username.length < 3) {
      newErrors.username = 'Username must be at least 3 characters';
    }

    if (!formData.email.trim()) {
      newErrors.email = 'Email is required';
    } else if (!/\S+@\S+\.\S+/.test(formData.email)) {
      newErrors.email = 'Email is invalid';
    }

    if (!formData.password) {
      newErrors.password = 'Password is required';
    } else if (formData.password.length < 6) {
      newErrors.password = 'Password must be at least 6 characters';
    }

    if (!confirmPassword) {
      newErrors.confirmPassword = 'Please confirm your password';
    } else if (formData.password !== confirmPassword) {
      newErrors.confirmPassword = 'Passwords do not match';
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
      const response = await authService.signUp(formData);
      if (response.success) {
        setMessage({ type: 'success', text: response.message });
        setFormData({ username: '', email: '', password: '' });
        setConfirmPassword('');
      } else {
        setMessage({ type: 'error', text: response.message });
      }
    } catch (error: any) {
      if (error.response?.data) {
        if (typeof error.response.data === 'object' && !error.response.data.message) {
          setErrors(error.response.data);
        } else {
          setMessage({ type: 'error', text: error.response.data.message || 'Registration failed' });
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
              <div className="mx-auto w-16 h-16 mb-4 rounded-full overflow-hidden bg-white shadow-md border border-[#D4B072]">
                <img src={logo} alt="Chronovia Logo" className="w-full h-full object-cover" />
              </div>
              <h2 className="text-3xl font-bold text-[#4A3B39] mb-2">Create Account</h2>
              <p className="text-[#4A3B39]">Join Chronovia and discover luxury timepieces</p>
            </div>

            {message && (
                <div className="mb-6">
                  <Alert type={message.type} message={message.text} onClose={() => setMessage(null)} />
                </div>
            )}

            <form onSubmit={handleSubmit} className="space-y-6">
              <Input
                  label="Username"
                  name="username"
                  type="text"
                  value={formData.username}
                  onChange={handleInputChange}
                  error={errors.username}
                  icon={<User className="h-5 w-5 text-[#4A3B39]" />}
                  placeholder="Enter your username"
                  required
              />

              <Input
                  label="Email Address"
                  name="email"
                  type="email"
                  value={formData.email}
                  onChange={handleInputChange}
                  error={errors.email}
                  icon={<Mail className="h-5 w-5 text-[#4A3B39]" />}
                  placeholder="Enter your email"
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

              <div className="relative">
                <Input
                    label="Confirm Password"
                    name="confirmPassword"
                    type={showConfirmPassword ? 'text' : 'password'}
                    value={confirmPassword}
                    onChange={(e) => {
                      setConfirmPassword(e.target.value);
                      if (errors.confirmPassword) {
                        setErrors(prev => ({ ...prev, confirmPassword: '' }));
                      }
                    }}
                    error={errors.confirmPassword}
                    icon={<Lock className="h-5 w-5 text-[#4A3B39]" />}
                    placeholder="Confirm your password"
                    required
                />
                <button
                    type="button"
                    className="absolute right-3 top-8 text-[#D4B072] hover:text-[#b89356]"
                    onClick={() => setShowConfirmPassword(!showConfirmPassword)}
                >
                  {showConfirmPassword ? <EyeOff className="h-5 w-5" /> : <Eye className="h-5 w-5" />}
                </button>
              </div>

              <Button
                  type="submit"
                  variant="primary"
                  size="lg"
                  className="w-full bg-[#D4B072] hover:bg-[#b89356] text-[#4A3B39] font-semibold"
                  isLoading={isLoading}
              >
                Create Account
              </Button>
            </form>

            <div className="mt-6 text-center">
              <p className="text-[#4A3B39]">
                Already have an account?{' '}
                <Link
                    to="/"
                    className="text-[#D4B072] hover:text-[#b89356] font-medium transition-colors"
                >
                  Sign in
                </Link>
              </p>
            </div>
          </div>
        </div>
      </div>
  );
};
