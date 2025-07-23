
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { AuthProvider } from './context/AuthContext';
import { ProtectedRoute } from './components/ProtectedRoute';
import { SignIn } from './components/auth/SignIn';
import { SignUp } from './components/auth/SignUp';
import { EmailVerification } from './components/auth/EmailVerification';
import { Dashboard } from './components/Dashboard';
import {RequestPasswordResetEmail} from "./components/auth/RequestPasswordResetEmail.tsx";
import {RequestPasswordReset} from "./components/auth/RequestPasswordReset.tsx";
import MainPage from "./components/layout/MainPage.tsx";
import WatchAddForm from "./components/watches/WatchAddForm.tsx";




  const App = () => {

    return (
    <AuthProvider>
      <Router>
        <div className="min-h-screen bg-gray-50">
          <Routes>
            {/* Public Routes */}
            <Route path="/" element={<SignIn />} />
            <Route path="/signup" element={<SignUp />} />
            <Route path="/verify-email" element={<EmailVerification />} />
              <Route path="/request-password-reset" element={<RequestPasswordResetEmail />} />
              <Route path="/reset-password" element={<RequestPasswordReset />} />
            <Route path="/main-page" element={<MainPage />} />
            <Route path="/add-watch" element={<WatchAddForm />} />

            
            {/* Protected Routes */}
            <Route 
              path="/dashboard" 
              element={
                <ProtectedRoute requireEmailVerification={true}>
                  <Dashboard />
                </ProtectedRoute>
              } 
            />
            
            {/* Default redirect */}

          </Routes>
        </div>
      </Router>
    </AuthProvider>
  );
}

export default App;