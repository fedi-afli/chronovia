import React, { useState, useEffect } from 'react';
import { useLocation, useNavigate, useSearchParams } from 'react-router-dom';
import { Mail, CheckCircle, RefreshCw } from 'lucide-react';
import { Button } from '../ui/Button';
import { Alert } from '../ui/Alert';
import { authService } from '../../services/authService';

export const EmailVerification: React.FC = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const [isLoading, setIsLoading] = useState(false);
  const [isResending, setIsResending] = useState(false);
  const [message, setMessage] = useState<{ type: 'success' | 'error'; text: string } | null>(null);
  const [isVerified, setIsVerified] = useState(false);

  const email = location.state?.email || '';
  const token = searchParams.get('token');

  useEffect(() => {
    if (token) {
      handleVerifyEmail(token);
    }
  }, [token]);

  const handleVerifyEmail = async (verificationToken: string) => {
    setIsLoading(true);
    setMessage(null);

    try {
      const response = await authService.verifyEmail(verificationToken);
      if (response.success) {
        setMessage({ type: 'success', text: response.message });
        setIsVerified(true);
        setTimeout(() => {
          navigate('/signin');
        }, 3000);
      } else {
        setMessage({ type: 'error', text: response.message });
      }
    } catch (error: any) {
      setMessage({ 
        type: 'error', 
        text: error.response?.data?.message || 'Verification failed. Please try again.' 
      });
    } finally {
      setIsLoading(false);
    }
  };

  const handleResendVerification = async () => {
    if (!email) {
      setMessage({ type: 'error', text: 'Email address is required to resend verification.' });
      return;
    }

    setIsResending(true);
    setMessage(null);

    try {
      const response = await authService.resendVerification(email);
      if (response.success) {
        setMessage({ type: 'success', text: response.message });
      } else {
        setMessage({ type: 'error', text: response.message });
      }
    } catch (error: any) {
      setMessage({ 
        type: 'error', 
        text: error.response?.data?.message || 'Failed to resend verification email.' 
      });
    } finally {
      setIsResending(false);
    }
  };

  if (isLoading && token) {
    return (
      <div className="min-h-screen bg-gradient-to-br from-gray-900 via-gray-800 to-amber-900 flex items-center justify-center p-4">
        <div className="max-w-md w-full">
          <div className="bg-white rounded-2xl shadow-2xl p-8 text-center">
            <div className="animate-spin mx-auto w-12 h-12 border-4 border-amber-600 border-t-transparent rounded-full mb-4"></div>
            <h2 className="text-2xl font-bold text-gray-900 mb-2">Verifying Email</h2>
            <p className="text-gray-600">Please wait while we verify your email address...</p>
          </div>
        </div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gradient-to-br from-gray-900 via-gray-800 to-amber-900 flex items-center justify-center p-4">
      <div className="max-w-md w-full">
        <div className="bg-white rounded-2xl shadow-2xl p-8">
          <div className="text-center mb-8">
            <div className="mx-auto w-16 h-16 bg-gradient-to-r from-amber-600 to-amber-700 rounded-full flex items-center justify-center mb-4">
              {isVerified ? (
                <CheckCircle className="h-8 w-8 text-white" />
              ) : (
                <Mail className="h-8 w-8 text-white" />
              )}
            </div>
            <h2 className="text-3xl font-bold text-gray-900 mb-2">
              {isVerified ? 'Email Verified!' : 'Verify Your Email'}
            </h2>
            <p className="text-gray-600">
              {isVerified 
                ? 'Your email has been successfully verified. Redirecting to sign in...'
                : email 
                  ? `We've sent a verification link to ${email}. Please check your inbox and click the link to verify your account.`
                  : 'Please check your email for a verification link.'
              }
            </p>
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

          {!isVerified && (
            <div className="space-y-4">
              <div className="text-center">
                <p className="text-sm text-gray-600 mb-4">
                  Didn't receive the email? Check your spam folder or request a new one.
                </p>
                <Button
                  onClick={handleResendVerification}
                  variant="outline"
                  size="md"
                  className="w-full"
                  isLoading={isResending}
                  disabled={!email}
                >
                  <RefreshCw className="h-4 w-4 mr-2" />
                  Resend Verification Email
                </Button>
              </div>

              <div className="text-center pt-4 border-t">
                <Button
                  onClick={() => navigate('/signin')}
                  variant="ghost"
                  size="md"
                >
                  Back to Sign In
                </Button>
              </div>
            </div>
          )}
        </div>
      </div>
    </div>
  );
};