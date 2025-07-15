// RequestPasswordResetEmail.tsx

import React, { useState } from 'react';
import { Input } from '../ui/Input';
import { Button } from '../ui/Button';
import { Alert } from '../ui/Alert';
import { authService } from '../../services/authService';

export const RequestPasswordResetEmail: React.FC = () => {
    const [email, setEmail] = useState('');
    const [message, setMessage] = useState<{type: 'success' | 'error'; text: string} | null>(null);
    const [isLoading, setIsLoading] = useState(false);

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setIsLoading(true);
        setMessage(null);
        try {
            const response = await authService.requestPasswordReset(email);
            setMessage({ type: 'success', text: response.message });
        } catch (error: any) {
            setMessage({
                type: 'error',
                text: error.response?.data?.message || 'Failed to send reset email.',
            });
        } finally {
            setIsLoading(false);
        }
    };

    return (
        <div className="min-h-screen flex items-center justify-center p-4 bg-gradient-to-br from-gray-900 via-gray-800 to-amber-900">
            <div className="max-w-md w-full bg-white rounded-2xl shadow-2xl p-8">
                <h2 className="text-3xl font-bold text-gray-900 mb-6 text-center">Forgot Password</h2>

                {message && <Alert type={message.type} message={message.text} onClose={() => setMessage(null)} />}

                <form onSubmit={handleSubmit} className="space-y-6">
                    <Input
                        label="Email"
                        name="email"
                        type="email"
                        value={email}
                        onChange={e => setEmail(e.target.value)}
                        placeholder="Enter your email address"
                        required
                    />

                    <Button
                        type="submit"
                        variant="primary"
                        size="lg"
                        className="w-full bg-[#D4B072] hover:bg-[#b89356] text-[#4A3B39] font-semibold"
                        isLoading={isLoading}
                    >
                        Send Reset Email
                    </Button>
                </form>
            </div>
        </div>
    );
};
