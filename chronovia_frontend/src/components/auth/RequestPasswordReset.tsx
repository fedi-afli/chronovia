import React, { useState } from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';
import { Lock} from 'lucide-react';
import { Input } from '../ui/Input';
import { Button } from '../ui/Button';
import { Alert } from '../ui/Alert';
import { authService } from '../../services/authService';

export const RequestPasswordReset: React.FC = () => {
    const [searchParams] = useSearchParams();
    const token = searchParams.get('token');
    const navigate = useNavigate();

    const [formData, setFormData] = useState({
        password: '',
        confirmPassword: '',
    });

    const [message, setMessage] = useState<{ type: 'success' | 'error'; text: string } | null>(null);
    const [isSubmitting, setIsSubmitting] = useState(false);
    const [errors, setErrors] = useState<Record<string, string>>({});

    const validateForm = (): boolean => {
        const newErrors: Record<string, string> = {};
        if (!formData.password) {
            newErrors.password = 'New password is required';
        }
        if (formData.password !== formData.confirmPassword) {
            newErrors.confirmPassword = 'Passwords do not match';
        }
        setErrors(newErrors);
        return Object.keys(newErrors).length === 0;
    };

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setFormData(prev => ({ ...prev, [name]: value }));
        if (errors[name]) {
            setErrors(prev => ({ ...prev, [name]: '' }));
        }
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        if (!token) return setMessage({ type: 'error', text: 'Invalid or missing token.' });
        if (!validateForm()) return;

        setIsSubmitting(true);
        setMessage(null);

        try {
            const response = await authService.resetPassword({token, newPassword: formData.password});
            if (response.success) {
                setMessage({ type: 'success', text: response.message });
                setTimeout(() => navigate('/signin'), 3000);
            } else {
                setMessage({ type: 'error', text: response.message });
            }
        } catch (error: any) {
            setMessage({
                type: 'error',
                text: error.response?.data?.message || 'Reset failed. Please try again.',
            });
        } finally {
            setIsSubmitting(false);
        }
    };

    return (
        <div className="min-h-screen bg-gradient-to-br from-gray-900 via-gray-800 to-amber-900 flex items-center justify-center p-4">
            <div className="max-w-md w-full">
                <div className="bg-white rounded-2xl shadow-2xl p-8">
                    <div className="text-center mb-8">
                        <div className="mx-auto w-16 h-16 bg-gradient-to-r from-amber-600 to-amber-700 rounded-full flex items-center justify-center mb-4">
                            <Lock className="h-8 w-8 text-white" />
                        </div>
                        <h2 className="text-3xl font-bold text-gray-900 mb-2">Reset Your Password</h2>
                        <p className="text-gray-600">Enter your new password below to regain access.</p>
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
                            label="New Password"
                            name="password"
                            type="password"
                            value={formData.password}
                            onChange={handleChange}
                            error={errors.password}
                            icon={<Lock className="h-5 w-5 text-[#4A3B39]" />}
                            placeholder="Enter new password"
                            required
                        />

                        <Input
                            label="Confirm Password"
                            name="confirmPassword"
                            type="password"
                            value={formData.confirmPassword}
                            onChange={handleChange}
                            error={errors.confirmPassword}
                            icon={<Lock className="h-5 w-5 text-[#4A3B39]" />}
                            placeholder="Confirm new password"
                            required
                        />

                        <Button
                            type="submit"
                            variant="primary"
                            size="lg"
                            className="w-full bg-[#D4B072] hover:bg-[#b89356] text-[#4A3B39] font-semibold"
                            isLoading={isSubmitting}
                        >
                            Confirm Password
                        </Button>
                    </form>
                </div>
            </div>
        </div>
    );
};
